import 'package:basic_beauty_cam/basic_beauty_cam.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BasicBeautyCamView extends StatelessWidget {
  const BasicBeautyCamView({super.key});

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    const String viewType = 'basic_beauty_cam';
    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{};

    // return AndroidView(
    //   viewType: viewType,
    //   layoutDirection: TextDirection.ltr,
    //   creationParams: creationParams,
    //   creationParamsCodec: const StandardMessageCodec(),
    // );

    return Expanded(
      child: AndroidView(
        viewType: viewType,
        layoutDirection: TextDirection.ltr,
        creationParams: creationParams,
        creationParamsCodec: const StandardMessageCodec(),
        onPlatformViewCreated: (_) {
          BasicBeautyCam.enableBeauty();
        },
      ),
    );
  }
}
