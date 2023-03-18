package com.larsorbegozo.qlock.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.larsorbegozo.qlock.R
import com.larsorbegozo.qlock.adapter.SettingsAdapter
import com.larsorbegozo.qlock.adapter.SettingsProvider
import com.larsorbegozo.qlock.data.DataStoreManager
import com.larsorbegozo.qlock.databinding.FragmentSettingsBinding
import com.larsorbegozo.qlock.ui.viewmodel.ClockViewModel

class SettingsFragment : Fragment(), SettingsAdapter.OnItemClickListener {

    private lateinit var viewModel: ClockViewModel
    private lateinit var dataStoreManager: DataStoreManager

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this@SettingsFragment)[ClockViewModel::class.java]
        dataStoreManager = DataStoreManager(requireContext())
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SettingsAdapter(SettingsProvider.settingsList, viewModel, this)

        binding.apply {
            toolbar.setNavigationIcon(R.drawable.arrow_back)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            recyclerViewMenu.adapter = adapter
            recyclerViewMenu.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun updateSelectedItemIndex() {
        binding.apply {
            viewModel.getSelectedItem.observe(this@SettingsFragment) { item ->
                viewModel.getSelectedItemIndex = item
            }
        }
    }

    override fun changeSelectedItem(value: Int) {
        viewModel.setSelectedItem(value)
    }
}