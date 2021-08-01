package com.zelyder.chilldev.ui.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.InterestsPageBinding
import com.zelyder.chilldev.ui.FragmentPage

class InterestsScreenFragment : FragmentPage<InterestsPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = InterestsPageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firstInterests.requestFocus()
    }

    companion object {
        @JvmStatic
        fun newInstance() = InterestsScreenFragment()
    }
}