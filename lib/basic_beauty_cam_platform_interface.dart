import 'package:basic_beauty_cam/basic_beauty_cam_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class BasicBeautyCamPlatform extends PlatformInterface {
  static final Object _token = Object();

  static BasicBeautyCamPlatform _instance = MethodChannelBasicBeautyCam();

  static BasicBeautyCamPlatform get instance => _instance;

  BasicBeautyCamPlatform() : super(token: _token);

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BasicBeautyCamPlatform] when
  /// they register themselves.
  static set instance(BasicBeautyCamPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> switchCamera() {
    throw UnimplementedError('switchCamera() has not been implemented.');
  }

  Future<String?> takePicture() {
    throw UnimplementedError('takePicture() has not been implemented.');
  }

  Future<void> enableBeauty() {
    throw UnimplementedError('enableBeauty() has not been implemented.');
  }

  Future<void> disableBeauty() {
    throw UnimplementedError('disableBeauty() has not been implemented.');
  }

  Future<void> startImageStream() {
    throw UnimplementedError('startImageStream() has not been implemented.');
  }

  Future<void> stopImageStream() {
    throw UnimplementedError('stopImageStream() has not been implemented.');
  }
}
