package com.chaboi.breakaway.core.util

import com.chaboi.breakaway.core.adapter.AdapterItem

fun MutableList<AdapterItem>.update(items: List<AdapterItem>) {
    this.clear()
    this.addAll(items)
}

fun MutableList<AdapterItem>.prepend(item: AdapterItem) {
    this.add(0, item)
}