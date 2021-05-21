package com.myapp.pm.features.pmlist.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.myapp.pm.features.pmlist.adapter.placeholder.PlaceholderContent.PlaceholderItem
import com.myapp.pm.databinding.ItemPasswordBinding

class PasswordAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPasswordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvAccountName.text = item.accountName
        holder.tvUserName.text = item.username
        holder.tvPassHint.text = item.passHint
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemPasswordBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvAccountName: TextView = binding.tvAccountName
        val tvUserName: TextView = binding.tvUserName
        val tvPassHint: TextView = binding.tvHint

        override fun toString(): String {
            return super.toString() + " '" + tvAccountName.text + "'"
        }
    }

}