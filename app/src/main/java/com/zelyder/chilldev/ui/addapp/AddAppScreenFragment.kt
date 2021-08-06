package com.zelyder.chilldev.ui.addapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zelyder.chilldev.databinding.AppAccessPageBinding
import com.zelyder.chilldev.ui.FragmentPage

class AddAppScreenFragment : FragmentPage<AppAccessPageBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AppRecyclerAdapter()
        }
        return binding.root
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = AppAccessPageBinding.inflate(inflater, container, attachToParent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddAppScreenFragment()
    }
}