package com.zelyder.chilldev

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.zelyder.chilldev.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentSelectedItem = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.apply {
            adapter = ScrollBarAdapter(this@MainActivity)
            createScrollBarItems(adapter!!.itemCount)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onPageSelected(position: Int) {
                    refreshState(position)
                }
            })
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                if (currentSelectedItem >= 1)
                    binding.pager.setCurrentItem(--currentSelectedItem, true)
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                if (currentSelectedItem <= 6)
                    binding.pager.setCurrentItem(++currentSelectedItem, true)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun refreshState(position: Int) {
        if (binding.scrollBar.size - 2 == 0) return
        val startPos = 1
        val endPos = binding.scrollBar.size - 2
        for (i in startPos..endPos) {
            (binding.scrollBar[i] as TextView).apply {
                background =
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.circle_item)
                setTextColor(getColor(android.R.color.black))
                text = ""
            }
        }
        if (startPos != endPos) {
            when (position + 1) {
                endPos -> {
                    binding.scrollBar[0].visibility = View.VISIBLE
                    binding.scrollBar[endPos + 1].visibility = View.INVISIBLE
                }
                startPos -> {
                    binding.scrollBar[0].visibility = View.INVISIBLE
                    binding.scrollBar[endPos + 1].visibility = View.VISIBLE
                }
                else -> {
                    binding.scrollBar[0].visibility = View.VISIBLE
                    binding.scrollBar[endPos + 1].visibility = View.VISIBLE
                }
            }
        }
        val item = binding.scrollBar[position + 1] as TextView
        item.apply {
            background =
                AppCompatResources.getDrawable(this@MainActivity, R.drawable.circle_item_selected)
            text = (position + 1).toString()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createScrollBarItems(numItems: Int) {
        if (numItems <= 0) return

        val imageViewUp = ImageView(this).apply {
            setOnClickListener { binding.pager.setCurrentItem(binding.pager.currentItem - 1, true) }
            setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24))
            visibility = View.INVISIBLE
        }
        binding.scrollBar.addView(imageViewUp)

        val firstItem = TextView(this).apply {
            background = getDrawable(R.drawable.circle_item_selected)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            gravity = Gravity.CENTER
        }
        binding.scrollBar.addView(firstItem)

        for (i in 0 until numItems - 1) {
            val item = TextView(this).apply {
                background = getDrawable(R.drawable.circle_item)
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                gravity = Gravity.CENTER
            }
            binding.scrollBar.addView(item)
        }
        val imageViewDown = ImageView(this).apply {
            setOnClickListener { binding.pager.setCurrentItem(binding.pager.currentItem + 1, true) }
            setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))
            visibility = View.INVISIBLE
        }
        binding.scrollBar.addView(imageViewDown)
    }

    fun swipeToNext() {
        binding.pager.setCurrentItem(binding.pager.currentItem + 1, true)
    }
}