import android.content.Context
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class NativeViewFactory(private val messenger: BinaryMessenger) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val creationParams = args as Map<String?, Any?>?
        val imageStreamProcessor = ImageFrameProcessor(messenger)
        // Set up CameraStreamCallback for sending image frames to Flutter
        val nativeView = NativeView(context, viewId, creationParams, imageStreamProcessor)

        // Set up CameraApi with NativeView as the implementation
        CameraApi.setUp(messenger, nativeView)

        return nativeView
    }
}