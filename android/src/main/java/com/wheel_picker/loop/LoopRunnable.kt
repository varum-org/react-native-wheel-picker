package com.wheel_picker.loop

internal class LoopRunnable(private val loopView: LoopView) : Runnable {

    override fun run() {
        val listener = loopView.loopListener
        val selectedItem = LoopView.getSelectedItem(loopView)
        loopView.arrayList[selectedItem]
        listener?.onItemSelect(loopView, selectedItem)
    }
}
