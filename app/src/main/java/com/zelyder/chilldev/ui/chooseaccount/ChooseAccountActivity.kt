package com.zelyder.chilldev.ui.chooseaccount

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.zelyder.chilldev.databinding.ActivityChooseAccountBinding
import com.zelyder.chilldev.domain.models.Account
import com.zelyder.chilldev.ui.main.MainActivity

class ChooseAccountActivity : FragmentActivity() {

    private lateinit var binding: ActivityChooseAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseAccountBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val accountsAdapter = AccountsAdapter(this@ChooseAccountActivity,
            object : AccountClickListener {
                override fun onAddProfileClick() {
                    MainActivity.startActivity(this@ChooseAccountActivity)
                }
            })

        binding.rvAccounts.apply {
            adapter = accountsAdapter
            layoutManager = AccountsLinearLayoutManager(this@ChooseAccountActivity)
        }

        accountsAdapter.accounts = listOf(
            Account("test1", 1),
            Account("tes2", 2))
    }
}