package com.zelyder.chilldev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.FragmentPageSampleBinding
import com.zelyder.chilldev.databinding.FragmentPinCodeBinding

class PinCodeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pin_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PinCodeFragment().apply {
                arguments = Bundle().apply { putString("pageNum", param1) }
            }
    }
}