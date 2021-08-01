package com.zelyder.chilldev.ui.birthday

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.BirthdayPageBinding
import com.zelyder.chilldev.ui.FragmentPage


class BirthdayFragment : FragmentPage<BirthdayPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = BirthdayPageBinding.inflate(inflater, container, attachToParent)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BirthdayFragment()
    }
}