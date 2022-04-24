package com.chaboi.breakaway.core.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.chaboi.breakaway.R

@BindingAdapter(value = ["imageUrl", "cache", "errorImg"], requireAll = false)
fun bindImageUrl(imageView: ImageView, url: String, cache: Boolean = true, errorImg: Int = R.drawable.ic_nhl_logo) {
    if (url.lowercase().endsWith("svg")) {
        val imageLoader = ImageLoader.Builder(imageView.context)
            .componentRegistry {
                add(SvgDecoder(imageView.context))
            }.build()

        val request = ImageRequest.Builder(imageView.context).apply {
            error(errorImg)
            placeholder(errorImg)
            data(url).decoder(SvgDecoder(imageView.context))
        }.target(imageView).build()

        imageLoader.enqueue(request)
    } else {
        val imageLoader = ImageLoader(imageView.context)

        val request = ImageRequest.Builder(imageView.context).apply {
            if (cache) {
                memoryCachePolicy(CachePolicy.ENABLED)
            } else {
                memoryCachePolicy(CachePolicy.DISABLED)
            }
            error(errorImg)
            placeholder(errorImg)
            data(url)
        }.target(imageView).build()

        imageLoader.enqueue(request)
    }
}