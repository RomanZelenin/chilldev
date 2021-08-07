package com.zelyder.chilldev.ui.kidname

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.KidNamePageBinding
import com.zelyder.chilldev.ui.FragmentPage

class KidNameFragment: FragmentPage<KidNamePageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = KidNamePageBinding.inflate(inflater, container, attachToParent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = KidNameFragment()
    }
}