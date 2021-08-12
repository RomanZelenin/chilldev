package com.zelyder.chilldev.ui.applicationaccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zelyder.chilldev.databinding.ApplicationAccessPageBinding
import com.zelyder.chilldev.domain.models.parseToDate
import com.zelyder.chilldev.ui.FragmentPage
import java.util.*

class ApplicationAccessFragment : FragmentPage<ApplicationAccessPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = ApplicationAccessPageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.kidInfo.observe(viewLifecycleOwner) {
            binding.kidNameTextView.text = it.name
            val age = (Date().year - it.birthdate.parseToDate()!!.year)
            binding.kidAge.text = buildString {
                append("$age ")
                val lastNum = age.toString().run { this.last().digitToInt() }
                when (age) {
                    1 -> append("год")
                    in 2..4 -> append("года")
                    in 5..20 -> append("лет")
                    else -> append("")
                }
                append(", ")
            }
            binding.kidInterests.text = buildString {
                it.categories.forEachIndexed { index, i ->
                    val category = viewModel.cachedCategories.first { it.id == i }
                    append(category.title)
                    if (index < it.categories.size - 1) append(", ")
                }
            }
            binding.kidGender.text = it.gender
        }
        binding.createAccBtn.setOnClickListener {
            viewModel.postKidInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.createAccBtn.requestFocus()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApplicationAccessFragment()
    }
}