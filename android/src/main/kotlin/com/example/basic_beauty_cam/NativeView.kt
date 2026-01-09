package com.example.basic_beauty_cam

import CameraApi
import ImageFrame
import ImageFrameProcessor
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.example.basic_beauty_cam.AICameraGLSurfaceView.Companion.BEAUTY
import io.flutter.plugin.platform.PlatformView

internal class NativeView(
    private val context: Context,
    private val id: Int,
    private val creationParams: Map<String?, Any?>?,
    private var cameraStreamProcessor: ImageFrameProcessor
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
        cameraView.startShotTimer()

        // Set up image frame callback from camera view
        cameraView.setOnImageFrameCallback { bitmap ->
            sendImageFrameToFlutter(bitmap)
        }
        callback(Result.success(Unit))
    }

    override fun stopImageStream(callback: (Result<Unit>) -> Unit) {
        cameraView.stopShotTimer()
        callback(Result.success(Unit))
    }

    companion object {
        const val TAG = "NativeView"
    }

    private fun sendImageFrameToFlutter(bitmap: Bitmap) {
        val bytes = FileUtil.bitmapToBytes(bitmap)

        val frame = ImageFrame(
            bytes = bytes,
            width = bitmap.width.toLong(),
            height = bitmap.height.toLong(),
            rotation = 0L
        )
        cameraStreamProcessor.onImageFrame(frame) { result ->
            // Handle result if needed (optional)
        }
    }
}
