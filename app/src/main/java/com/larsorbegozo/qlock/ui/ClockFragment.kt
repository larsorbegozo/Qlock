package com.larsorbegozo.qlock.ui

import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.navigation.fragment.findNavController
import com.larsorbegozo.qlock.R
import com.larsorbegozo.qlock.databinding.FragmentClockBinding


class ClockFragment : Fragment() {

    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClockBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            val action = ClockFragmentDirections.actionClockFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
    }

    //TODO: Clock format doesn't change when go 12hour to 24hour and vice-versa, until the app is restarted
}