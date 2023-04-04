package com.larsorbegozo.qlock.ui

import android.content.Context
import androidx.core.content.ContextCompat
import com.larsorbegozo.qlock.R

class LightTheme: QlockTheme {
    override fun mainActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.white)
    }

    override fun mainActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

    override fun mainActivityButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

    override fun mainActivityLogoColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

    override fun id(): Int {
        return 0
    }
}

class NightTheme: QlockTheme {
    override fun mainActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

    override fun mainActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.white)
    }

    override fun mainActivityButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.white)
    }

    override fun mainActivityLogoColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.white)
    }

    override fun id(): Int {
        return 1
    }
}