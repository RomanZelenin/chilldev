package com.zelyder.chilldev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zelyder.chilldev.R
import com.zelyder.chilldev.domain.models.PinCodeStage
import com.zelyder.chilldev.ui.main.SwipePage
import com.zelyder.chilldev.ui.pincode.PinCodeFragment

class PinActivity : FragmentActivity(), SwipePage {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
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