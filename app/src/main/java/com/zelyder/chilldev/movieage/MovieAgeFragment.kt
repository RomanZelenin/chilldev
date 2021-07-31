package com.zelyder.chilldev.movieage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zelyder.chilldev.MainActivity
import com.zelyder.chilldev.databinding.FragmentMovieAgeBinding

class MovieAgeFragment : Fragment() {

    private var _binding: FragmentMovieAgeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieAgeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rgAge.setOnCheckedChangeListener { _, _ ->
            (activity as MainActivity).swipeToNext()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}