package com.chaboi.breakaway.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chaboi.breakaway.core.util.prepend
import com.chaboi.breakaway.core.util.update

class InfiniteBindableAdapter : RecyclerView.Adapter<BindableViewHolder>() {
    var items: MutableList<AdapterItem> = mutableListOf()
    val firstElementPosition = INITIAL_POSITION
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

    override fun getItemCount() = if (items.isNotEmpty()) 1000 else 0

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<AdapterItem>?) {
        newItems?.let {
            items.update(newItems)
            notifyDataSetChanged()
        }
    }

    fun appendItem(item: AdapterItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun prependItem(item: AdapterItem) {
        items.prepend(item)
        notifyItemInserted(0)
    }

    fun replaceItem(index: Int, item: AdapterItem) {
        items[index] = item
        notifyItemChanged(index)
    }
}