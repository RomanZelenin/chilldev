package com.zelyder.chilldev.ui.addapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


class InstalledAppsProvider(private val packageManager: PackageManager) {

    private val yandexAppRegex = Regex("com.yandex.(tv|io).\\S+")

    fun provide(): List<InstalledApp> {
        val launchIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
        val resolveInfos = packageManager.queryIntentActivities(launchIntent, PackageManager.MATCH_ALL)
        return resolveInfos
            .map {
                InstalledApp(
                    it.activityInfo.packageName,
                    it.activityInfo.loadLabel(packageManager).toString()
                )
            }
            .filterNot { installedApp ->
                installedApp.packageName.matches(yandexAppRegex)
            }
    }

    @Parcelize
    data class InstalledApp(val packageName: String, val name: String) : Parcelable
}
