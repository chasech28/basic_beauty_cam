package com.example.basic_beauty_cam

import CameraApi
import android.content.Context
import android.util.Log
import android.view.View
import com.example.basic_beauty_cam.AICameraGLSurfaceView.Companion.BEAUTY
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView

internal class NativeView(
    private val context: Context,
    id: Int,
    creationParams: Map<String?, Any?>?,
) :
    PlatformView, CameraApi {

    private val cameraView = AICameraGLSurfaceView.getInstance(context, null)

    override fun getView(): View {
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

    override fun switchCamera(callback: (Result<Unit>) -> Unit) {
        cameraView.switchCamera()
    }

    override fun takePicture(callback: (Result<Unit>) -> Unit) {
        cameraView.takePicture(
            { bitmap ->
                FileUtil.saveBitmapToCache(context, bitmap)
            },
            null, BEAUTY, 1f, true
        )
    }

    override fun enableBeauty(callback: (Result<Unit>) -> Unit) {
        cameraView.enableBeauty(true)
    }

    override fun disableBeauty(callback: (Result<Unit>) -> Unit) {
        cameraView.enableBeauty(false)
    }

    companion object {
        const val TAG = "NativeView"
    }
}
