import 'package:flutter/foundation.dart';

import 'basic_beauty_cam_platform_interface.dart';
import 'camera.g.dart';

/// An implementation of [BasicBeautyCamPlatform] that uses Pigeon.
class MethodChannelBasicBeautyCam extends BasicBeautyCamPlatform {
  @visibleForTesting
  final cameraApi = CameraApi();

  @override
  Future<void> switchCamera() async {
    try {
      await cameraApi.switchCamera();
    } catch (e) {
      debugPrint('Failed to switch camera: $e');
    }
  }

  @override
  Future<void> takePicture() async {
    try {
      await cameraApi.takePicture();
    } catch (e) {
      debugPrint('Failed to take picture: $e');
    }
  }

  @override
  Future<void> enableBeauty() async {
    try {
      await cameraApi.enableBeauty();
    } catch (e) {
      debugPrint('Failed to enable beauty: $e');
    }
  }

  @override
  Future<void> disableBeauty() async {
    try {
      await cameraApi.disableBeauty();
    } catch (e) {
      debugPrint('Failed to disable beauty: $e');
    }
  }
}
