package com.github.yeeun_yun97.toy.mobymovie.ui.tool

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

class RecyclerViewStatusUiTool(
    private val recyclerView: RecyclerView,
    private val shimmerView: ShimmerFrameLayout,
    private val emptyView: View
) {
    init {
        recyclerView.visibility = View.INVISIBLE
        shimmerView.visibility = View.INVISIBLE
        emptyView.visibility = View.INVISIBLE
    }

    fun setLoadingStatus() {
        recyclerView.visibility = View.INVISIBLE
        shimmerView.startShimmer()
        shimmerView.visibility = View.VISIBLE
        emptyView.visibility = View.INVISIBLE
    }

    fun setLoadedStatus() {
        recyclerView.visibility = View.VISIBLE
        shimmerView.visibility = View.INVISIBLE
        shimmerView.stopShimmer()
        emptyView.visibility = View.INVISIBLE
    }

    fun setEmptyStatus() {
        recyclerView.visibility = View.INVISIBLE
        shimmerView.visibility = View.INVISIBLE
        shimmerView.stopShimmer()
        emptyView.visibility = View.VISIBLE
    }


}