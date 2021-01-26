package com.example.imagesearch

import androidx.recyclerview.widget.RecyclerView

abstract class OnSingleScrollListener: RecyclerView.OnScrollListener() {

    private var lastClickTime = 0L

    protected abstract fun OnSingleScrollListener(recyclerView: RecyclerView)

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val now = System.currentTimeMillis()
        if (now - lastClickTime > 700L) {
            OnSingleScrollListener(recyclerView)
            lastClickTime = now
        }
    }

}