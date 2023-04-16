package com.larsorbegozo.qlock

import android.content.Context
import android.content.res.ColorStateList
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.Toast
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import com.google.android.gms.ads.AdRequest
import com.larsorbegozo.qlock.databinding.ActivityMainBinding
import com.larsorbegozo.qlock.ui.LightTheme
import com.larsorbegozo.qlock.ui.NightTheme
import com.larsorbegozo.qlock.ui.QlockTheme
import java.io.File
import java.util.*

class MainActivity : ThemeActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isNight = true
    private var isChecked: Boolean = false

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

        // Flashlight button
        binding.flashlightButton.setOnClickListener {
            toggleFlashLight()
        }
    }

    // Flashlight functionality
    private fun toggleFlashLight() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if(!checkEmulatorFiles()) {
            if(!isChecked) {
                try {
                    cameraManager.setTorchMode(cameraId, true)
                    isChecked = true
                } catch (e: CameraAccessException) {

                }
            } else {
                try {
                    cameraManager.setTorchMode(cameraId, false)
                    isChecked = false
                } catch (e: CameraAccessException) {

                }
            }
        } else {
            Toast.makeText(this, getString(R.string.flashlight_emulator_error), Toast.LENGTH_LONG).show()
        }
    }

    // Check if the device is an emulator or not
    private val GENY_FILES = arrayOf(
        "/dev/socket/genyd",
        "/dev/socket/baseband_genyd"
    )
    private val PIPES = arrayOf(
        "/dev/socket/qemud",
        "/dev/qemu_pipe"
    )
    private val X86_FILES = arrayOf(
        "ueventd.android_x86.rc",
        "x86.prop",
        "ueventd.ttVM_x86.rc",
        "init.ttVM_x86.rc",
        "fstab.ttVM_x86",
        "fstab.vbox86",
        "init.vbox86.rc",
        "ueventd.vbox86.rc"
    )
    private val ANDY_FILES = arrayOf(
        "fstab.andy",
        "ueventd.andy.rc"
    )
    private val NOX_FILES = arrayOf(
        "fstab.nox",
        "init.nox.rc",
        "ueventd.nox.rc"
    )
    fun checkFiles(targets: Array<String>): Boolean {
        for (pipe in targets) {
            val file = File(pipe)
            if (file.exists()) {
                return true
            }
        }
        return false
    }

    fun checkEmulatorFiles(): Boolean {
        return (checkFiles(GENY_FILES)
                || checkFiles(ANDY_FILES)
                || checkFiles(NOX_FILES)
                || checkFiles(X86_FILES)
                || checkFiles(PIPES))
    }


    // Set light/night theme
    override fun syncTheme(appTheme: AppTheme) {
        val qlockTheme = appTheme as QlockTheme

        binding.root.setBackgroundColor(qlockTheme.mainActivityBackgroundColor(this))

        binding.cardViewSetTheme.backgroundTintList = ColorStateList.valueOf(qlockTheme.mainActivityButtonColor(this))
        binding.themeIcon.setColorFilter(qlockTheme.mainActivityBackgroundColor(this))

        binding.cardViewFlashlight.backgroundTintList = ColorStateList.valueOf(qlockTheme.mainActivityButtonColor(this))
        binding.flashlightButton.backgroundTintList = ColorStateList.valueOf(qlockTheme.mainActivityButtonColor(this))
        binding.flashlightButton.iconTint = ColorStateList.valueOf(qlockTheme.mainActivityBackgroundColor(this))

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