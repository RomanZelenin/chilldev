package com.zelyder.chilldev

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.zelyder.chilldev.databinding.ActivityMainBinding


interface SwipePage {
    fun swipeToNext()
    fun swipeToPrevious()
}

class MainActivity : FragmentActivity(), SwipePage {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.apply {
            adapter = ScrollBarAdapter(this@MainActivity)
            initScrollBar(adapter!!.itemCount)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.scrollBar.selectedPosition = position
                }
            })
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                swipeToPrevious()
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                swipeToNext()
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    private fun initScrollBar(numItems: Int) {
        with(binding) {
            scrollBar.numItems = numItems
            scrollBar.setOnScrollListenerToPreviousItem {
                pager.setCurrentItem(pager.currentItem - 1, true)
            }
            scrollBar.setOnScrollListenerToNextItem {
                pager.setCurrentItem(pager.currentItem + 1, true)
            }
        }
    }

    override fun swipeToNext() {
        if (binding.scrollBar.selectedPosition <= binding.scrollBar.numItems)
            binding.pager.setCurrentItem(binding.scrollBar.selectedPosition + 1, true)
    }

    override fun swipeToPrevious() {
        if (binding.scrollBar.selectedPosition >= 1)
            binding.pager.setCurrentItem(binding.scrollBar.selectedPosition - 1, true)
    }
}