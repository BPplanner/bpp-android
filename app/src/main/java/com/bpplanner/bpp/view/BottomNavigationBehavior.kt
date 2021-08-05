package com.bpplanner.bpp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.bpplanner.bpp.utils.LogUtil
import com.google.android.material.bottomnavigation.BottomNavigationView


class BottomNavigationBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View?>(context, attrs) {

    fun layoutDependsOn(
        parent: CoordinatorLayout?,
        child: BottomNavigationView?,
        dependency: View?
    ): Boolean {
        return dependency is FrameLayout
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (dy < 0) {
            showBottomNavigationView(child)
        } else if (dy > 0) {
            hideBottomNavigationView(child)
        }
    }

    private var isShow = true
    private fun hideBottomNavigationView(view: View) {
        if(!isShow) return
        view.animate().translationY(view.height.toFloat())
        isShow = false
    }

    private fun showBottomNavigationView(view: View) {
        if(isShow) return
        view.animate().translationY(0f)
        isShow = true
    }

}