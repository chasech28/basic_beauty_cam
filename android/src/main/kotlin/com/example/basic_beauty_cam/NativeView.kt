import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import io.flutter.plugin.platform.PlatformView
import org.wysaid.myUtils.FileUtil

class NativeView(
    private val context: Context,
    private val id: Int,
    private val creationParams: Map<String?, Any?>?,
    private var cameraStreamProcessor: ImageFrameProcessor
) :
    PlatformView, CameraApi {

    private val cameraView = AICameraGLSurfaceView(context, null)
    private val mainHandler = Handler(Looper.getMainLooper())



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
        
        // Stop image stream
        cameraView.stopImageStream()
        cameraView.setOnImageFrameCallback(null)

        cameraView.onPause()
        // Clean up camera view and release camera hardware resources
        cameraView.release {}

        // Clean up main handler
        mainHandler.removeCallbacksAndMessages(null)
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
        cameraView.enableBeauty()
        callback(Result.success(Unit))
    }

    override fun disableBeauty(callback: (Result<Unit>) -> Unit) {
        cameraView.disableBeauty()
        callback(Result.success(Unit))
    }

    override fun startImageStream(callback: (Result<Unit>) -> Unit) {
        cameraView.setOnImageFrameCallback { bitmap ->
            sendImageFrameToFlutter(bitmap)
        }
        cameraView.startImageStream()
        callback(Result.success(Unit))
    }

    override fun stopImageStream(callback: (Result<Unit>) -> Unit) {
        cameraView.stopImageStream()
        callback(Result.success(Unit))
    }

    companion object {
        const val TAG = "NativeView"
    }

    private fun sendImageFrameToFlutter(bitmap: Bitmap) {
        val bytes = FileUtil.bitmapToBytes(bitmap)

        //todo 降低内存开销
        val frame = ImageFrame(
            bytes = bytes,
            width = bitmap.width.toLong(),
            height = bitmap.height.toLong(),
            rotation = 0L
        )
        Log.d(TAG, "width: ${bitmap.width}, height: ${bitmap.height} bytes: ${bytes.size}")
        
        mainHandler.post {
            cameraStreamProcessor.onImageFrame(frame) { result ->
                // Handle result if needed (optional)
            }
        }
    }
}
