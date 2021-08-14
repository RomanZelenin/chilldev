package com.zelyder.chilldev.ui.pincode

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.UserDictionary
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.PinCodePageBinding
import com.zelyder.chilldev.domain.PolicyContract
import com.zelyder.chilldev.domain.models.PinCodeStage
import com.zelyder.chilldev.ui.FragmentPage
import com.zelyder.chilldev.ui.customkeyboard.KeyboardOutput
import com.zelyder.chilldev.ui.main.MainActivity

class PinCodeFragment : FragmentPage<PinCodePageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = PinCodePageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonWithoutPin.setOnClickListener {
            page.swipeToNext()
        }

        var firstPassword = ""
        binding.numPad.setupKeyboard(binding.pinView, 4, object : KeyboardOutput {
            override fun onSizeIsReached() {
                when (arguments?.getInt(PIN_CODE_STAGE)) {
                    PinCodeStage.NEW.type -> {
                        if (firstPassword == "") {
                            firstPassword = binding.pinView.text.toString()
                            binding.description.text =
                                resources.getText(R.string.screen_pin_confirm)
                            binding.pinView.setText("")
                        } else if (firstPassword == binding.pinView.text.toString()) {
                            viewModel.setPinCode(firstPassword)
                            //page.swipeToNext()
                            transferData()

                        } else {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.screen_pin_error_text),
                                Toast.LENGTH_LONG
                            ).show()
                            firstPassword = ""
                            binding.description.text = resources.getText(R.string.screen_pin_description)
                            binding.pinView.setText("")
                        }
                    }
                    PinCodeStage.ENTER.type -> {

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
    }

    fun transferData() {
        val uri: Uri = PolicyContract.buildPolicySettingsUri()
        val updateValues = ContentValues().apply {
//            put(PolicyContract.COLUMN_NAME, PolicyContract.NAME_KIDS_AGE_LIMIT)
//            put(PolicyContract.COLUMN_VALUE, viewModel.kidInfo.value?.age_limit?.age.toString())

            put(PolicyContract.COLUMN_NAME, PolicyContract.NAME_POLICY_LEVEL_INDEX)
            put(PolicyContract.COLUMN_VALUE, "2")
//            put(PolicyContract.NAME_LIMITED_AGE_LIMIT, 18)
//            put(PolicyContract.COLUMN_NAME, PolicyContract.NAME_LIMITED_AGE_LIMIT)
//            put(PolicyContract.COLUMN_VALUE, "18")

//            put(PolicyContract.NAME_SEARCH_MODE_INDEX, 3)
//            put(PolicyContract.NAME_SEARCH_MODE_INDEX, PolicyContract.NAME_LIMITED_AGE_LIMIT)
//            put(PolicyContract.COLUMN_VALUE, "3")
        }




        val rowsUpdated = requireContext().contentResolver.insert(
            uri,   // the user dictionary content URI
            updateValues
        )
        Log.d(this::class.simpleName, "rowsUpdated:$rowsUpdated")
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
}