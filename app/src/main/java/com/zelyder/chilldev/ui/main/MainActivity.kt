package com.zelyder.chilldev.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yandex.tv.services.passport.PassportProviderSdk
import com.zelyder.chilldev.ScrollBarAdapter
import com.zelyder.chilldev.databinding.ActivityMainBinding
import com.zelyder.chilldev.di.DaggerAppComponent
import com.zelyder.chilldev.domain.models.RemoteService
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.concurrent.thread


interface SwipePage {
    fun swipeToNext()
    fun swipeToPrevious()
}

class MainActivity : FragmentActivity(), SwipePage {

    val mainActivityScope = CoroutineScope(Job())
    private lateinit var binding: ActivityMainBinding
    val pageViewModel: PageViewModel by viewModels { PageViewModelFactory() }

    @Inject
    lateinit var remoteService: RemoteService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerAppComponent.factory()
            .create()
            .inject(this)

        // Hack to prevent ViewPager2 from grabbing focus
        val recyclerView = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            .also { it.isAccessible = true }
            .get(binding.pager) as RecyclerView
        recyclerView.isFocusable = false

        binding.pager.apply {
            adapter = ScrollBarAdapter(this@MainActivity)
            initScrollBar(adapter!!.itemCount)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.scrollBar.selectedPosition = position
                }
            })
        }

        // TODO: remove (testing only)
        getAccessToken { token ->
            Log.d(TAG, "Obtained token from TV: $token")
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

    private fun getAccessToken(callback: (String?) -> Unit) {
        thread {
            try {
                val passportProviderSdk = PassportProviderSdk(this)
                Handler(Looper.getMainLooper()).post {
                    callback(passportProviderSdk.currentAccount?.token)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Cannot get access token", e)
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

    companion object {
        private const val TAG = "MainActivity"
    }
}
