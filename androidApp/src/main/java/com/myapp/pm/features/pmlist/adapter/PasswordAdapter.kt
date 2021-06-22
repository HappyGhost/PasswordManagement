package com.myapp.pm.features.pmlist.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.myapp.myapplication.db.entity.PasswordEntity

import com.myapp.pm.databinding.ItemPasswordBinding
import com.myapp.pm.features.uimodel.PasswordUiModel

class PasswordAdapter : RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    var source = listOf<PasswordUiModel>()
    var onItemClick: ((item: PasswordUiModel) -> Unit)? = null
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
        val item = source[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
        holder.tvAccountName.text = item.accountName
        holder.tvUserName.text = item.username
        holder.tvPassHint.text = item.passHint

    }

    override fun getItemCount(): Int = source.size

    inner class ViewHolder(binding: ItemPasswordBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvAccountName: TextView = binding.tvAccountName
        val tvUserName: TextView = binding.tvUserName
        val tvPassHint: TextView = binding.tvHint

        override fun toString(): String {
            return super.toString() + " '" + tvAccountName.text + "'"
        }
    }
}
