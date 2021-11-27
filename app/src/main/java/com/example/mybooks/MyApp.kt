package com.example.mybooks

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.mybooks.util.ThemeManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mode = sharedPreferences.getString("theme", "") ?: ""
        ThemeManager.applyTheme(mode)
    }
}