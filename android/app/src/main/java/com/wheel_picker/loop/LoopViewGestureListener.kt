package com.wheel_picker.loop

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

internal class LoopViewGestureListener(private val loopView: LoopView) : SimpleOnGestureListener() {

    override fun onDown(motionevent: MotionEvent): Boolean {
        loopView.cancelFuture()
        return true
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        loopView.smoothScroll(velocityY)
        return true
    }
}
