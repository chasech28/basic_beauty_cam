import 'dart:typed_data';
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

  //   /// Set the callback for receiving image frames from native code
  //   static void setImageStreamCallback(ImageStreamCallback? callback) {
  //     CameraStreamCallback.setUp(_CallbackAdapter(callback));
  //   }
}

// /// Callback interface for receiving image frames from native camera
// abstract class ImageStreamCallback {
//   void onImageFrame(Uint8List bytes, int width, int height, int rotation);
// }

// /// Internal adapter to convert FlutterApi callback to user callback
// class _CallbackAdapter implements CameraStreamCallback {
//   final ImageStreamCallback? userCallback;

//   _CallbackAdapter(this.userCallback);

//   @override
//   void onImageFrame(ImageFrame frame) {
//     if (userCallback != null && frame.bytes != null) {
//       userCallback!.onImageFrame(
//         frame.bytes!,
//         frame.width ?? 0,
//         frame.height ?? 0,
//         frame.rotation ?? 0,
//       );
//     }
//   }
// }
