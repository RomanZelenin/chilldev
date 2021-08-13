package com.zelyder.chilldev.ui.pincode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.PinCodePageBinding
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
        binding.numPad.setupKeyboard(binding.pinView, 4, object : KeyboardOutput {
            override fun onSizeIsReached() {
                (activity as MainActivity).pageViewModel.setPinCode(binding.pinView.text.toString())
                page.swipeToNext()
            }
        })

        binding.description.text = resources.getText(
            when(arguments?.getInt(PIN_CODE_STAGE)) {
                PinCodeStage.NEW.type -> R.string.screen_pin_description
                PinCodeStage.CONFIRM.type -> R.string.screen_pin_confirm
                PinCodeStage.ENTER.type -> R.string.screen_pin_enter
                else -> R.string.screen_pin_description
            }
        )
    }

    companion object {
        private const val PIN_CODE_STAGE = "pin_code_stage"
        @JvmStatic
        fun newInstance(pinCodeStage: PinCodeStage):PinCodeFragment {
            val fragment = PinCodeFragment()
            val bundle = Bundle()
            bundle.putInt(PIN_CODE_STAGE, pinCodeStage.type)
            fragment.arguments = bundle
            return fragment
        }
    }
}