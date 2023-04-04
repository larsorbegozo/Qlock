package com.larsorbegozo.qlock.ui

import android.content.Context
import com.dolatkia.animatedThemeManager.AppTheme

interface QlockTheme : AppTheme {
    fun mainActivityBackgroundColor(context: Context): Int
    fun mainActivityTextColor(context: Context): Int
    fun mainActivityButtonColor(context: Context): Int
    fun mainActivityLogoColor(context: Context): Int
}