package com.zelyder.chilldev.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val intent = Intent()
            intent.setClassName("com.zelyder.chilldev", "com.zelyder.chilldev.ui.chooseaccount.ChooseAccountActivity")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }
}