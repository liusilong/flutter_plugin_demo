import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginDemo {
  static const MethodChannel _channel =
      const MethodChannel('flutter_plugin_demo');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  
  static Future<void> showToast() async {
    await _channel.invokeMethod("showToast");
  }

}
