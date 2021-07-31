package com.zelyder.chilldev.childgender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zelyder.chilldev.MainActivity
import com.zelyder.chilldev.databinding.FragmentChildGenderBinding

class ChildGenderFragment : Fragment() {

    private var _binding: FragmentChildGenderBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildGenderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rgGender.setOnCheckedChangeListener { _, _ ->
            (activity as MainActivity).swipeToNext()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}