package com.wheel_picker

import android.graphics.Color
import android.graphics.Typeface
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.facebook.react.views.text.ReactFontManager
import com.wheel_picker.loop.LoopListener
import com.wheel_picker.loop.LoopView
import java.util.*

class WheelPickerManager(
) : SimpleViewManager<LoopView?>(), LoopListener {
	lateinit var wheelPicker:LoopView
	
	override fun getName(): String {
		return REACT_CLASS
	}
	
	override fun createViewInstance(context: ThemedReactContext): LoopView {
		wheelPicker = LoopView(context).apply {
			loopListener = this@WheelPickerManager
		}
		return wheelPicker
	}
	
	@ReactProp(name = "data")
	fun setData(wheelPicker: LoopView?, data: ReadableArray) {
		if (wheelPicker != null) {
			val emptyList: List<String> = ArrayList()
			try {
				val dataInt: MutableList<Int> = ArrayList()
				for (i in 0 until data.size()) {
					dataInt.add(data.getInt(i))
				}
				wheelPicker.arrayList = (dataInt as ArrayList<*>)
			} catch (e: Exception) {
				try {
					val dataString: MutableList<String?> = ArrayList()
					var i = 0
					while (i < data.size()) {
						dataString.add(data.getString(i))
						i++
					}
					wheelPicker.arrayList = (dataString as ArrayList<*>)
				} catch (ex: Exception) {
					ex.printStackTrace()
					wheelPicker.arrayList = (emptyList as ArrayList<*>)
				}
			}
		}
	}
	
	@ReactProp(name = "isCyclic")
	fun setCyclic(wheelPicker: LoopView?, isCyclic: Boolean?) {
		if (wheelPicker != null) {
			wheelPicker.isLoop = isCyclic!!
		}
	}
	
	@ReactProp(name = "selectedItemTextColor")
	fun setSelectedItemTextColor(wheelPicker: LoopView?, selectedItemTextColor: String) {
		wheelPicker?.setSelectedItemTextColor(convertColor(selectedItemTextColor))
	}
	
	@ReactProp(name = "selectedItemTextSize")
	fun setSelectedItemTextSize(wheelPicker: LoopView?, itemTextSize: Int) {
		wheelPicker?.setSelectedItemTextSize(itemTextSize)
	}
	
	@ReactProp(name = "selectedItemTextFontFamily")
	fun setSelectedItemFont(wheelPicker: LoopView?, itemTextFontFamily: String?) {
		if (wheelPicker != null) {
			val typeface = ReactFontManager.getInstance().getTypeface(itemTextFontFamily, Typeface.NORMAL, wheelPicker.context.assets)
			wheelPicker.setSelectedItemFont(typeface)
		}
	}
	
	@ReactProp(name = "indicatorWidth")
	fun setIndicatorWidth(wheelPicker: LoopView?, indicatorSize: Int) {
		wheelPicker?.setIndicatorWidth(indicatorSize)
	}
	
	@ReactProp(name = "hideIndicator")
	fun setIndicator(wheelPicker: LoopView?, renderIndicator: Boolean?) {
		wheelPicker?.hideIndicator()
	}
	
	@ReactProp(name = "indicatorColor")
	fun setIndicatorColor(wheelPicker: LoopView?, indicatorColor: String) {
		wheelPicker?.setIndicatorColor(convertColor(indicatorColor))
	}
	
	@ReactProp(name = "itemTextColor")
	fun setItemTextColor(wheelPicker: LoopView?, itemTextColor: String) {
		wheelPicker?.setItemTextColor(convertColor(itemTextColor))
	}
	
	@ReactProp(name = "itemTextSize")
	fun setItemTextSize(wheelPicker: LoopView?, itemTextSize: Int) {
		wheelPicker?.setItemTextSize(itemTextSize)
	}
	
	@ReactProp(name = "itemTextFontFamily")
	fun setItemFont(wheelPicker: LoopView?, itemTextFontFamily: String?) {
		if (wheelPicker != null) {
			val typeface = ReactFontManager.getInstance().getTypeface(itemTextFontFamily, Typeface.NORMAL, wheelPicker.context.assets)
			wheelPicker.setItemFont(typeface)
		}
	}
	
	@ReactProp(name = "initPosition")
	fun setInitialPosition(wheelPicker: LoopView?, selectedItemPosition: Int) {
		if (wheelPicker != null) {
			wheelPicker.initPosition = selectedItemPosition
		}
	}
	
	@ReactProp(name = "backgroundColor")
	fun setBackgroundColor(wheelPicker: LoopView?, backgroundColor: String) {
		wheelPicker?.setBackgroundColor(convertColor(backgroundColor))
	}
	
	@ReactProp(name = "selectedItem")
	fun setSelectedItem(wheelPicker: LoopView?, pos: Int) {
		if (wheelPicker != null) {
			wheelPicker.selectedItem = pos
		}
	}
	
	override fun onItemSelect(view: LoopView, item: Int) {
		val event = Arguments.createMap()
		event.putInt("position", item)
		(wheelPicker.context as ReactContext).getJSModule(RCTEventEmitter::class.java).receiveEvent(
				view.id,
				"topChange",
				event)
	}
	
	private fun convertColor(color: String): Int {
		return if (!color.startsWith("rgb")) {
			Color.parseColor(color)
		} else {
			val colors = color.substring(color.indexOf("(") + 1, color.length - 1).split(",".toRegex()).toTypedArray()
			val red = colors[0].trim { it <= ' ' }.toInt()
			val green = colors[1].trim { it <= ' ' }.toInt()
			val blue = colors[2].trim { it <= ' ' }.toInt()
			var opacity = 1.0
			if (colors.size > 3) {
				opacity = colors[3].trim { it <= ' ' }.toDouble()
			}
			val alpha = (opacity * 255.0f).toInt()
			Color.argb(alpha, red, green, blue)
		}
	}
	
	companion object {
		const val REACT_CLASS = "WheelPicker"
	}
}
