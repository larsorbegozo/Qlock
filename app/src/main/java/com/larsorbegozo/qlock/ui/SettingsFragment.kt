package com.larsorbegozo.qlock.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.larsorbegozo.qlock.R
import com.larsorbegozo.qlock.adapter.SettingsAdapter
import com.larsorbegozo.qlock.adapter.SettingsProvider
import com.larsorbegozo.qlock.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        binding.apply {
            toolbar.setNavigationIcon(R.drawable.arrow_back)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewMenu.adapter = SettingsAdapter(SettingsProvider.settingsList)
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(this.context)
    }
}