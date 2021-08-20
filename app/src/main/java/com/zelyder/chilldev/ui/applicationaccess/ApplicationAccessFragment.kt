package com.zelyder.chilldev.ui.applicationaccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.ApplicationAccessPageBinding
import com.zelyder.chilldev.domain.models.Gender
import com.zelyder.chilldev.domain.models.parseToDate
import com.zelyder.chilldev.ui.FragmentPage
import kotlinx.coroutines.launch
import java.util.*

class ApplicationAccessFragment : FragmentPage<ApplicationAccessPageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = ApplicationAccessPageBinding.inflate(inflater, container, attachToParent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var str: String = ""
        binding.kidAge.text = ""
        val list = arrayListOf<String>()

        viewModel.kidInfo.observe(viewLifecycleOwner) { it ->
            val ageFromView = it.birthdate.parseToDate() ?: Date()
            val age = getAge(ageFromView)
            str = it.name
            if (age in 1..20) {
                str += ", "
            }
            str += buildString {
                if (age in 1..20) {
                    append("$age ")
                    when (age) {
                        1 -> append("год")
                        in 2..4 -> append("года")
                        in 5..20 -> append("лет")
                        else -> append("")
                    }
                }
            }
            binding.apply {
                imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        viewModel.kidInfo.value!!.iconType.resId,
                        null
                    )
                )
                createAccBtn.onFocusChangeListener =
                    View.OnFocusChangeListener { _, hasFocus ->
                        if (hasFocus) {
                            binding.createAccBtn.setTextColor(resources.getColor(R.color.text_black))
                        } else {
                            binding.createAccBtn.setTextColor(resources.getColor(R.color.white))
                        }
                    }
                kidNameTextView.text = str
                kidGender.text = when (it.gender) {
                    Gender.FEMALE -> ", ${getString(R.string.child_gender_female).toLowerCase()}"
                    Gender.MALE -> ", ${getString(R.string.child_gender_male).toLowerCase()}"
                    Gender.WHATEVER -> ""
                }
                savedLimitationAge.text = getString(R.string.check_kid_age, it.age_limit)
                savedConfirmSites.text = if (it.apps.keySet().isEmpty()) {
                    getString(R.string.check_no_info)
                } else {
                    it.apps.keySet().joinToString(", ")
                }

                createAccBtn.setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.saveKidInfo()
                        page.swipeToNext()
                    }
                }
            }

            viewModel.categories.observe(viewLifecycleOwner) { listCat ->
                binding.descriptionKidInterests.text =
                    if (it.categories.isEmpty()) {
                        resources.getString(R.string.all)
                    } else {
                        it.categories.joinToString { index -> listCat[index - 1] }
                    }
            }
        }
    }

    private fun getAge(date: Date): Int {
        val dob: Calendar = Calendar.getInstance()
        dob.timeInMillis = date.time
        val today: Calendar = Calendar.getInstance()
        var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        val ageInt = age
        return ageInt
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