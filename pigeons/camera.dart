import 'package:pigeon/pigeon.dart';

@HostApi()
abstract class CameraApi {
  @async
  void switchCamera();

  @async
  void takePicture();

  @async
  void enableBeauty();

  @async
  void disableBeauty();
}
