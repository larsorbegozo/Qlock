package com.larsorbegozo.qlock.adapter

import com.larsorbegozo.qlock.data.model.Settings

class SettingsProvider {
    companion object {
        val settingsList: List<Settings> = listOf(
            Settings(0, "Theme"),
            Settings(1, "Language"),
            Settings(2, "Font settings"),
            Settings(3, "Remove Ads"),
            Settings(4, "About us")
        )
    }
}
