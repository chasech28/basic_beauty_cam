import 'dart:typed_data';
import 'package:flutter/material.dart';

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

  /// Set the callback for receiving image frames from native code
  static void setImageProcessor(ImageFrameProcessor? processor) {
    ImageFrameProcessor.setUp(processor ?? _ImageProcessor());
  }
}

/// Internal adapter to convert FlutterApi callback to user callback
class _ImageProcessor implements ImageFrameProcessor {
  @override
  void onImageFrame(ImageFrame frame) {
    debugPrint(
      'Received image frame: ${frame.width}x${frame.height}, rotation: ${frame.rotation}, bytes length: ${frame.bytes.length}',
    );
  }
}
