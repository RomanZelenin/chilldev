package com.zelyder.chilldev.ui.childgender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.rgGender.setOnCheckedChangeListener { _, _ ->
            page.swipeToNext()
        }
    }

    override fun onPause() {
        super.onPause()
        val genderIdx = when (binding.rgGender.checkedRadioButtonId) {
            binding.rbMale.id -> 0
            binding.rbFemale.id -> 1
            else -> 2
        }
        viewModel.setKidGender(Gender.values()[genderIdx])
    }
    companion object {
        @JvmStatic
        fun newInstance() = ChildGenderFragmentPage()
    }
}