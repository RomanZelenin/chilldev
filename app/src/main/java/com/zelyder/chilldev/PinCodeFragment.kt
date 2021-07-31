package com.zelyder.chilldev

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.FragmentPageSampleBinding
import com.zelyder.chilldev.databinding.FragmentPinCodeBinding

class PinCodeFragment : Fragment() {

    private var _binding: FragmentPinCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonWithoutPin.setOnClickListener {
//            childFragmentManager.beginTransaction()
//                .add(CongratulationFragment(), CongratulationFragment::class.simpleName)
//                .commit()
//        }
    }

    override fun onResume() {
        binding.pinView.requestFocus()
        super.onResume()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PinCodeFragment().apply {
                arguments = Bundle().apply { putString("pageNum", param1) }
            }
    }
}