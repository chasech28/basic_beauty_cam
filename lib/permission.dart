import 'package:flutter/foundation.dart';
import 'package:permission_handler/permission_handler.dart';

class PermissionUtils {
  PermissionUtils._internal();

  /// 请求单个权限
  static Future<PermissionStatus> checkAndRequestPermission(
    Permission permission,
  ) async {
    if (await permission.isGranted) {
      return PermissionStatus.granted;
    }
    if (await permission.isPermanentlyDenied) {
      return PermissionStatus.permanentlyDenied;
    }

    return await permission
        .onGrantedCallback(() {
          debugPrint('Permission granted: ${permission.toString()}');
        })
        .onDeniedCallback(() {
          debugPrint('Permission denied: ${permission.toString()}');
        })
        .onPermanentlyDeniedCallback(() {
          debugPrint('Permission permanently denied: ${permission.toString()}');
        })
        .onLimitedCallback(() {
          debugPrint('Permission limited: ${permission.toString()}');
        })
        .onRestrictedCallback(() {
          debugPrint('Permission restricted: ${permission.toString()}');
        })
        .request();
  }
}
