import 'dart:io';

import 'package:basic_beauty_cam/basic_beauty_cam.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BeautyCameraPreview extends StatefulWidget {
  const BeautyCameraPreview({super.key});

  @override
  State<BeautyCameraPreview> createState() => _BeautyCameraPreviewState();
}

class _BeautyCameraPreviewState extends State<BeautyCameraPreview>
    with WidgetsBindingObserver {
  late final Future<bool> _initializationFuture;

  @override
  void initState() {
    super.initState();
    _initializationFuture = BeautyCameraController.initialize();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    super.didChangeAppLifecycleState(state);

    switch (state) {
      case AppLifecycleState.resumed:
        debugPrint('App resumed');
        break;
      case AppLifecycleState.inactive:
        debugPrint('App inactive');
        break;
      case AppLifecycleState.paused:
        debugPrint('App paused');
        break;
      case AppLifecycleState.detached:
        debugPrint('App detached');
        break;
      case AppLifecycleState.hidden:
        debugPrint('App hidden');
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    const String viewType = 'basic_beauty_cam';
    final Map<String, dynamic> creationParams = <String, dynamic>{};

    return FutureBuilder(
      future: _initializationFuture,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        }
        if (snapshot.hasError || !(snapshot.data ?? false)) {
          return const Center(child: Text('Camera permission denied'));
        }

        return AnimatedOpacity(
          opacity: 1.0,
          duration: const Duration(milliseconds: 2000),
          child: Container(
            color: Colors.black,
            child: Platform.isAndroid
                ? buildAndroidView(viewType, creationParams)
                : buildIOSView(viewType, creationParams),
          ),
        );
      },
    );
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
