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
        with(binding) {
            layoutAgeRating.selectedPosition = viewModel.kidInfo.value!!.age_limit.ordinal
            layoutAgeRating.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            (v as AgeRatingLayout).moveToNext()
                            insertPosters()
                            true
                        }
                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            (v as AgeRatingLayout).moveToPrevious()
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

    private fun insertPosters() {
        with(binding.layoutAgeRating) {
            lifecycleScope.launch {
                val selectedAgeLimit = AgeLimit.values()[selectedPosition]
                viewModel.setKidAgeLimit(selectedAgeLimit)
                val posters = viewModel.getPosters(selectedAgeLimit)
                with(binding) {
                    for (i in posters.indices) {
                        Picasso.get().load(posters[i])
                            .fit()
                            .centerCrop()
                            .into(((clPosterContainer[i]) as CardView).getChildAt(0) as ImageView)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieAgeFragment()
    }

}