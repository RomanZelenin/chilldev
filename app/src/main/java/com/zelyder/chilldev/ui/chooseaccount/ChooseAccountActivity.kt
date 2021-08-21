package com.zelyder.chilldev.ui.chooseaccount

import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yandex.tv.services.passport.PassportProviderSdk
import com.zelyder.chilldev.databinding.ActivityChooseAccountBinding
import com.zelyder.chilldev.di.DaggerAppComponent
import com.zelyder.chilldev.domain.models.Account
import com.zelyder.chilldev.domain.models.Token
import com.zelyder.chilldev.ui.main.GenericViewModelFactory
import com.zelyder.chilldev.ui.main.MainActivity
import com.zelyder.chilldev.ui.main.PageViewModel
import com.zelyder.chilldev.ui.main.PageViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        getAccessToken { token ->
            Timber.d("Provider SDK Token:${token}")
            DaggerAppComponent.factory()
                .create(Token(token!!), application)
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
                    }else{
                        Timber.d(it.avatar)
                        Account(it.name, it.avatar)
                    }
                }
                accountsAdapter.accounts = accounts
            }
        }
    }

    private fun getAccessToken(callback: (String?) -> Unit) {
        lifecycleScope.launch {
            val passportProviderSdk = PassportProviderSdk(this@ChooseAccountActivity)
            withContext(Dispatchers.Main) {
                try {
                    callback(passportProviderSdk.currentAccount?.token)
                } catch (e: Exception) {
                    //Send Token for debugging
                    callback("AQAAAAANz-MGAAX_m-59qwupY0EhlUjn265cWDg")
                    Timber.e(e, "Cannot get access token")
                }
            }
        }
    }
}