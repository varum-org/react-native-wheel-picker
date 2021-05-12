package com.wheel_picker

/**
 * Created by duynn100198 on 5/12/21.
 */

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.modules.core.DeviceEventManagerModule


class ScannerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    @ReactMethod
    fun scan(from:String,to:String) {
        val eventEmitter = reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        eventEmitter.emit("deviceFound", DeviceResult(from, to).deviceToWriteableMap())
    }

    override fun getName(): String {
        return "Scanner"
    }
}

fun DeviceResult.deviceToWriteableMap(): WritableNativeMap? {
    val networkDeviceInfo = WritableNativeMap()
    networkDeviceInfo.putString("ip", this.ip)
    networkDeviceInfo.putString("mac", this.macAddress)
    return networkDeviceInfo
}


data class DeviceResult(var ip: String, var macAddress: String?)