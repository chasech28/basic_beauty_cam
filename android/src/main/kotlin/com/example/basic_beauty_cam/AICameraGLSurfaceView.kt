package com.example.basic_beauty_cam

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import org.wysaid.view.CameraRecordGLSurfaceView

class AICameraGLSurfaceView private constructor(context: Context, attrs: AttributeSet?) :
    CameraRecordGLSurfaceView(context, attrs) {

    companion object {
        const val TAG = "AICameraGLSurfaceView"
        const val BEAUTY = "@beautify face 1 1080 1920"

        @Volatile
        private var instance: AICameraGLSurfaceView? = null

        /**
         * 获取单例实例
         * @param context 上下文（建议使用ApplicationContext以避免内存泄漏）
         * @param attrs 属性集
         * @return AICameraGLSurfaceView单例实例
         */
        @JvmStatic
        fun getInstance(context: Context, attrs: AttributeSet?): AICameraGLSurfaceView {
            return instance ?: synchronized(this) {
                instance ?: AICameraGLSurfaceView(context, attrs).also {
                    instance = it
                }
            }
        }
    }

    // HandlerThread for background processing
    private val handlerThread = HandlerThread("CameraHandlerThread").apply {
        start()
    }
    private val handler = Handler(handlerThread.looper)
    
    // 定时器相关
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

    /**
     * Set callback to receive image frames
     * @param callback Callback that receives Bitmap when a new frame is captured
     */
    fun setOnImageFrameCallback(callback: ((Bitmap) -> Unit)?) {
        this.onImageFrameCallback = callback
    }

    fun startShotTimer() {
        Log.d(TAG, "timer start")
        if (!isTimerRunning) {
            isTimerRunning = true
            handler.post(shotRunnable)
        }
    }

    fun stopShotTimer() {
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
    
//    fun release(onComplete: () -> Unit) {
//        Log.d(TAG, "release")
//        stopShotTimer()
//        handlerThread.quitSafely()
//        onComplete()
//    }

    fun enableBeauty(enable: Boolean) {
        if (enable) {
            setFilterWithConfig(BEAUTY)
        } else {
            setFilterWithConfig("")
        }
    }

    init {
        Log.d(TAG, "AICameraGLSurfaceView init")

        switchCamera()
        setFitFullView(true)
        setPictureSize(1080, 1920, true)

        setOnCreateCallback {
            startShotTimer()
        }
    }
}