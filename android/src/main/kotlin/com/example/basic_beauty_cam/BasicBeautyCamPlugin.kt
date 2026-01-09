package com.example.basic_beauty_cam

import io.flutter.embedding.android.KeyData.CHANNEL
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel

/** BasicBeautyCamPlugin */
class BasicBeautyCamPlugin : FlutterPlugin {
    private val viewTypeId = "basic_beauty_cam"

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        // Register native view factory
        flutterPluginBinding.platformViewRegistry.registerViewFactory(
            viewTypeId,
            NativeViewFactory(flutterPluginBinding.binaryMessenger)
        )
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        // Cleanup if needed
    }
}        
