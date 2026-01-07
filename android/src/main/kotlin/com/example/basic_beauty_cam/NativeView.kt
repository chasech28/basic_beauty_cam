package com.example.basic_beauty_cam

import android.R.attr.bitmap
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.example.basic_beauty_cam.AICameraGLSurfaceView.Companion.BEAUTY
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import org.wysaid.myUtils.ImageUtil
import org.wysaid.view.CameraGLSurfaceView
import java.lang.Compiler.enable

internal class NativeView(
    context: Context,
    id: Int,
    creationParams: Map<String?, Any?>?,
    messenger: BinaryMessenger
) :
    PlatformView, MethodChannel.MethodCallHandler {

    private val cameraView = AICameraGLSurfaceView.getInstance(context, null)
    private val channel = MethodChannel(messenger, CHANNEL)

    private val context = context

    override fun getView(): View {
        channel.setMethodCallHandler(this)
        return cameraView
    }

    override fun onFlutterViewAttached(flutterView: View) {
        super.onFlutterViewAttached(flutterView)
        Log.d(TAG, "onFlutterViewAttached")
        cameraView.onResume()
    }

    override fun onFlutterViewDetached() {
        super.onFlutterViewDetached()
        Log.d(TAG, "onFlutterViewDetached")
        cameraView.onPause()
    }

    override fun dispose() {
        Log.d(TAG, "dispose")

        cameraView.release {}
        cameraView.onPause()
    }

    override fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result
    ) {
        when (call.method) {
            "switchCamera" -> {
                cameraView.switchCamera()
                result.success(null)
            }

            "takePicture" -> {
                cameraView.takePicture(
                    { bitmap ->
                        FileUtil.saveBitmapToCache(context, bitmap)
                    },
                    null, BEAUTY, 1f, true
                )
            }

            "enableBeauty" -> {
                cameraView.enableBeauty(true)
            }

            "disableBeauty" -> {
                cameraView.enableBeauty( false)
            }

//            "setBeautyLevel" -> {
//                val level = call.arguments as Double
//                cameraView.setBeautyLevel(level.toFloat())
//            }

//            "startImageStream" -> {
//                cameraView.startImageStream { bitmap ->
//                    val image = ImageUtil.bitmapToMat(bitmap)
//                    cameraView.processImage(image)
//                }
//            }

            else -> {
                result.notImplemented()
            }
        }
    }

    companion object {
        const val TAG = "NativeView"
    }
}

const val CHANNEL = "com.example.basic_beauty_cam/camera_control"
