package com.zelyder.chilldev

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zelyder.chilldev.ui.FragmentPage
import com.zelyder.chilldev.ui.addapp.AddAppScreenFragment
import com.zelyder.chilldev.ui.applicationaccess.ApplicationAccessFragment
import com.zelyder.chilldev.ui.birthday.BirthdayFragment
import com.zelyder.chilldev.ui.childgender.ChildGenderFragmentPage
import com.zelyder.chilldev.ui.congratulation.CongratulationFragment
import com.zelyder.chilldev.ui.interests.InterestsScreenFragment
import com.zelyder.chilldev.ui.kidname.KidNameFragment
import com.zelyder.chilldev.ui.movieage.MovieAgeFragment
import com.zelyder.chilldev.ui.pincode.PinCodeFragment


typealias OrderedListPages = Map<Int, Lazy<FragmentPage<*>>>

class PageAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    //fragment order is important!!!
    private val listPages: OrderedListPages = mapOf(
        1 to lazy { KidNameFragment.newInstance() },
        2 to lazy { MovieAgeFragment.newInstance() },
        3 to lazy { ChildGenderFragmentPage.newInstance() },
        4 to lazy { BirthdayFragment.newInstance() },
        5 to lazy { InterestsScreenFragment.newInstance() },
        6 to lazy { AddAppScreenFragment.newInstance() },
        7 to lazy { ApplicationAccessFragment.newInstance() },
        8 to lazy { PinCodeFragment.newInstance() },
        9 to lazy { CongratulationFragment.newInstance() },
    )

    override fun getItemCount(): Int = listPages.size

    override fun createFragment(position: Int): Fragment {
        return listPages[position + 1]!!.value
    }
}