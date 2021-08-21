package com.zelyder.chilldev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zelyder.chilldev.R
import com.zelyder.chilldev.domain.models.PinCodeStage
import com.zelyder.chilldev.ui.pincode.PinCodeFragment

class PinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        supportFragmentManager.beginTransaction()
            .replace(R.id.pin_fragment, PinCodeFragment.newInstance(PinCodeStage.ENTER))
            .commit()
    }
}