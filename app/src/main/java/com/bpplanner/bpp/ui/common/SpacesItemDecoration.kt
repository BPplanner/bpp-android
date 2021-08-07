package com.bpplanner.bpp.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacesItemDecoration(private val horizon: Int, private val vertical: Int, private val gridCount: Int = 1) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = horizon
        outRect.right = horizon
        outRect.bottom = vertical

        if (parent.getChildLayoutPosition(view) < gridCount) {
            outRect.top = 0
        } else {
            outRect.top = vertical
        }
    }
}
