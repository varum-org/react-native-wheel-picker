package com.wheel_picker.loop

import android.os.Handler
import android.os.Looper
import android.os.Message

internal class MessageHandler(private val loopView: LoopView) : Handler(Looper.getMainLooper()) {

    override fun handleMessage(paramMessage: Message) {
        if (paramMessage.what == 1000) loopView.invalidate()
        while (true) {
            if (paramMessage.what == 2000) LoopView.smoothScroll(loopView)
            else if (paramMessage.what == 3000) loopView.itemSelected()
            super.handleMessage(paramMessage)
            return
        }
    }
}
