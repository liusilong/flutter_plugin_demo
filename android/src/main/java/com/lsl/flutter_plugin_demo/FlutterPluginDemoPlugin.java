package com.lsl.flutter_plugin_demo;

import android.content.Context;
import android.widget.Toast;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterPluginDemoPlugin
 */
public class FlutterPluginDemoPlugin implements MethodCallHandler {
    private final Registrar registrar;
    private final Context context;

    public FlutterPluginDemoPlugin(Registrar registrar) {
        this.registrar = registrar;
        this.context = registrar.context();
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_plugin_demo");
        channel.setMethodCallHandler(new FlutterPluginDemoPlugin(registrar));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("showToast")) {
            Toast.makeText(context, "来自 Android 端的 Toast", Toast.LENGTH_SHORT).show();
        } else {
            result.notImplemented();
        }
    }
}
