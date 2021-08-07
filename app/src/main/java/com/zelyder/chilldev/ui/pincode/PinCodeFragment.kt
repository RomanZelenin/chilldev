package com.zelyder.chilldev.ui.pincode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.PinCodePageBinding
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
        binding.numPad.setupKeyboard(binding.pinView, 4, object: KeyboardOutput {
            override fun onSizeIsReached() {
                (activity as MainActivity).pageViewModel.setPinCode(binding.pinView.text.toString())
                page.swipeToNext()
            }

        })
    }

    override fun onResume() {
        super.onResume()
//        binding.numPad.requestFocus()

    }

    companion object {
        @JvmStatic
        fun newInstance() = PinCodeFragment()
    }
}