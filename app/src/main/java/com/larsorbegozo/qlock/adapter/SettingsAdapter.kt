package com.larsorbegozo.qlock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.larsorbegozo.qlock.R
import com.larsorbegozo.qlock.data.model.Settings
import com.larsorbegozo.qlock.adapter.SettingsAdapter.SettingsViewHolder
import com.larsorbegozo.qlock.databinding.SettingsItemRvBinding
import com.larsorbegozo.qlock.ui.viewmodel.ClockViewModel

class SettingsAdapter(private val settingsList: List<Settings>, private val viewModel: ClockViewModel, private val listener: OnItemClickListener) : RecyclerView.Adapter<SettingsViewHolder>() {
    inner class SettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = SettingsItemRvBinding.bind(view)

        private val arrayThemes = arrayOf("Light", "Dark", "Schizophrenia")

        fun render(item: Settings) {
            val id = item.id
            binding.settingsItemTitle.text = item.name
            listener.updateSelectedItemIndex() // Called here to initialize viewModel.getSelectedItemIndex, if it won't be initialized, it will select incorrect item at first
            when (id) { // Differentiate each button according to its ID
                0 -> {
                    binding.cardView.setOnClickListener {
                        val builder = AlertDialog.Builder(itemView.context) // TODO: Change it for Material?
                        builder
                            .setTitle("Choose Theme")
                            .setSingleChoiceItems(arrayThemes, viewModel.getSelectedItemIndex) { _, i ->
                                listener.changeSelectedItem(i)
                            }
                            .setPositiveButton("OK") { _, _ ->
                                if(arrayThemes[viewModel.getSelectedItemIndex] == "Dark") {
                                    viewModel.setTheme(true)
                                } else {
                                    viewModel.setTheme(false)
                                }
                            }
                            .setNegativeButton("NO") { _, _ ->
                                Toast.makeText(builder.context, "UARNSEW", Toast.LENGTH_SHORT).show()
                            }
                            .create()
                            .show()
                    }
                }
                1 -> {
                    binding.cardView.setOnClickListener {
                        Toast.makeText(itemView.context, "UNO", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    binding.cardView.setOnClickListener {
                        Toast.makeText(itemView.context, "DEAAAAAAAAAA", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun updateSelectedItemIndex()
        fun changeSelectedItem(value: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SettingsViewHolder(layoutInflater.inflate(R.layout.settings_item_rv, parent, false))
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val item = settingsList[position]
        holder.render(item)
    }
}