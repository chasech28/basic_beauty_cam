import 'basic_beauty_cam_platform_interface.dart';

class BasicBeautyCam {
  static Future<void> switchCamera() async {
    await BasicBeautyCamPlatform.instance.switchCamera();
  }

  static Future<void> takePicture() async {
    await BasicBeautyCamPlatform.instance.takePicture();
  }

  static Future<void> enableBeauty() async {
    await BasicBeautyCamPlatform.instance.enableBeauty();
  }

  static Future<void> disableBeauty() async {
    await BasicBeautyCamPlatform.instance.disableBeauty();
  }
}
