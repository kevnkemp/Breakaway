package com.chaboi.breakaway.core.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import com.chaboi.breakaway.R

interface AdapterItem {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
        get() = 0
}