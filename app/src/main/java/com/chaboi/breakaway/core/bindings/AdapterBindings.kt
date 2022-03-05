package com.chaboi.breakaway.core.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager2.widget.ViewPager2
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.core.adapter.BindableAdapter
import com.chaboi.breakaway.core.adapter.InfiniteBindableAdapter

@BindingAdapter("setItems")
fun bindItems(recyclerView: RecyclerView, items: List<AdapterItem>?) {
    val adapter = getOrCreateAdapter(recyclerView)
    adapter.updateItems(items)
}

@BindingAdapter("itemAnimator")
fun setItemAnimator(recyclerView: RecyclerView, shouldSet: Boolean) {
    if (shouldSet) {
        recyclerView.itemAnimator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }
    }
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

@BindingAdapter("viewPagerInfiniteAdapter")
fun setViewPagerInfiniteAdapter(viewPager: ViewPager2, adapter: InfiniteBindableAdapter) {
    viewPager.adapter = adapter
    adapter.apply {
        viewPager.setCurrentItem(this.firstElementPosition, false)
    }
}

@BindingAdapter("offScreenPageLimit")
fun setOffScreenPageLimit(viewPager: ViewPager2, offScreenPageLimit: Int) {
    viewPager.offscreenPageLimit = offScreenPageLimit
}

@BindingAdapter("pageChangeListener")
fun setPageChangeListener(viewPager: ViewPager2, listener: ViewPager2.OnPageChangeCallback) {
    viewPager.registerOnPageChangeCallback(listener)
}

@BindingAdapter("goToNext")
fun goToNextPage(viewPager: ViewPager2, shouldGoToNext: Boolean) {
    if (shouldGoToNext) viewPager.setCurrentItem(viewPager.currentItem + 1, true)
}

@BindingAdapter("goToPrevious")
fun goToPreviousPage(viewPager: ViewPager2, shouldGoToPrevious: Boolean) {
    if (shouldGoToPrevious) viewPager.setCurrentItem(viewPager.currentItem - 1, true)
}

