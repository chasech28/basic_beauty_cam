package com.example.basic_beauty_cam

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.util.Log
import org.wysaid.view.CameraRecordGLSurfaceView

class AICameraGLSurfaceView(context: Context, attrs: AttributeSet?) :
    CameraRecordGLSurfaceView(context, attrs) {

    companion object {
        const val TAG = "AICameraGLSurfaceView"
        const val BEAUTY = "@beautify face 1 1080 1920"
    }

    // HandlerThread for background processing
    private val handlerThread = HandlerThread("CameraHandlerThread").apply {
        start()
    }
    private val handler = Handler(handlerThread.looper)

    // 定时器相关
    private var isTimerEnabled = false
    private var isTimerRunning = false
    private val delayMillis = 1000L

    // Image frame callback
    private var onImageFrameCallback: ((Bitmap) -> Unit)? = null

    private val shotRunnable = object : Runnable {
        override fun run() {
            if (isTimerRunning) {
                takeShot { bitmap ->
                    Log.d(TAG, "takeShot")
                    onImageFrameCallback?.invoke(bitmap)
                }
                handler.postDelayed(this, delayMillis)
            }
        }
    }

    init {
        Log.d(TAG, "AICameraGLSurfaceView init")

        presetCameraForward(false)
        setZOrderOnTop(false)
        setZOrderMediaOverlay(true)
        setPictureSize(2048, 2048, true)

        setOnCreateCallback {
            enableBeauty()
        }
    }


    /**
     * Set callback to receive image frames
     * @param callback Callback that receives Bitmap when a new frame is captured
     */
    fun setOnImageFrameCallback(callback: ((Bitmap) -> Unit)?) {
        this.onImageFrameCallback = callback
    }

    fun startImageStream() {
        isTimerEnabled = true
        startShotTimer()
    }

    fun stopImageStream() {
        isTimerEnabled = false
        stopShotTimer()
    }


    private fun startShotTimer() {
        if (isTimerEnabled) {
            Log.d(TAG, "timer start")
            if (!isTimerRunning) {
                isTimerRunning = true
                handler.post(shotRunnable)
            }
        }
    }

    private fun stopShotTimer() {
        isTimerRunning = false
        handler.removeCallbacks(shotRunnable)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        startShotTimer()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        stopShotTimer()
    }

    fun enableBeauty() {
        setFilterWithConfig(BEAUTY)
    }

    fun disableBeauty() {
        setFilterWithConfig("")
    }
}
