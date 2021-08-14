package com.zelyder.chilldev.ui.applicationaccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.zelyder.chilldev.databinding.ApplicationAccessPageBinding
import com.zelyder.chilldev.domain.models.Gender
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
        var str : String = ""
        binding.kidAge.text = ""
        val list = arrayListOf<String>()
        viewModel.categories.observe(viewLifecycleOwner) { it -> list.addAll(it)}

        viewModel.kidInfo.observe(viewLifecycleOwner) { it ->
            str = it.name + ", "
            val age = 10//getAge(it.birthdate.parseToDate()!!)
            str += buildString {
                append("$age ")
                //val lastNum = age.toString().run { this.last().digitToInt() }
                when (age) {
                    1 -> append("год")
                    in 2..4 -> append("года")
                    0, in 5..20 -> append("лет")
                    else -> append("")
                }
            }

            binding.kidNameTextView.text = str
            binding.kidGender.text = when (it.gender) {
                Gender.FEMALE -> ", девочка"
                Gender.MALE -> ", мальчик"
                Gender.WHATEVER -> ""
            }

            binding.savedLimitationAge.text = "+${it.age_limit}"

            binding.descriptionKidInterests.text =
                it.categories.joinToString { index -> list[index-1] }
        }
        binding.createAccBtn.setOnClickListener {
            lifecycleScope.launch {  viewModel.saveKidInfo() }
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