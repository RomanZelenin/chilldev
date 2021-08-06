package com.zelyder.chilldev.ui.movieage

import android.os.Bundle
import android.view.KeyEvent
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
        binding.layoutAgeRating.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        (v as AgeRatingLayout).moveToNext()
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        (v as AgeRatingLayout).moveToPrevious()
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        page.swipeToNext()
                        true
                    }
                    else -> false
                }
            } else {
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.layoutAgeRating.requestFocus()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieAgeFragment()
    }

}