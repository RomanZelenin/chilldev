package com.zelyder.chilldev.ui.chooseaccount

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zelyder.chilldev.databinding.ActivityChooseAccountBinding
import com.zelyder.chilldev.di.DaggerAppComponent
import com.zelyder.chilldev.domain.models.Account
import com.zelyder.chilldev.ui.main.GenericViewModelFactory
import com.zelyder.chilldev.ui.main.MainActivity
import com.zelyder.chilldev.ui.main.PageViewModel
import com.zelyder.chilldev.ui.main.PageViewModelFactory
import kotlinx.coroutines.launch
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

        DaggerAppComponent.factory()
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
            })

        binding.rvAccounts.apply {
            adapter = accountsAdapter
            layoutManager = AccountsLinearLayoutManager(this@ChooseAccountActivity)
            addItemDecoration(AccountsItemDecoration())
        }

        lifecycleScope.launch {
            val accounts = pageViewModel.getAllKids().map { Account(it.name, it.avatar) }
            accountsAdapter.accounts = accounts
        }

    }
}