package com.zelyder.chilldev.ui.movieage

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import com.squareup.picasso.Picasso
import com.zelyder.chilldev.databinding.MovieAgePageBinding
import com.zelyder.chilldev.domain.models.AgeLimit
import com.zelyder.chilldev.ui.FragmentPage
import com.zelyder.chilldev.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        with((requireActivity() as MainActivity)) {
            mainActivityScope.launch {
                remoteService.posters(AgeLimit.SIX_PLUS).body()
                    ?.message
                    ?.forEachIndexed { index, poster_url ->
                        withContext(Dispatchers.Main) {
                            Picasso.get().load(poster_url)
                                .fit()
                                .into((binding.llPosterContainer[index] as ImageView))
                        }
                    }
            }
        }
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