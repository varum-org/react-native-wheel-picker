package com.wheel_picker

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

class ScannerPackage : ReactPackage {
    override fun createNativeModules(
            reactContext: ReactApplicationContext): MutableList<NativeModule> {
        val modules: MutableList<NativeModule> = ArrayList()
        modules.add(ScannerModule(reactContext))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): MutableList<ViewManager<*, *>> {
        return mutableListOf()
    }
}
