package com.zelyder.chilldev.ui.birthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numPad.setupKeyboard(binding.day, 2, KeyboardOutputDay())
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

        override fun onFieldIsEmpty() {

        }

    }
}