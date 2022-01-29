package com.chaboi.breakaway.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chaboi.breakaway.BR

class BindableAdapter : RecyclerView.Adapter<BindableViewHolder>() {
    var items: List<AdapterItem> = emptyList()
    private val viewTypeToLayoutId: MutableMap<Int, Int> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewTypeToLayoutId[viewType] ?: 0,
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if (!viewTypeToLayoutId.containsKey(item.viewType)) {
            viewTypeToLayoutId[item.viewType] = item.layoutId
        }
        return item.viewType
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<AdapterItem>? ) {
        items = newItems ?: emptyList()
        notifyDataSetChanged()
    }
}

class BindableViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(adapterItem: AdapterItem) {
        binding.setVariable(BR.item, adapterItem)
    }
}