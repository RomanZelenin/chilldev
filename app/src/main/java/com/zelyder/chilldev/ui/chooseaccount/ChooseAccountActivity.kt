package com.zelyder.chilldev.ui.chooseaccount

import android.content.Intent
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zelyder.chilldev.YandexKidTvApplication
import com.zelyder.chilldev.databinding.ActivityChooseAccountBinding
import com.zelyder.chilldev.domain.models.Account
import com.zelyder.chilldev.ui.PinActivity
import com.zelyder.chilldev.ui.main.GenericViewModelFactory
import com.zelyder.chilldev.ui.main.MainActivity
import com.zelyder.chilldev.ui.main.PageViewModel
import com.zelyder.chilldev.ui.main.PageViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ChooseAccountActivity : FragmentActivity() {

    private lateinit var binding: ActivityChooseAccountBinding

    @Inject
    lateinit var pageViewModelFactory: PageViewModelFactory
    lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as YandexKidTvApplication).appComponent
            .createActivitySubcomponent()
            .create()
            .inject(this)

        pageViewModel = ViewModelProvider(
            viewModelStore,
            GenericViewModelFactory(pageViewModelFactory)
        )[PageViewModel::class.java]

        val accountsAdapter = AccountsAdapter(this@ChooseAccountActivity,
            object : AccountClickListener {
                override fun onAddProfileClick() {
                    MainActivity.startActivity(this@ChooseAccountActivity)
                }

                override fun onParentAccountClick() {
                    val intent = Intent(this@ChooseAccountActivity, PinActivity::class.java)
                    startActivity(intent)
                }
            })

        binding.rvAccounts.apply {
            adapter = accountsAdapter
            layoutManager = AccountsLinearLayoutManager(this@ChooseAccountActivity)
            addItemDecoration(AccountsItemDecoration())
        }

        lifecycleScope.launch {
            val accounts = pageViewModel.getAllKids().map {
                if (it.avatar.isDigitsOnly() && it.avatar.toInt() in (0..10)) {
                    Account(it.name, it.avatar)
                } else {
                    Timber.d(it.avatar)
                    Account(it.name, it.avatar)
                }
            }
            accountsAdapter.accounts = accounts
        }

    }
}