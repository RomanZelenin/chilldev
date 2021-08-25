package com.zelyder.chilldev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.zelyder.chilldev.R
import com.zelyder.chilldev.YandexKidTvApplication
import com.zelyder.chilldev.domain.models.PinCodeStage
import com.zelyder.chilldev.ui.main.GenericViewModelFactory
import com.zelyder.chilldev.ui.main.PageViewModel
import com.zelyder.chilldev.ui.main.PageViewModelFactory
import com.zelyder.chilldev.ui.main.SwipePage
import com.zelyder.chilldev.ui.pincode.PinCodeFragment
import javax.inject.Inject

class PinActivity : FragmentActivity(), SwipePage {

    @Inject
    lateinit var pageViewModelFactory: PageViewModelFactory
    lateinit var pageViewModel: PageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        (applicationContext as YandexKidTvApplication).appComponent
            .createActivitySubcomponent()
            .create()
            .inject(this)

        pageViewModel = ViewModelProvider(
            viewModelStore,
            GenericViewModelFactory(pageViewModelFactory)
        )[PageViewModel::class.java]

        supportFragmentManager.beginTransaction()
            .replace(R.id.pin_fragment, PinCodeFragment.newInstance(PinCodeStage.ENTER))
            .commit()
    }

    override fun swipeToNext() {
        Log.d(PinActivity::class.simpleName, "swipeToNext")
    }

    override fun swipeToPrevious() {
        Log.d(PinActivity::class.simpleName, "swipeToNext")
    }
}