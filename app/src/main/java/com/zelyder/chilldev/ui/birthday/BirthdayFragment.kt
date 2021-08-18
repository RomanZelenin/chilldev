package com.zelyder.chilldev.ui.birthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.BirthdayPageBinding
import com.zelyder.chilldev.ui.FragmentPage
import com.zelyder.chilldev.ui.customkeyboard.KeyboardOutput
import com.zelyder.chilldev.ui.main.MainActivity


class BirthdayFragment : FragmentPage<BirthdayPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = BirthdayPageBinding.inflate(inflater, container, attachToParent)
    }

    private fun initBirthdayErrorHandler() {
        binding.month.doAfterTextChanged { month ->
            if (month!!.isNotEmpty()) {
                if (month.toString().toInt() !in 0..12 || month.toString() == "00") {
                    showErrorMessage(
                        binding.month,
                        resources.getString(R.string.month_message_error)
                    )
                } else {
                    hideErrorMessage(binding.month)
                }
            } else {
                hideErrorMessage(binding.month)
            }
        }
        binding.day.doAfterTextChanged { day ->
            if (day!!.isNotEmpty()) {
                if (day.toString().toInt() !in 0..31 || day.toString() == "00") {
                    showErrorMessage(binding.day, resources.getString(R.string.day_message_error))
                } else {
                    hideErrorMessage(binding.day)
                }
            } else {
                hideErrorMessage(binding.day)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.numPad.requestFocus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numPad.setupKeyboard(binding.day, 2, KeyboardOutputDay())
        initBirthdayErrorHandler()
    }

    private fun showErrorMessage(text: EditText, mess: String) {
        text.setTextColor(resources.getColor(R.color.red))
        binding.errorMessage.text = mess
        binding.errorMessage.visibility = View.VISIBLE
        binding.numPad.setOnlyDeleteIsClickable(true)
    }

    private fun hideErrorMessage(text: EditText) {
        text.setTextColor(resources.getColor(R.color.white))
        binding.errorMessage.visibility = View.INVISIBLE
        binding.numPad.setOnlyDeleteIsClickable(false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BirthdayFragment()
    }

    inner class KeyboardOutputYear() : KeyboardOutput {
        override fun onSizeIsReached() {
            (activity as MainActivity).pageViewModel.setKidBirthday("${binding.year.text}-${binding.month.text}-${binding.day.text}")
            page.swipeToNext()
        }

        override fun onFieldIsEmpty() {
            binding.numPad.setupKeyboard(binding.month, 2, KeyboardOutputMonth())
        }

    }

    inner class KeyboardOutputMonth() : KeyboardOutput {
        override fun onSizeIsReached() {
            binding.numPad.setupKeyboard(binding.year, 4, KeyboardOutputYear())
        }

        override fun onFieldIsEmpty() {
            binding.numPad.setupKeyboard(binding.day, 2, KeyboardOutputDay())
        }

    }

    inner class KeyboardOutputDay() : KeyboardOutput {
        override fun onSizeIsReached() {
            binding.numPad.setupKeyboard(binding.month, 2, KeyboardOutputMonth())
        }

    }
}