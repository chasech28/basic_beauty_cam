package com.example.basic_beauty_cam

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import org.wysaid.view.CameraRecordGLSurfaceView

class AICameraGLSurfaceView private constructor(context: Context, attrs: AttributeSet?) :
    CameraRecordGLSurfaceView(context, attrs) {

    companion object {
        private const val TAG = "AICameraGLSurfaceView"
        private const val BEAUTY = "@beautify face 1"

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
                instance ?: AICameraGLSurfaceView(context.applicationContext, attrs).also {
                    instance = it
                }
            }
        }
    }

    // 定时器相关
    private val handler = Handler(Looper.getMainLooper())
    private var isTimerRunning = false
    private val delayMillis = 100L

    private val shotRunnable = object : Runnable {
        var count = 0

        override fun run() {
            if (isTimerRunning) {
                count ++
                Log.d(TAG, "timer: $count")
                handler.postDelayed(this, delayMillis)
            }
        }
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
//        startShotTimer()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
//        stopShotTimer()
    }

    init {
        Log.d(TAG, "AICameraGLSurfaceView init")

        switchCamera()

        setOnCreateCallback {
            setFilterWithConfig(BEAUTY)
        }
    }
}