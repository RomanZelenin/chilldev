package com.zelyder.chilldev.ui.interests

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zelyder.chilldev.databinding.InterestsPageBinding
import com.zelyder.chilldev.ui.FragmentPage
import kotlin.math.ceil
import android.os.Looper




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
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            adapter = InterestsRecyclerAdapter(context)
            addItemDecoration(InterestsItemDecoration())
        }
        viewModel.fetchCategories()
        viewModel.categories.observe(viewLifecycleOwner) { items ->
            Log.d("TEST_CHECK", "items : $items")
            setInterests(items)
        }
    }

    private fun setInterests(items: List<String>) {
        val spanCount = ceil(items.size.toFloat() / 2.5).toInt()
        binding.interestsRecycler.apply {
            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
        }
        (binding.interestsRecycler.adapter as InterestsRecyclerAdapter).setItems(items)
        Handler(Looper.getMainLooper()).post(Runnable {
            binding.interestsRecycler.adapter?.notifyDataSetChanged()
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.setKidCategories((binding.interestsRecycler.adapter as InterestsRecyclerAdapter).checkedCategories)
    }

    companion object {
        @JvmStatic
        fun newInstance() = InterestsScreenFragment()
    }
}