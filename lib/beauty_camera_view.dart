import 'dart:io';

import 'package:basic_beauty_cam/basic_beauty_cam.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BeautyCameraPreview extends StatefulWidget {
  const BeautyCameraPreview({super.key});

  @override
  State<BeautyCameraPreview> createState() => _BeautyCameraPreviewState();
}

class _BeautyCameraPreviewState extends State<BeautyCameraPreview> {
  @override
  Widget build(BuildContext context) {
    const String viewType = 'basic_beauty_cam';
    final Map<String, dynamic> creationParams = <String, dynamic>{};

    return Platform.isAndroid
        ? buildAndroidView(viewType, creationParams)
        : buildIOSView(viewType, creationParams);
  }

  Widget buildAndroidView(viewType, creationParams) {
    return AndroidView(
      viewType: viewType,
      layoutDirection: TextDirection.ltr,
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
      onPlatformViewCreated: (_) {},
    );
  }

  Widget buildIOSView(viewType, creationParams) {
    return UiKitView(
      viewType: viewType,
      layoutDirection: TextDirection.ltr,
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
      onPlatformViewCreated: (_) {},
    );
  }
}
