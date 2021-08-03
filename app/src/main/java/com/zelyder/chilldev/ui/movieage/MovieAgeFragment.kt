package com.zelyder.chilldev.ui.movieage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import com.squareup.picasso.Picasso
import com.zelyder.chilldev.MainActivity
import com.zelyder.chilldev.data.ChildAges
import com.zelyder.chilldev.databinding.MovieAgePageBinding
import com.zelyder.chilldev.ui.FragmentPage
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
        binding.rgAge.setOnCheckedChangeListener { _, _ ->
            page.swipeToNext()
        }

        with((requireActivity() as MainActivity)) {
            mainActivityScope.launch {
                remoteService.posters(ChildAges.SIX).body()
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

    override fun onStop() {
        binding.root.clearFocus()
        super.onStop()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieAgeFragment()
    }

}