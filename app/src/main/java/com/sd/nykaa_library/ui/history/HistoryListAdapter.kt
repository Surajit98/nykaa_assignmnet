package com.sd.nykaa_library.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sd.nykaa_library.R
import com.sd.nykaa_library.data.database.entity.Sessions
import com.sd.nykaa_library.databinding.ListItemHistoryBinding


class HistoryListAdapter() : RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    private var items: ArrayList<Sessions> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_history, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<Sessions>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

    }

    inner class ViewHolder(val binding: ListItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sessions) {
            binding.session = item

        }

    }
}