package com.larsorbegozo.qlock

import android.app.Application
import com.google.android.gms.ads.MobileAds

class QlockApp: Application() {
    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this)
    }
}