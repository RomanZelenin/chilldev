package com.zelyder.chilldev.ui.addapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.AppAccessPageBinding
import com.zelyder.chilldev.ui.FragmentPage

class AddAppScreenFragment : FragmentPage<AppAccessPageBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = AppAccessPageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AppRecyclerAdapter()
        }

        binding.recycler.smoothScrollToPosition(0)
        val installedAppsProvider = InstalledAppsProvider(requireContext().packageManager)
        val installedAppsList = installedAppsProvider.provide()

        val appItems = installedAppsList.map {
            AppItem(it.name, R.color.orange)
        }

        (binding.recycler.adapter as AppRecyclerAdapter).setItems(appItems)
    }

    override fun onResume() {
        super.onResume()
        binding.recycler.smoothScrollToPosition(0)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddAppScreenFragment()
    }
}