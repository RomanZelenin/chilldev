package com.zelyder.chilldev.ui.pincode

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.postDelayed
import androidx.lifecycle.lifecycleScope
import com.yandex.tv.services.passport.PassportProviderSdk
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.PinCodePageBinding
import com.zelyder.chilldev.di.DaggerAppComponent
import com.zelyder.chilldev.domain.PolicyContract
import com.zelyder.chilldev.domain.models.PinCodeStage
import com.zelyder.chilldev.domain.models.Token
import com.zelyder.chilldev.domain.repository.Repository
import com.zelyder.chilldev.ui.FragmentPage
import com.zelyder.chilldev.ui.customkeyboard.KeyboardOutput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class PinCodeFragment : FragmentPage<PinCodePageBinding>() {

    @Inject
    lateinit var repository: Repository

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = PinCodePageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAccessToken { token ->
            DaggerAppComponent.factory()
                .create(Token(token!!), requireActivity().application)
                .inject(this)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonWithoutPin.setOnClickListener {
            page.swipeToNext()
        }

        var firstPassword = ""
        binding.numPad.setupKeyboard(binding.pinView, 4, object : KeyboardOutput {
            override fun onSizeIsReached() {
                binding.pinView.postDelayed(300) {
                    when (arguments?.getInt(PIN_CODE_STAGE)) {
                        PinCodeStage.NEW.type -> {
                            if (firstPassword == "") {
                                firstPassword = binding.pinView.text.toString()
                                binding.description.text =
                                    resources.getText(R.string.screen_pin_confirm)
                                binding.pinView.setText("")
                            } else if (firstPassword == binding.pinView.text.toString()) {
                                viewModel.setPinCode(firstPassword)
                                transferData("2")
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    resources.getString(R.string.screen_pin_error_text),
                                    Toast.LENGTH_LONG
                                ).show()
                                firstPassword = ""
                                binding.description.text =
                                    resources.getText(R.string.screen_pin_description)
                                binding.pinView.setText("")
                            }
                        }
                        PinCodeStage.ENTER.type -> {
                            if (firstPassword == repository.getPinCode()){
                                transferData("0")
                            }
                        }
                    }
                }
            }
        })

        binding.description.text = resources.getText(
            when (arguments?.getInt(PIN_CODE_STAGE)) {
                PinCodeStage.NEW.type -> R.string.screen_pin_description
                PinCodeStage.ENTER.type -> R.string.screen_pin_enter
                else -> R.string.screen_pin_description
            }
        )

        if (arguments?.getInt(PIN_CODE_STAGE) == PinCodeStage.ENTER.type) {
            binding.buttonWithoutPin.visibility = View.GONE
        }
    }


    override fun onResume() {
        super.onResume()
        binding.numPad.requestFocus()
    }

    fun transferData(param: String) {
        val uri: Uri = PolicyContract.buildPolicySettingsUri()
        val updateValues = ContentValues().apply {
            put(PolicyContract.COLUMN_NAME, PolicyContract.NAME_POLICY_LEVEL_INDEX)
            put(PolicyContract.COLUMN_VALUE, param)
        }
        val rowsUpdated = requireContext().contentResolver.insert(
            uri,   // the user dictionary content URI
            updateValues
        )
        Timber.d("rowsUpdated:$rowsUpdated")
        requireActivity().finish()
    }

    companion object {
        private const val PIN_CODE_STAGE = "pin_code_stage"

        @JvmStatic
        fun newInstance(pinCodeStage: PinCodeStage): PinCodeFragment {
            val fragment = PinCodeFragment()
            val bundle = Bundle()
            bundle.putInt(PIN_CODE_STAGE, pinCodeStage.type)
            fragment.arguments = bundle
            return fragment
        }
    }
    private fun getAccessToken(callback: (String?) -> Unit) {
        lifecycleScope.launch {
            val passportProviderSdk = PassportProviderSdk(requireActivity())
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