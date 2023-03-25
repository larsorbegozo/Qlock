package com.larsorbegozo.qlock.ui

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.larsorbegozo.qlock.R
import com.larsorbegozo.qlock.data.DataStoreManager
import com.larsorbegozo.qlock.databinding.FragmentClockBinding
import com.larsorbegozo.qlock.ui.viewmodel.ClockViewModel
import java.util.*


class ClockFragment : Fragment() {

    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ClockViewModel
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this@ClockFragment)[ClockViewModel::class.java]
        dataStoreManager = DataStoreManager(requireContext().applicationContext)
        _binding = FragmentClockBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val light: String = getString(R.string.light)
        val dark: String = getString(R.string.dark)

        val arrayThemes = arrayOf(light, dark)

        updateSelectedItemIndex() // Called here to initialize viewModel.getSelectedItemIndex, if it won't be initialized, it will select incorrect item at first
        binding.themeButton.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext(), R.style.dialogDeLosCojones)
            builder
                .setTitle(R.string.change_theme_title   )
                .setSingleChoiceItems(arrayThemes, viewModel.getSelectedThemeIndex) { _, i ->
                    changeSelectedTheme(i)
                }
                .setPositiveButton(R.string.confirm_change_theme) { _, _ ->
                    if(arrayThemes[viewModel.getSelectedThemeIndex] == dark) {
                        viewModel.setTheme(true)
                    } else {
                        viewModel.setTheme(false)
                    }
                }
                .setNegativeButton(R.string.cancel_change_theme) { _, _ ->
                }
                .show()
        }

        binding.apply {
            val skeleton = DateFormat.getBestDateTimePattern(Locale.getDefault(), "EEEE, MMMM d, yyyy")
            dayDate.format12Hour = skeleton.format(Calendar.DATE)
            dayDate.format24Hour = skeleton.format(Calendar.DATE)
        }
    }

    private fun updateSelectedItemIndex() {
        binding.apply {
            viewModel.getSelectedItem.observe(viewLifecycleOwner) { item ->
                viewModel.getSelectedThemeIndex = item
            }
        }
    }

    private fun changeSelectedTheme(value: Int) {
        viewModel.setSelectedTheme(value)
    }
}