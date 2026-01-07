import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'basic_beauty_cam_platform_interface.dart';

/// An implementation of [BasicBeautyCamPlatform] that uses method channels.
class MethodChannelBasicBeautyCam extends BasicBeautyCamPlatform {
  static const channel = "com.example.basic_beauty_cam/camera_control";

  @visibleForTesting
  final methodChannel = const MethodChannel(channel);

  @override
  Future<void> switchCamera() async {
    try {
      await methodChannel.invokeMethod('switchCamera');
    } on PlatformException catch (e) {
      debugPrint('Failed to switch camera: ${e.message}');
    }
  }

  @override
  Future<void> takePicture() async {
    try {
      await methodChannel.invokeMethod('takePicture');
    } on PlatformException catch (e) {
      debugPrint('Failed to take picture: ${e.message}');
    }
  }

  @override
  Future<void> enableBeauty() async {
    try {
      await methodChannel.invokeMethod('enableBeauty');
    } on PlatformException catch (e) {
      debugPrint('Failed to enable beauty: ${e.message}');
    }
  }

  @override
  Future<void> disableBeauty() async {
    try {
      await methodChannel.invokeMethod('enableBeauty');
    } on PlatformException catch (e) {
      debugPrint('Failed to disable beauty: ${e.message}');
    }
  }
}
