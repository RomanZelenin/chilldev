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
        binding.pinBtn1.setOnClickListener(this.ClickLister())
        binding.pinBtn2.setOnClickListener(this.ClickLister())
        binding.pinBtn3.setOnClickListener(this.ClickLister())
        binding.pinBtn4.setOnClickListener(this.ClickLister())
        binding.pinBtn5.setOnClickListener(this.ClickLister())
        binding.pinBtn6.setOnClickListener(this.ClickLister())
        binding.pinBtn7.setOnClickListener(this.ClickLister())
        binding.pinBtn8.setOnClickListener(this.ClickLister())
        binding.pinBtn9.setOnClickListener(this.ClickLister())
        binding.pinBtn0.setOnClickListener(this.ClickLister())
        binding.pinBtnDel.setOnClickListener(this.ClickLister())
    }

    override fun onResume() {
        super.onResume()
        binding.pinBtn1.requestFocus()

    }

    inner class ClickLister : View.OnClickListener {
        override fun onClick(view: View?) {
            var len = binding.pinView.text?.length ?: 0
            when (view?.id) {
                R.id.pin_btn_1 -> binding.pinView.text?.append("1")
                R.id.pin_btn_2 -> binding.pinView.text?.append("2")
                R.id.pin_btn_3 -> binding.pinView.text?.append("3")
                R.id.pin_btn_4 -> binding.pinView.text?.append("4")
                R.id.pin_btn_5 -> binding.pinView.text?.append("5")
                R.id.pin_btn_6 -> binding.pinView.text?.append("6")
                R.id.pin_btn_7 -> binding.pinView.text?.append("7")
                R.id.pin_btn_8 -> binding.pinView.text?.append("8")
                R.id.pin_btn_9 -> binding.pinView.text?.append("9")
                R.id.pin_btn_0 -> binding.pinView.text?.append("0")
                R.id.pin_btn_del -> {
                    if (len > 0) {
                        binding.pinView.text?.delete(len - 1, len)
                    }
                }
            }
            len = binding.pinView.text?.length ?: 0
            if (len == 4) {
                (activity as MainActivity).pageViewModel.setPinCode(binding.pinView.text.toString())
                page.swipeToNext()
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = PinCodeFragment()
    }
}