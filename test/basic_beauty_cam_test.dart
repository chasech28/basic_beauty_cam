// import 'package:flutter_test/flutter_test.dart';
// import 'package:basic_beauty_cam/basic_beauty_cam.dart';
// import 'package:basic_beauty_cam/basic_beauty_cam_platform_interface.dart';
// import 'package:basic_beauty_cam/basic_beauty_cam_method_channel.dart';
// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// class MockBasicBeautyCamPlatform
//     with MockPlatformInterfaceMixin
//     implements BasicBeautyCamPlatform {
//   @override
//   Future<String?> getPlatformVersion() => Future.value('42');
// }

// void main() {
//   final BasicBeautyCamPlatform initialPlatform =
//       BasicBeautyCamPlatform.instance;

//   test('$MethodChannelBasicBeautyCam is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelBasicBeautyCam>());
//   });

//   test('getPlatformVersion', () async {
//     BeautyCameraController basicBeautyCamPlugin = BeautyCameraController();
//     MockBasicBeautyCamPlatform fakePlatform = MockBasicBeautyCamPlatform();
//     BasicBeautyCamPlatform.instance = fakePlatform;

//     expect(await basicBeautyCamPlugin.getPlatformVersion(), '42');
//   });
// }
