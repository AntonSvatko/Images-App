package com.tony.imagemvvm.ui.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ImageDecoration : RecyclerView.ItemDecoration(){

    private val spacing = 4f

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val totalSpanCount = getTotalSpanCount(parent)

        outRect.top = if (isInTheFirstRow(position, totalSpanCount)) 0 else spacing.toInt()
        outRect.left = if (isFirstInRow(position, totalSpanCount)) 0 else (spacing / 2).toInt()
        outRect.right = if (isLastInRow(position, totalSpanCount)) 0 else (spacing / 2).toInt()
        outRect.bottom = 0
    }

    private fun isInTheFirstRow(position: Int, spanCount: Int): Boolean {
        return position < spanCount
    }

    private fun isFirstInRow(position: Int, spanCount: Int): Boolean {
        return position % spanCount == 0
    }

    private fun isLastInRow(position: Int, spanCount: Int): Boolean {
        return isFirstInRow(position + 1, spanCount)
    }

    private fun getTotalSpanCount(parent: RecyclerView): Int {
        val layoutManager = parent.layoutManager
        return if (layoutManager is GridLayoutManager) layoutManager.spanCount else 1
    }

}