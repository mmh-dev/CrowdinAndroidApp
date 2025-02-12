package com.murod.crowdinandroidapp

import android.content.Context
import java.util.Locale

class LanguagePreferences(context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getLanguageCode(): String {
        return prefs.getString("language", Locale.getDefault().language) ?: "ru"
    }

    fun setLanguageCode(language: String) {
        prefs.edit().putString("language", language).apply()
    }
}