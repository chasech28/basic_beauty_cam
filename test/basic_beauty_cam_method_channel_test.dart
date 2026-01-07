import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:basic_beauty_cam/basic_beauty_cam_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelBasicBeautyCam platform = MethodChannelBasicBeautyCam();
  const MethodChannel channel = MethodChannel('basic_beauty_cam');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
