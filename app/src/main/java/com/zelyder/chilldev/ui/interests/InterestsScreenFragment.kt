package com.zelyder.chilldev.ui.interests

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zelyder.chilldev.databinding.InterestsPageBinding
import com.zelyder.chilldev.ui.FragmentPage
import kotlin.math.ceil
import android.os.Looper
import android.view.accessibility.AccessibilityEvent

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
        binding.interestsRecycler.apply {
            layoutManager = InterestsStaggeredLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL, page)
            adapter = InterestsRecyclerAdapter(context)
            addItemDecoration(InterestsItemDecoration())
        }
        viewModel.categories.observe(viewLifecycleOwner) { items ->
            setInterests(items)
        }
    }

    private fun setInterests(items: List<String>) {
        val spanCount = ceil(items.size.toFloat() / 2.5).toInt()
        binding.interestsRecycler.apply {
            layoutManager =
                InterestsStaggeredLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL, page)
        }
        (binding.interestsRecycler.adapter as InterestsRecyclerAdapter).setItems(items)
        Handler(Looper.getMainLooper()).post {
            binding.interestsRecycler.adapter?.notifyDataSetChanged()
            requestFocusOnFirstItem()
        }
    }

    override fun onResume() {
        super.onResume()
        requestFocusOnFirstItem()

    }

    override fun onPause() {
        super.onPause()
        viewModel.setKidCategories((binding.interestsRecycler.adapter as InterestsRecyclerAdapter).checkedCategories)
    }

    private fun requestFocusOnFirstItem() {
        binding.interestsRecycler.getChildAt(0)?.apply {
            requestFocus()
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = InterestsScreenFragment()
    }
}