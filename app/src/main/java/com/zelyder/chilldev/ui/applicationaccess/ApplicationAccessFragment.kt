package com.zelyder.chilldev.ui.applicationaccess

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.ApplicationAccessPageBinding
import com.zelyder.chilldev.ui.FragmentPage

class ApplicationAccessFragment : FragmentPage<ApplicationAccessPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = ApplicationAccessPageBinding.inflate(inflater, container, attachToParent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApplicationAccessFragment()
    }
}