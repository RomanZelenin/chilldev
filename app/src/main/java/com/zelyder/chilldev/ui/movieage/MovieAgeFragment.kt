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
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import com.zelyder.chilldev.databinding.MovieAgePageBinding
import com.zelyder.chilldev.domain.models.AgeLimit
import com.zelyder.chilldev.ui.FragmentPage
import kotlinx.coroutines.launch

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
        binding.layoutAgeRating.requestFocus()
        with(binding) {
            layoutAgeRating.selectedPosition =
                AgeLimit.values().indexOfFirst { it.age == viewModel.kidInfo.value!!.age_limit }
            layoutAgeRating.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            (v as AgeRatingLayout).moveToNext()
                            updateAgeLimit()
                            insertPosters()
                            true
                        }
                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            (v as AgeRatingLayout).moveToPrevious()
                            updateAgeLimit()
                            insertPosters()
                            true
                        }
                        KeyEvent.KEYCODE_DPAD_CENTER -> {
                            layoutAgeRating.isEnabled = false
                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    page.swipeToNext()
                                    layoutAgeRating.isEnabled = true
                                },
                                500
                            )
                            true
                        }
                        else -> false
                    }
                } else {
                    false
                }
            }
        }
        insertPosters()
    }

    override fun onResume() {
        super.onResume()
        binding.layoutAgeRating.requestFocus()
    }

    private fun updateAgeLimit() {
        val selectedAgeLimit =
            AgeLimit.values()[binding.layoutAgeRating.selectedPosition]
        viewModel.setKidAgeLimit(selectedAgeLimit)
    }

    private fun insertPosters() {
        lifecycleScope.launch {
            val selectedAgeLimit = AgeLimit.values()[binding.layoutAgeRating.selectedPosition]
            val posters = viewModel.getPosters(selectedAgeLimit)
            posters.forEachIndexed { index, uri ->
                Picasso.get()
                    .load(uri)
                    .into(((binding.clPosterContainer[index]) as CardView).getChildAt(0) as ImageView)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieAgeFragment()
    }

}