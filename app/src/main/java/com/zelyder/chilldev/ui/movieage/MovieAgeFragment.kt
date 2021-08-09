package com.zelyder.chilldev.ui.movieage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.squareup.picasso.Picasso
import com.zelyder.chilldev.databinding.MovieAgePageBinding
import com.zelyder.chilldev.domain.models.AgeLimit
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
        setObservers()
        binding.layoutAgeRating.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        (v as AgeRatingLayout).moveToNext()
                        viewModel.setPosters(AgeLimit.values()[binding.layoutAgeRating.selectedPosition + 1])
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        (v as AgeRatingLayout).moveToPrevious()
                        viewModel.setPosters(AgeLimit.values()[binding.layoutAgeRating.selectedPosition + 1])
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        binding.layoutAgeRating.isEnabled = false
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                page.swipeToNext()
                                binding.layoutAgeRating.isEnabled = true
                            },
                            1000
                        )
                        true
                    }
                    else -> false
                }
            } else {
                false
            }
        }
        viewModel.setPosters(AgeLimit.values()[binding.layoutAgeRating.selectedPosition + 1])
    }

    private fun setObservers() {
        viewModel.posters.observe(viewLifecycleOwner, { posters ->
            with(binding) {
                for (i in posters.indices) {
                    Picasso.get().load(posters[i])
                        .fit()
                        .into(((clPosterContainer[i]) as CardView).getChildAt(0) as ImageView)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.layoutAgeRating.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        viewModel.setKidAgeLimit(AgeLimit.values()[binding.layoutAgeRating.selectedPosition + 1])
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieAgeFragment()
    }

}