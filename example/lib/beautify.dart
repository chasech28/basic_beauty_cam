import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

/// 美颜相机控制器
/// 用于在 Flutter 端调用原生相机视图的功能
class BeautyCamController {
  static const _channel = MethodChannel('beauty_cam_native_view');

  /// 开启或关闭美颜
  ///
  /// [enable] true 开启美颜，false 关闭美颜
  static Future<void> enableBeauty(bool enable) async {
    try {
      await _channel.invokeMethod('enableBeauty', {'enable': enable});
    } on PlatformException catch (e) {
      debugPrint('Failed to enable beauty: ${e.message}');
    }
  }

  /// 拍照
  static Future<void> takePicture() async {
    try {
      await _channel.invokeMethod('takePicture');
    } on PlatformException catch (e) {
      debugPrint('Failed to take picture: ${e.message}');
    }
  }
}

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
      ),
    );
  }
}
