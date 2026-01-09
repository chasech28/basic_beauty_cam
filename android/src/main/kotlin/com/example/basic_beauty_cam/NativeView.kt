package com.example.basic_beauty_cam

import CameraApi
import CameraStreamCallback
import ImageFrame
import android.content.Context
import android.util.Log
import android.view.View
import com.example.basic_beauty_cam.AICameraGLSurfaceView.Companion.BEAUTY
import io.flutter.plugin.platform.PlatformView

internal class NativeView(
    private val context: Context,
    private val id: Int,
    private val creationParams: Map<String?, Any?>?,
    private val cameraStreamCallback: CameraStreamCallback,
) :
    PlatformView, CameraApi {

    private val cameraView = AICameraGLSurfaceView.getInstance(context, null)
    private var isStreaming = false

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
        callback(Result.success(Unit))
    }

    override fun takePicture(callback: (Result<String?>) -> Unit) {
        cameraView.takePicture(
            { bitmap ->
                val imagePath = FileUtil.saveBitmapToCache(context, bitmap)
                callback.invoke(Result.success(imagePath))
            },
            null, BEAUTY, 1f, true
        )
    }

    override fun enableBeauty(callback: (Result<Unit>) -> Unit) {
        cameraView.enableBeauty(true)
        callback(Result.success(Unit))
    }

    override fun disableBeauty(callback: (Result<Unit>) -> Unit) {
        cameraView.enableBeauty(false)
        callback(Result.success(Unit))
    }

    override fun startImageStream(callback: (Result<Unit>) -> Unit) {
        isStreaming = true
        cameraView.startShotTimer()
        callback(Result.success(Unit))
    }

    override fun stopImageStream(callback: (Result<Unit>) -> Unit) {
        isStreaming = false
        cameraView.stopShotTimer()
        callback(Result.success(Unit))
    }

    companion object {
        const val TAG = "NativeView"
    }

    /**
     * Helper method to send image frames from native code to Flutter
     * Call this method from your camera frame callback
     */
    fun sendImageFrameToFlutter(bytes: ByteArray, width: Int, height: Int, rotation: Int) {
        if (isStreaming) {
            val frame = ImageFrame(
                bytes = bytes,
                width = width.toLong(),
                height = height.toLong(),
                rotation = rotation.toLong()
            )
            cameraStreamCallback.onImageFrame(frame) { result ->
                // Handle result if needed (optional)
            }
        }
    }
}
