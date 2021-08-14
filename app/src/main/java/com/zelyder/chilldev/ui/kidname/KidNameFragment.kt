package com.zelyder.chilldev.ui.kidname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.KidNamePageBinding
import com.zelyder.chilldev.ui.FragmentPage

class KidNameFragment : FragmentPage<KidNamePageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = KidNamePageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kidNameEditText.text.apply {
            clear()
            append(viewModel.kidInfo.value!!.name)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.setKidName(binding.kidNameEditText.text.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() = KidNameFragment()
    }
}