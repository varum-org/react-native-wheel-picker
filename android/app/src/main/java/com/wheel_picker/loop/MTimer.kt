package com.wheel_picker.loop

import java.util.*
import kotlin.math.abs

internal class MTimer(private val loopView: LoopView, var offset: Int) : TimerTask() {
    private var realTotalOffset: Int = Int.MAX_VALUE
    private var realOffset: Int = 0

    override fun run() {
        if (realTotalOffset == Int.MAX_VALUE) {
            val itemHeight = loopView.lineSpacingMultiplier * loopView.maxTextHeight
            offset = ((offset + itemHeight) % itemHeight).toInt()
            realTotalOffset =
                if (offset.toFloat() > itemHeight / 2.0f) (itemHeight - offset.toFloat()).toInt()
                else -offset
        }
        realOffset = (realTotalOffset.toFloat() * 0.1f).toInt()
        if (realOffset == 0) {
            realOffset = if (realTotalOffset < 0) -1 else 1
        }
        if (abs(realTotalOffset) <= 0) {
            loopView.cancelFuture()
            loopView.handlerMessage.sendEmptyMessage(3000)
        } else {
            loopView.totalScrollY = loopView.totalScrollY + realOffset
            loopView.handlerMessage.sendEmptyMessage(1000)
            realTotalOffset -= realOffset
        }
    }
}
