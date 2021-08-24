package com.zelyder.chilldev.ui.addapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.content.ContextCompat
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
            layoutManager = AppLinearLayoutManager(requireContext(), page)
            adapter = AppRecyclerAdapter(object : AppClickListener {
                override fun onAppClick(title: String, checked: Boolean) {
                    viewModel.updateServices(title, checked)
                }
            })
        }

        binding.recycler.smoothScrollToPosition(0)
        val installedAppsProvider = InstalledAppsProvider(requireContext().packageManager)
        val installedAppsList = installedAppsProvider.provide()

        val appItems = installedAppsList.map {
            AppItem(it.name,
                viewModel.kidInfo.value!!.apps.keySet().contains(it.name),
                it.icon)
        }

        val additionalItems : ArrayList<AppItem> = arrayListOf()

        additionalItems.add(AppItem("Youtube",
            viewModel.kidInfo.value!!.apps.keySet().contains("Youtube"),
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_icon_youtube)!!))

        additionalItems.add(
            AppItem("ivi",
                viewModel.kidInfo.value!!.apps.keySet().contains("ivi"),
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_icon_ivi)!!))

        (binding.recycler.adapter as AppRecyclerAdapter).setItems(appItems, additionalItems)
    }

    override fun onResume() {
        super.onResume()
        binding.recycler.smoothScrollToPosition(0)
        requestFocusOnFirstItem()
    }

    private fun requestFocusOnFirstItem() {
        binding.recycler.getChildAt(0)?.apply {
            requestFocus()
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddAppScreenFragment()
    }
}