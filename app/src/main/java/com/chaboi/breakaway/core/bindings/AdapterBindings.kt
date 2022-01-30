package com.chaboi.breakaway.core.bindings

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
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
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        bindableAdapter
    }
}

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: BindableAdapter) {
    recyclerView.adapter = adapter
}

@BindingAdapter("scrollListener")
fun setScrollListener(recyclerView: RecyclerView, scrollListener: RecyclerView.OnScrollListener) {
    recyclerView.addOnScrollListener(scrollListener)
}

@InverseBindingAdapter(attribute = "currentPosition", event = "currentPositionAttributeChanged")
fun getCurrentPosition(recyclerView: RecyclerView): Int {
    return (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
}

@BindingAdapter(value = ["currentPositionAttributeChanged"])
fun setPositionChangedListener(recyclerView: RecyclerView, listener: InverseBindingListener) {
    val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
    var previousPosition = layoutManager.findFirstVisibleItemPosition()
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dx == 0) return
            val newPosition = layoutManager.findFirstVisibleItemPosition()
            if (previousPosition != newPosition) {
                previousPosition = newPosition
                listener.onChange()
            }
        }
    })
}

@BindingAdapter("currentPosition")
fun setCurrentPosition(recyclerView: RecyclerView, position: Int) {
    (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(position)
}

