import 'package:basic_beauty_cam_example/beautify.dart';
import 'package:basic_beauty_cam_example/permission.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:basic_beauty_cam/basic_beauty_cam.dart';
import 'package:permission_handler/permission_handler.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(MyApp());
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

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Plugin example app')),
        body: SafeArea(
          child: Column(
            children: [
              BasicBeautyCamView(),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  TextButton(
                    style: TextButton.styleFrom(
                      backgroundColor: Colors.white,
                      shadowColor: Colors.black,
                      elevation: 4,
                    ),
                    onPressed: () {
                      BasicBeautyCam.switchCamera();
                    },
                    child: Text('切换相机'),
                  ),
                  TextButton(
                    style: TextButton.styleFrom(
                      backgroundColor: Colors.white,
                      shadowColor: Colors.black,
                      elevation: 4,
                    ),
                    onPressed: () {
                      BasicBeautyCam.takePicture();
                    },
                    child: Text('拍照'),
                  ),
                  TextButton(
                    style: TextButton.styleFrom(
                      backgroundColor: Colors.white,
                      shadowColor: Colors.black,
                      elevation: 4,
                    ),
                    onPressed: () {
                      BasicBeautyCam.enableBeauty();
                    },
                    child: Text('开启美颜'),
                  ),
                  TextButton(
                    style: TextButton.styleFrom(
                      backgroundColor: Colors.white,
                      shadowColor: Colors.black,
                      elevation: 4,
                    ),
                    onPressed: () {
                      BasicBeautyCam.disableBeauty();
                    },
                    child: Text('关闭美颜'),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class CustomButton extends StatelessWidget {
  const CustomButton({super.key});

  @override
  Widget build(BuildContext context) {
    return Container();
  }
}
