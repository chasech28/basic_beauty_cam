
import 'basic_beauty_cam_platform_interface.dart';

class BasicBeautyCam {
  Future<String?> getPlatformVersion() {
    return BasicBeautyCamPlatform.instance.getPlatformVersion();
  }
}
