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
              TextButton(
                onPressed: () {
                  PermissionUtils.checkAndRequestPermission(Permission.camera);
                },
                child: Text('请求相机权限'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
