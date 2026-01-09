import 'package:basic_beauty_cam/beauty_camera_view.dart';
import 'package:basic_beauty_cam_example/permission.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:basic_beauty_cam/basic_beauty_cam.dart';
import 'package:permission_handler/permission_handler.dart';

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
  // final _basicBeautyCamPlugin = BasicBeautyCam();

  @override
  void initState() {
    super.initState();
  }

  final buttonStyle = TextButton.styleFrom(
    backgroundColor: Colors.white,
    shadowColor: Colors.black,
    elevation: 4,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Plugin example app')),
      body: SafeArea(
        child: Column(
          children: [
            SizedBox(width: 500, height: 500, child: BeautyCameraPreview()),
            SizedBox(
              height: 50,
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: [
                  TextButton(
                    style: buttonStyle,
                    onPressed: () {
                      //   Navigator.push(
                      //     context,
                      //     MaterialPageRoute(
                      //       builder: (context) => const SecondPage(),
                      //     ),
                      //   );

                      Navigator.pushReplacement(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const SecondPage(),
                        ),
                      );
                    },
                    child: const Text('跳转到第二页'),
                  ),
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
