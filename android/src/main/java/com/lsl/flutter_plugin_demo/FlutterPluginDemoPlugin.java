package com.lsl.flutter_plugin_demo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterPluginDemoPlugin
 */
public class FlutterPluginDemoPlugin implements MethodCallHandler {
    private final Context context;
    private static EventChannel.EventSink eventSink;

    public FlutterPluginDemoPlugin(Registrar registrar) {
        this.context = registrar.context();
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_plugin_demo");
        final EventChannel eventChannel = new EventChannel(registrar.messenger(), "flutter_plugin_event");

        channel.setMethodCallHandler(new FlutterPluginDemoPlugin(registrar));
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {
                Log.e("lsl", "onListen: " + (eventSink == null));
                FlutterPluginDemoPlugin.eventSink = eventSink;
            }

            @Override
            public void onCancel(Object o) {
                FlutterPluginDemoPlugin.eventSink = null;
            }
        });
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("showToast")) {
            Map<String, String> params = call.arguments();
            Toast.makeText(context, params.get("name"), Toast.LENGTH_SHORT).show();
            // 通知 Dart 层
            if (null != eventSink)
                eventSink.success("Dart 调用 原始方法成功");
        } else {
            result.notImplemented();
        }
    }
}
