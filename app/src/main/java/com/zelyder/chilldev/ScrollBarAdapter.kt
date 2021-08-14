package com.zelyder.chilldev

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zelyder.chilldev.domain.models.PinCodeStage
import com.zelyder.chilldev.ui.addapp.AddAppScreenFragment
import com.zelyder.chilldev.ui.applicationaccess.ApplicationAccessFragment
import com.zelyder.chilldev.ui.birthday.BirthdayFragment
import com.zelyder.chilldev.ui.childgender.ChildGenderFragmentPage
import com.zelyder.chilldev.ui.congratulation.CongratulationFragment
import com.zelyder.chilldev.ui.interests.InterestsScreenFragment
import com.zelyder.chilldev.ui.kidname.KidNameFragment
import com.zelyder.chilldev.ui.movieage.MovieAgeFragment
import com.zelyder.chilldev.ui.pincode.PinCodeFragment

class ScrollBarAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 9

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> KidNameFragment.newInstance()
            1 -> MovieAgeFragment.newInstance()
            2 -> ChildGenderFragmentPage.newInstance()
            3 -> BirthdayFragment.newInstance()
            4 -> InterestsScreenFragment.newInstance()
            5 -> AddAppScreenFragment.newInstance()
            6 -> ApplicationAccessFragment.newInstance()
            7 -> PinCodeFragment.newInstance(PinCodeStage.NEW)
            else -> CongratulationFragment.newInstance()
        }
    }
}