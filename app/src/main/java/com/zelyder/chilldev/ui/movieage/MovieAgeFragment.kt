package com.zelyder.chilldev.ui.movieage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.MovieAgePageBinding
import com.zelyder.chilldev.ui.FragmentPage

class MovieAgeFragment : FragmentPage<MovieAgePageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = MovieAgePageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rgAge.setOnCheckedChangeListener { _, _ ->
            page.swipeToNext()
        }
    }

    override fun onStop() {
        binding.root.clearFocus()
        super.onStop()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieAgeFragment()
    }

}