package com.murod.crowdinandroidapp

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.crowdin.platform.Crowdin
import com.crowdin.platform.CrowdinConfig

class App : Application() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Crowdin.wrapContext(newBase))
    }

    override fun onCreate() {
        super.onCreate()

        Crowdin.init(
            applicationContext,
            CrowdinConfig.Builder()
                .withDistributionHash("04273d65834118ac24ad77a1pl3")
                .withSourceLanguage("ru")
                .build()
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Reload translations on configuration change when real-time preview ON.
        Crowdin.onConfigurationChanged()
    }
}