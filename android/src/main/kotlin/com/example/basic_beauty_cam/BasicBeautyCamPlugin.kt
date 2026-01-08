package com.example.basic_beauty_cam

import io.flutter.embedding.android.KeyData.CHANNEL
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel

/** BasicBeautyCamPlugin */
class BasicBeautyCamPlugin : FlutterPlugin {
//    private lateinit var channel: MethodChannel
    private val viewTypeId = "basic_beauty_cam"

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        flutterPluginBinding.platformViewRegistry.registerViewFactory(
            viewTypeId,
            NativeViewFactory(flutterPluginBinding.binaryMessenger)
        )

//        channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
//        channel.setMethodCallHandler(null)
    }
}

