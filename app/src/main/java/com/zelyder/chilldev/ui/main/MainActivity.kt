package com.zelyder.chilldev.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yandex.tv.services.passport.PassportProviderSdk
import com.zelyder.chilldev.PageAdapter
import com.zelyder.chilldev.databinding.ActivityMainBinding
import com.zelyder.chilldev.di.DaggerAppComponent
import com.zelyder.chilldev.domain.models.Token
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

interface SwipePage {
    fun swipeToNext()
    fun swipeToPrevious()
}

class MainActivity : FragmentActivity(), SwipePage {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var pageViewModelFactory: PageViewModelFactory
    lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAccessToken { token ->
            DaggerAppComponent.factory()
                .create(Token(token!!), application)
                .inject(this)

            pageViewModel = ViewModelProvider(
                viewModelStore,
                GenericViewModelFactory(pageViewModelFactory)
            )[PageViewModel::class.java]

            // Hack to prevent ViewPager2 from grabbing focus
            val recyclerView = ViewPager2::class.java.getDeclaredField("mRecyclerView")
                .also { it.isAccessible = true }
                .get(binding.pager) as RecyclerView
            recyclerView.isFocusable = false

            val pageAdapter = PageAdapter(this@MainActivity)
            val numItemsInScrollBar = pageAdapter.itemCount
            initScrollBar(numItemsInScrollBar)

            binding.pager.apply {
                adapter = pageAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        binding.scrollBar.selectedPosition = position
                    }
                })
                offscreenPageLimit = 1
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val currentPosition = binding.scrollBar.selectedPosition
        when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                when (currentPosition) {
                    3, 4, 6 -> {
                    }
                    else -> swipeToPrevious()
                }
            }

            KeyEvent.KEYCODE_DPAD_DOWN -> {
                when (currentPosition) {
                    0, 3, 4, 6 -> {
                    }
                    5 -> return true
                    else -> swipeToNext()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initScrollBar(numItems: Int) {
        with(binding.scrollBar) {
            this.numItems = numItems
            setOnScrollListenerToPreviousItem { swipeToPrevious() }
            setOnScrollListenerToNextItem { swipeToNext() }
        }
    }

    private fun getAccessToken(callback: (String?) -> Unit) {
        lifecycleScope.launch {
            val passportProviderSdk = PassportProviderSdk(this@MainActivity)
            withContext(Dispatchers.Main) {
                try {
                    callback(passportProviderSdk.currentAccount?.token)
                } catch (e: Exception) {
                    callback("AQAAAAAn24kQAAdMKtm-VDWEMkljrl20f4nKnEk")
                    Timber.e(e, "Cannot get access token")
                }
            }
        }
    }

    override fun swipeToNext() {
        if (binding.scrollBar.selectedPosition <= binding.scrollBar.numItems) {
            binding.pager.setCurrentItem(binding.scrollBar.selectedPosition + 1, true)
        }
    }

    override fun swipeToPrevious() {
        if (binding.scrollBar.selectedPosition >= 1) {
            binding.pager.setCurrentItem(binding.scrollBar.selectedPosition - 1, true)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
