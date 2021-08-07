package com.zelyder.chilldev.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zelyder.chilldev.ui.main.SwipePage

abstract class FragmentPage<T : ViewBinding> : Fragment() {

    protected var _binding: T? = null
    protected val binding
        get() = _binding!!

    protected lateinit var page: SwipePage

    override fun onAttach(context: Context) {
        super.onAttach(context)
        page = context as SwipePage
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflateBinding(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    )
}