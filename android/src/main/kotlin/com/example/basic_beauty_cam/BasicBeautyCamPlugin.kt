package com.example.basic_beauty_cam

import NativeViewFactory
import io.flutter.embedding.engine.plugins.FlutterPlugin

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
