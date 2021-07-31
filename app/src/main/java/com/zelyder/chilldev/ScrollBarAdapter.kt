package com.zelyder.chilldev

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScrollBarAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 8

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            4 -> InterestsScreenFragment()
            5 -> AddAppScreenFragment()
            else -> {
                PageSampleFragment.newInstance((position + 1).toString())
            }
        }
    }
}