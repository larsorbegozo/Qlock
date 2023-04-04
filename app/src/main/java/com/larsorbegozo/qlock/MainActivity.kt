package com.larsorbegozo.qlock

import android.content.Context
import android.content.res.ColorStateList
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.ToggleButton
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import com.google.android.gms.ads.AdRequest
import com.larsorbegozo.qlock.databinding.ActivityMainBinding
import com.larsorbegozo.qlock.ui.LightTheme
import com.larsorbegozo.qlock.ui.NightTheme
import com.larsorbegozo.qlock.ui.QlockTheme
import java.util.*

class MainActivity : ThemeActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isNight = true
    private var toggleFlashLightOnOff: ToggleButton? = null
    private var cameraManager: CameraManager? = null
    private var getCameraID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // Status Bar hidden
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Initialize Ads
        initLoadAds()

        // Change theme button
        binding.cardViewSetTheme.setOnClickListener {
            isNight = if(isNight) {
                ThemeManager.instance.changeTheme(LightTheme(), it)
                false
            } else {
                ThemeManager.instance.changeTheme(NightTheme(), it)
                true
            }
        }

        // Formatting date
        binding.apply {
            val skeleton = DateFormat.getBestDateTimePattern(Locale.getDefault(), "EEEE, MMMM d, yyyy")
            dayDate.format12Hour = skeleton.format(Calendar.DATE)
            dayDate.format24Hour = skeleton.format(Calendar.DATE)
        }

        // Flashlight
        toggleFlashLightOnOff = findViewById(R.id.flashlight_button)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            getCameraID = cameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun toggleFlashLight(view: View?) {
        if(toggleFlashLightOnOff!!.isChecked) {
            try {
                cameraManager!!.setTorchMode(getCameraID!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        } else {
            try {
                cameraManager!!.setTorchMode(getCameraID!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun finish() {
        super.finish()
        try {
            cameraManager!!.setTorchMode(getCameraID!!, false)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Set light/night theme
    override fun syncTheme(appTheme: AppTheme) {
        val qlockTheme = appTheme as QlockTheme

        binding.root.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.cardViewSetTheme.backgroundTintList = ColorStateList.valueOf(qlockTheme.mainActivityButtonColor(this))
        binding.themeIcon.setColorFilter(qlockTheme.mainActivityBackgroundColor(this))

        binding.cardViewFlashlight.backgroundTintList = ColorStateList.valueOf(qlockTheme.mainActivityButtonColor(this))
        binding.flashlightButton.setTextColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.logoQlock.setColorFilter(qlockTheme.mainActivityLogoColor(this))
        binding.logoQlock.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.dayDate.setTextColor(qlockTheme.mainActivityTextColor(this))
        binding.dayDate.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.clockSeconds.setTextColor(qlockTheme.mainActivityTextColor(this))
        binding.clockSeconds.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.clockAMPM.setTextColor(qlockTheme.mainActivityTextColor(this))
        binding.clockAMPM.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.clock.setTextColor(qlockTheme.mainActivityTextColor(this))
        binding.clock.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        syncStatusBarIconColors(qlockTheme)
    }

    override fun getStartTheme(): AppTheme {
        return NightTheme()
    }

    private fun initLoadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adBanner.loadAd(adRequest)
    }

    private fun syncStatusBarIconColors(theme: QlockTheme) {
        ThemeManager.instance.syncStatusBarIconsColorWithBackground(
            this,
            theme.mainActivityBackgroundColor(this)
        )
        ThemeManager.instance.syncNavigationBarButtonsColorWithBackground(
            this,
            theme.mainActivityBackgroundColor(this)
        )
    }
}