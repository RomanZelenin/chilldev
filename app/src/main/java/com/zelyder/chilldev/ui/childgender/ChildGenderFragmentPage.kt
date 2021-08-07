package com.zelyder.chilldev.ui.childgender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.ChildGenderPageBinding
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

    companion object {
        @JvmStatic
        fun newInstance() = ChildGenderFragmentPage()
    }
}