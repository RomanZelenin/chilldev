package com.zelyder.chilldev.ui.applicationaccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createAccBtn.setOnClickListener {
            viewModel.postKidInfo()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApplicationAccessFragment()
    }
}