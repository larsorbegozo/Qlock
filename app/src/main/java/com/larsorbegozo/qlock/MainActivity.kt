package com.larsorbegozo.qlock

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.larsorbegozo.qlock.databinding.ActivityMainBinding
import com.larsorbegozo.qlock.ui.ClockFragment
import com.larsorbegozo.qlock.ui.SettingsFragment
import com.larsorbegozo.qlock.ui.viewmodel.ClockViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var viewModel: ClockViewModel

    private var toggleFlashLightOnOff: ToggleButton? = null
    private var cameraManager: CameraManager? = null
    private var getCameraID: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@MainActivity)[ClockViewModel::class.java]

        checkThemeMode()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        toggleFlashLightOnOff = findViewById(R.id.flashlight_button)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            getCameraID = cameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                when (f) {
                    is SettingsFragment -> {
                        binding.flashlightButton.visibility = View.GONE
                    }
                    is ClockFragment -> {
                        binding.flashlightButton.visibility = View.VISIBLE
                    }
                }
            }
        }, true)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun finish() {
        super.finish()
        try {
            cameraManager!!.setTorchMode(getCameraID!!, false)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(this@MainActivity) { isDarkMode ->
                when(isDarkMode) {
                    true -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    false -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    }
                }
            }
        }
    }
}