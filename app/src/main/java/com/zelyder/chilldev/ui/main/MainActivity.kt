package com.zelyder.chilldev.ui.main

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.widget.ViewPager2
import com.zelyder.chilldev.ScrollBarAdapter
import com.zelyder.chilldev.domain.models.RemoteService
import com.zelyder.chilldev.databinding.ActivityMainBinding
import com.zelyder.chilldev.di.DaggerAppComponent
import kotlinx.coroutines.*
import javax.inject.Inject


interface SwipePage {
    fun swipeToNext()
    fun swipeToPrevious()
}

class MainActivity : FragmentActivity(), SwipePage {

    val mainActivityScope = CoroutineScope(Job())
    private lateinit var binding: ActivityMainBinding
    lateinit var pageViewModel: PageViewModel

    @Inject
    lateinit var remoteService: RemoteService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerAppComponent.factory()
            .create()
            .inject(this)

        pageViewModel = ViewModelProvider(
            viewModelStore,
            PageViewModelFactory(remoteService)
        )[PageViewModel::class.java]

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

    override fun onDestroy() {
        mainActivityScope.cancel()
        super.onDestroy()
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