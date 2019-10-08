import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginDemo {
  static const MethodChannel _channel =
      const MethodChannel('flutter_plugin_demo');
  static const EventChannel _eventChannel =
      EventChannel("flutter_plugin_event");

  static StreamController<String> streamController = StreamController<String>.broadcast();


  /// 问题是这里没有主动调用
  FlutterPluginDemo() {
    print('FLutter 构造方法');
    _eventChannel.receiveBroadcastStream().listen((data) {
      streamController.sink.add(data);
    });
  }

  static dispose(){
    streamController.close();
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// [message] Toast 的内容
  static Future<void> showToast({String message}) async {
    Map<String, String> params = Map<String, String>();
    params['name'] = 'Lili';
    params['age'] = '20';
    params['country'] = 'China';
    await _channel.invokeMethod("showToast", params);
  }
}
