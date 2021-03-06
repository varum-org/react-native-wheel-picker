package com.wheel_picker

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

class WheelPickerPackage : ReactPackage {
	override fun createNativeModules(
			reactContext: ReactApplicationContext): MutableList<NativeModule> {
		return mutableListOf()
	}
	
	override fun createViewManagers(
		
			reactContext: ReactApplicationContext): MutableList<ViewManager<*, *>> {
		return mutableListOf(
			WheelPickerManager()
		)
	}
}
