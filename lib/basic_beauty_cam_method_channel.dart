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
  Future<String?> takePicture() async {
    try {
      return await cameraApi.takePicture();
    } catch (e) {
      debugPrint('Failed to take picture: $e');
    }
    return null;
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

  @override
  Future<void> startImageStream() async {
    try {
      await cameraApi.startImageStream();
    } catch (e) {
      debugPrint('Failed to start image stream: $e');
    }
  }

  @override
  Future<void> stopImageStream() async {
    try {
      await cameraApi.stopImageStream();
    } catch (e) {
      debugPrint('Failed to stop image stream: $e');
    }
  }
}
