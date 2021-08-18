package com.zelyder.chilldev.ui.childgender

import android.os.Bundle
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

        for (idx in 0 until binding.rgGender.childCount) {
            binding.rgGender.getChildAt(idx).setOnClickListener {
                page.swipeToNext()
            }
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
                    binding.rbSkip
                }
                else -> focused
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.kidInfo.observe(viewLifecycleOwner) {
            setGenderFocus(it.gender)
        }
    }

    override fun onPause() {
        viewModel.kidInfo.removeObservers(viewLifecycleOwner)
        super.onPause()
    }

    private fun setGenderFocus(gender: Gender) {
        when (gender) {
            Gender.MALE -> {
                binding.rbMale.requestFocus()
            }
            Gender.FEMALE -> {
                binding.rbFemale.requestFocus()
            }
            Gender.WHATEVER -> {
                binding.rbSkip.requestFocus()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChildGenderFragmentPage()
    }
}