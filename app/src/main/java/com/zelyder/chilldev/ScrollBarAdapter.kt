package com.zelyder.chilldev

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zelyder.chilldev.childgender.ChildGenderFragment
import com.zelyder.chilldev.movieage.MovieAgeFragment

class ScrollBarAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 8

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> MovieAgeFragment()
            2 -> ChildGenderFragment()
            3 -> PageFourFragment.newInstance()
            7 -> PinCodeFragment.newInstance((position + 1).toString())
            4 -> InterestsScreenFragment()
            5 -> AddAppScreenFragment()
            0-> KidNameFragment()
            6-> ApplicationAccessFragment()
            else -> {
                PageSampleFragment.newInstance((position + 1).toString())
            }
        }
    }
}