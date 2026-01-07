package com.example.basic_beauty_cam

import android.content.Context
import android.util.Log
import android.view.View
import io.flutter.plugin.platform.PlatformView

internal class NativeView(context: Context, id: Int, creationParams: Map<String?, Any?>?) :
    PlatformView {

    private val cameraView = AICameraGLSurfaceView.getInstance( context,  null)
//    private val cameraView = AICameraGLSurfaceView(context, null)

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

    companion object {
        const val TAG = "NativeView"
    }
}
