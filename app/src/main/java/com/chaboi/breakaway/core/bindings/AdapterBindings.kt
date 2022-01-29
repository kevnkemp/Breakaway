package com.chaboi.breakaway.core.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.core.adapter.BindableAdapter

@BindingAdapter("setItems")
fun bindItems(recyclerView: RecyclerView, items: List<AdapterItem>?) {
    val adapter = getOrCreateAdapter(recyclerView)
    adapter.updateItems(items)
}

private fun getOrCreateAdapter(recyclerView: RecyclerView): BindableAdapter {
    return if (recyclerView.adapter != null || recyclerView.adapter is BindableAdapter) {
        recyclerView.adapter as BindableAdapter
    } else {
        val bindableAdapter = BindableAdapter()
        recyclerView.adapter = bindableAdapter
        bindableAdapter
    }
}