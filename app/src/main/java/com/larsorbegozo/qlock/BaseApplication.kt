package com.larsorbegozo.qlock

import android.app.Application
import com.larsorbegozo.qlock.data.ClockDatabase

class BaseApplication : Application() {
    val clockDatabase: ClockDatabase by lazy { ClockDatabase.getDatabase(this) }
}