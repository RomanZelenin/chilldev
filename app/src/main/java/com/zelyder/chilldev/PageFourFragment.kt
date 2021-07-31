package com.zelyder.chilldev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zelyder.chilldev.databinding.FragmentPageFourBinding
import com.zelyder.chilldev.databinding.FragmentPageSampleBinding


class PageFourFragment : Fragment() {

    private var _binding: FragmentPageFourBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPageFourBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.day.requestFocus(View.FOCUSABLES_ALL)
    }

    override fun onResume() {
        super.onResume()
        binding.day.requestFocus(View.FOCUSABLES_ALL)
    }

    override fun onStop() {
        super.onStop()
        binding.root.clearFocus()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PageFourFragment()
    }
}