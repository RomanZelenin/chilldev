package com.zelyder.chilldev.ui.childgender

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.ChildGenderPageBinding
import com.zelyder.chilldev.domain.models.Gender
import com.zelyder.chilldev.ui.FragmentPage

class ChildGenderFragmentPage : FragmentPage<ChildGenderPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = ChildGenderPageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rbSkip.setOnClickListener {
            handleOnGenderClick(it as RadioButton)
        }
        binding.rbMale.setOnClickListener {
            handleOnGenderClick(it as RadioButton)
        }
        binding.rbFemale.setOnClickListener {
            handleOnGenderClick(it as RadioButton)
        }
        binding.browseContainer.setOnFocusSearchListener { focused, direction ->
            when {
                focused.id == R.id.rbSkip && direction == View.FOCUS_RIGHT -> {
                    viewModel.setKidGender(Gender.values()[UIGender.FEMALE.value])
                    binding.rbFemale
                }
                focused.id == R.id.rbFemale && direction == View.FOCUS_RIGHT -> {
                    viewModel.setKidGender(Gender.values()[UIGender.MALE.value])
                    binding.rbMale
                }
                focused.id == R.id.rbMale && direction == View.FOCUS_LEFT -> {
                    viewModel.setKidGender(Gender.values()[UIGender.FEMALE.value])
                    binding.rbFemale
                }
                focused.id == R.id.rbFemale && direction == View.FOCUS_LEFT -> {
                    viewModel.setKidGender(Gender.values()[UIGender.WHATEVER.value])
                    binding.rbSkip
                }
                focused.id == R.id.rbSkip && direction == View.FOCUS_LEFT -> {
                    null
                }
                else -> focused
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setDefaultFocus()
    }

    private fun setDefaultFocus() {
        viewModel.kidInfo.value?.gender?.let {
            when (it) {
                Gender.MALE.type -> binding.rbMale.requestFocus()
                Gender.FEMALE.type -> binding.rbFemale.requestFocus()
                else -> binding.rbSkip.requestFocus()
            }
        }
    }

    private fun handleOnGenderClick(radioButton: RadioButton) {
        radioButton.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed(
            {
                page.swipeToNext()
                radioButton.isEnabled = true
            },
            1000
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChildGenderFragmentPage()
    }
}