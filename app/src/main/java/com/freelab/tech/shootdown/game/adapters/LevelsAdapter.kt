package com.freelab.tech.shootdown.game.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freelab.tech.shootdown.databinding.ViewHolderLevelItemsBinding

class LevelsAdapter(
    private val levelList: List<String>,
    private val listener: (level: Int) -> Unit
): RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ViewHolderLevelItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(level: String) {
            binding.levelBtn.text = level
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHolderLevelItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return levelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val level = levelList[position]

        holder.bindItems(level)

        holder.itemView.setOnClickListener {
            listener.invoke(position)
        }
    }

}