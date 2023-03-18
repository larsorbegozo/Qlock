package com.larsorbegozo.qlock.adapter

import com.larsorbegozo.qlock.data.model.Settings

class SettingsProvider {
    companion object {
        val settingsList: List<Settings> = listOf(
            Settings(0, "Theme", "Light"),
            Settings(1, "Language", "EN"),
            Settings(2, "Font Family", "xd?"),
            Settings(3, "Font Size", "16?"),
            Settings(4, "Font Color", "black"),
            Settings(5, "Remove Ads", ""),
            Settings(6, "About us", "")
        )
    }
}
