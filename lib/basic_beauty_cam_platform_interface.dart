import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'basic_beauty_cam_method_channel.dart';

abstract class BasicBeautyCamPlatform extends PlatformInterface {
  /// Constructs a BasicBeautyCamPlatform.
  BasicBeautyCamPlatform() : super(token: _token);

  static final Object _token = Object();

  static BasicBeautyCamPlatform _instance = MethodChannelBasicBeautyCam();

  /// The default instance of [BasicBeautyCamPlatform] to use.
  ///
  /// Defaults to [MethodChannelBasicBeautyCam].
  static BasicBeautyCamPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BasicBeautyCamPlatform] when
  /// they register themselves.
  static set instance(BasicBeautyCamPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
