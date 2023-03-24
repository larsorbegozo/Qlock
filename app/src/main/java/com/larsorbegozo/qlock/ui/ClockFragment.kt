package com.larsorbegozo.qlock.ui

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.larsorbegozo.qlock.data.DataStoreManager
import com.larsorbegozo.qlock.databinding.FragmentClockBinding
import com.larsorbegozo.qlock.ui.viewmodel.ClockViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class ClockFragment : Fragment() {

    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ClockViewModel
    private lateinit var dataStoreManager: DataStoreManager

    private val arrayThemes = arrayOf("Light", "Dark", "Schizophrenia") // TODO: Add more themes and create them

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

        updateSelectedItemIndex() // Called here to initialize viewModel.getSelectedItemIndex, if it won't be initialized, it will select incorrect item at first
        binding.themeButton.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder
                .setTitle("Choose Theme")
                .setSingleChoiceItems(arrayThemes, viewModel.getSelectedThemeIndex) { _, i ->
                    changeSelectedTheme(i)
                }
                .setPositiveButton("OK") { _, _ ->
                    if(arrayThemes[viewModel.getSelectedThemeIndex] == "Dark") { // TODO: Add configuration for more themes
                        viewModel.setTheme(true)
                    } else {
                        viewModel.setTheme(false)
                    }
                }
                .setNegativeButton("NO") { _, _ ->
                    Toast.makeText(builder.context, "UARNSEW", Toast.LENGTH_SHORT).show()
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