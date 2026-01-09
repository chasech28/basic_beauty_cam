package com.example.basic_beauty_cam

import ImageFrameProcessor
import android.content.Context
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class NativeViewFactory(private val messenger: BinaryMessenger) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val creationParams = args as Map<String?, Any?>?
        val cameraStreamProcessor = ImageFrameProcessor(messenger)
        // Set up CameraStreamCallback for sending image frames to Flutter
        val nativeView = NativeView(context, viewId, creationParams, cameraStreamProcessor)

        // Set up CameraApi with NativeView as the implementation
        CameraApi.setUp(messenger, nativeView)

        return nativeView
    }
}