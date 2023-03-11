package com.larsorbegozo.qlock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.larsorbegozo.qlock.R
import com.larsorbegozo.qlock.data.model.Settings
import com.larsorbegozo.qlock.adapter.SettingsAdapter.SettingsViewHolder
import com.larsorbegozo.qlock.databinding.SettingsItemRvBinding

class SettingsAdapter(private val settingsList: List<Settings>) : RecyclerView.Adapter<SettingsViewHolder>() {
    inner class SettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = SettingsItemRvBinding.bind(view)

        fun render(item: Settings) {
            val id = item.id
            binding.settingsItemTitle.text = item.name
            when (id) { // Differentiate each button according to its ID
                0 -> {
                    binding.cardView.setOnClickListener {
                        Toast.makeText(itemView.context, "0", Toast.LENGTH_SHORT).show()
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