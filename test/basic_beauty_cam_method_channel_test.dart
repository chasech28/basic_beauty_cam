import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:basic_beauty_cam/basic_beauty_cam_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelBasicBeautyCam platform = MethodChannelBasicBeautyCam();

  test('switchCamera', () async {
    // Mock the Pigeon BasicMessageChannel for switchCamera
    const channelName = 'dev.flutter.pigeon.com.example.basic_beauty_cam.CameraApi.switchCamera';
    final channel = BasicMessageChannel<Object?>(
      channelName,
      const StandardMessageCodec(),
    );
    
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockDecodedMessageHandler<Object?>(
      channel,
      (message) async {
        return []; // Return empty list for success
      },
    );

    expect(() async => await platform.switchCamera(), returnsNormally);

    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockDecodedMessageHandler(channel, null);
  });

  test('takePicture', () async {
    // Mock the Pigeon BasicMessageChannel for takePicture
    const channelName = 'dev.flutter.pigeon.com.example.basic_beauty_cam.CameraApi.takePicture';
    final channel = BasicMessageChannel<Object?>(
      channelName,
      const StandardMessageCodec(),
    );
    
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockDecodedMessageHandler<Object?>(
