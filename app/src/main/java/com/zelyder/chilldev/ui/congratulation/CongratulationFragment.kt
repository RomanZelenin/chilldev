package com.zelyder.chilldev.ui.congratulation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.CongratulationPageBinding
import com.zelyder.chilldev.ui.FragmentPage

class CongratulationFragment : FragmentPage<CongratulationPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = CongratulationPageBinding.inflate(inflater, container, attachToParent)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CongratulationFragment()
    }
}