import 'basic_beauty_cam_platform_interface.dart';
import 'camera.g.dart';

class BasicBeautyCam {
  static Future<void> switchCamera() async {
    await BasicBeautyCamPlatform.instance.switchCamera();
  }

  static Future<String?> takePicture() async {
    return await BasicBeautyCamPlatform.instance.takePicture();
  }

  static Future<void> enableBeauty() async {
    await BasicBeautyCamPlatform.instance.enableBeauty();
  }

  static Future<void> disableBeauty() async {
    await BasicBeautyCamPlatform.instance.disableBeauty();
  }

  static Future<void> startImageStream() async {
    await BasicBeautyCamPlatform.instance.startImageStream();
  }

  static Future<void> stopImageStream() async {
    await BasicBeautyCamPlatform.instance.stopImageStream();
  }

  /// Set callback for receiving image frames from native code
  /// This callback will be invoked for each frame when image stream is active
  static void setImageFrameCallback(ImageFrameCallback? callback) {
    ImageFrameProcessor.setUp(_ImageFrameCallbackAdapter(callback));
  }
}

/// Image frame callback function type
/// Use this to receive image frames sent from native code
typedef ImageFrameCallback = void Function(ImageFrame frame);

/// Internal adapter to bridge FlutterApi callback to user callback
class _ImageFrameCallbackAdapter implements ImageFrameProcessor {
  final ImageFrameCallback? _imageFrameCallback;

  _ImageFrameCallbackAdapter(this._imageFrameCallback);

  @override
  void onImageFrame(ImageFrame frame) {
    _imageFrameCallback?.call(frame);
  }
}
