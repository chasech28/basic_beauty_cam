import 'package:basic_beauty_cam/beauty_camera_view.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:basic_beauty_cam/basic_beauty_cam.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(MaterialApp(home: MyApp()));
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int _frameCount = 0;

  @override
  void initState() {
    super.initState();

    // Set up image frame callback to receive frames from native code
    BasicBeautyCam.setImageFrameCallback((frame) {
      _frameCount++;
      debugPrint(
        'Received frame #$_frameCount: ${frame.width}x${frame.height}, '
        'bytes: ${frame.bytes.length}',
      );
    });
  }

  @override
  void dispose() {
    // Clean up callback when widget is disposed
    BasicBeautyCam.setImageFrameCallback(null);
    super.dispose();
  }

  final buttonStyle = TextButton.styleFrom(
    backgroundColor: Colors.white,
    shadowColor: Colors.black,
    elevation: 4,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Stack(
          alignment: Alignment.bottomCenter,
          children: [
            BeautyCameraPreview(),
            SizedBox(
              height: 50,
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: [
                  // TextButton(
                  //   style: buttonStyle,
                  //   onPressed: () {
                  //     Navigator.pushReplacement(
                  //       context,
                  //       MaterialPageRoute(
                  //         builder: (context) => const SecondPage(),
                  //       ),
                  //     );
                  //   },
                  //   child: const Text('跳转到第二页'),
                  // ),
                  TextButton(
                    style: buttonStyle,
                    onPressed: () {
                      BasicBeautyCam.switchCamera();
                    },
                    child: const Text('切换相机'),
                  ),
                  TextButton(
                    style: buttonStyle,
                    onPressed: () async {
                      var path = await BasicBeautyCam.takePicture();
                      debugPrint('path: $path');
                    },
                    child: const Text('拍照'),
                  ),
                  TextButton(
                    style: buttonStyle,
                    onPressed: () {
                      BasicBeautyCam.enableBeauty();
                    },
                    child: const Text('开启美颜'),
                  ),
                  TextButton(
                    style: buttonStyle,
                    onPressed: () {
                      BasicBeautyCam.disableBeauty();
                    },
                    child: const Text('关闭美颜'),
                  ),
                  TextButton(
                    style: buttonStyle,
                    onPressed: () async {
                      BasicBeautyCam.startImageStream();
                    },
                    child: const Text('开启图像流'),
                  ),
                  TextButton(
                    style: buttonStyle,
                    onPressed: () async {
                      BasicBeautyCam.stopImageStream();
                    },
                    child: const Text('关闭图像流'),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class SecondPage extends StatefulWidget {
  const SecondPage({super.key});

  @override
  State<SecondPage> createState() => _SecondPageState();
}

class _SecondPageState extends State<SecondPage> {
  bool _showCamera = false;

  @override
  void initState() {
    super.initState();
    // 延迟 500ms 确保上一个页面的相机资源完全释放
    Future.delayed(const Duration(milliseconds: 500), () {
      if (mounted) {
        setState(() {
          _showCamera = true;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Second Page')),
      body: SafeArea(
        child: _showCamera
            ? const SizedBox(
                width: 500,
                height: 500,
                child: BeautyCameraPreview(),
              )
            : const Center(child: CircularProgressIndicator()),
      ),
    );
  }
}
