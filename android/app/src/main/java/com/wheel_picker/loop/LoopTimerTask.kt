package com.wheel_picker.loop

import java.util.*
import kotlin.math.abs

internal class LoopTimerTask(
		private val loopView: LoopView,
		private val velocityY: Float
) : TimerTask() {
    var a: Float = Int.MAX_VALUE.toFloat()

    override fun run() {
        loopView.apply {
            if (a == Int.MAX_VALUE.toFloat()) {
                a = if (abs(velocityY) > 2000f) {
                    if (velocityY > 0.0f) 2000f else -2000f
                } else velocityY
            }
            if (abs(a) in 0.0f..20f) {
                cancelFuture()
                handlerMessage.sendEmptyMessage(2000)
                return
            }
            val i = (a * 10f / 1000f).toInt()
            totalScrollY -= i
            if (!isLoop) {
                val itemHeight = lineSpacingMultiplier * maxTextHeight
                if (totalScrollY <= ((-initPosition).toFloat() * itemHeight).toInt()) {
                    a = 40f
                    totalScrollY = ((-initPosition).toFloat() * itemHeight).toInt()
                } else if (
                    totalScrollY >= ((arrayList.size - 1 - initPosition).toFloat() * itemHeight).toInt()
                ) {
                    totalScrollY =
                        ((arrayList.size - 1 - initPosition).toFloat() * itemHeight).toInt()
                    a = -40f
                }
            }
            a = if (a < 0.0f) {
                a + 20f
            } else {
                a - 20f
            }
            handlerMessage.sendEmptyMessage(1000)
        }
    }
}
