import 'package:pigeon/pigeon.dart';

// Define data class for image frame
class ImageFrame {
  Uint8List bytes;
  int width;
  int height;
  int rotation;

  ImageFrame({
    required this.bytes,
    required this.width,
    required this.height,
    required this.rotation,
  });
}

// FlutterApi: Native → Flutter callback for receiving image frames
@FlutterApi()
abstract class ImageFrameProcessor {
  void onImageFrame(ImageFrame frame);
}

// HostApi: Flutter → Native method calls
@HostApi()
abstract class CameraApi {
  @async
  void switchCamera();

  @async
  String? takePicture();

  @async
  void enableBeauty();

  @async
  void disableBeauty();

  @async
  void startImageStream();

  @async
  void stopImageStream();
}
