package com.lsl.flutter_plugin_demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterPluginDemoPlugin
 */
public class FlutterPluginDemoPlugin implements MethodCallHandler {
    private final Registrar registrar;
    private final Context context;
    private EventChannel.EventSink eventSink;
    private Application.ActivityLifecycleCallbacks lifecycleCallbacks;

    public FlutterPluginDemoPlugin(Registrar registrar) {
        this.registrar = registrar;
        this.context = registrar.context();
        final EventChannel eventChannel = new EventChannel(registrar.messenger(), "flutter_plugin_event");
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {
                FlutterPluginDemoPlugin.this.eventSink = eventSink;
            }

            @Override
            public void onCancel(Object o) {
                FlutterPluginDemoPlugin.this.eventSink = null;
            }
        });

        // 注册声明周期方法的监听
        ((Application) registrar.context())
                .registerActivityLifecycleCallbacks(new LifeCycleCallbacks());

        registrar.addRequestPermissionsResultListener(new PluginRegistry.RequestPermissionsResultListener() {
            @Override
            public boolean onRequestPermissionsResult(int i, String[] strings, int[] ints) {
                return false;
            }
        });

        registrar.addActivityResultListener(new PluginRegistry.ActivityResultListener() {
            @Override
            public boolean onActivityResult(int requestCode, int responseCode, Intent intent) {
                return false;
            }
        });



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
            Map<String, String> params = call.arguments();
            Toast.makeText(context, params.get("name"), Toast.LENGTH_SHORT).show();
            // 通知 Dart 层
            if (null != eventSink) {
                eventSink.success("Dart 调用 原始方法成功");
            }
        } else {
            result.notImplemented();
        }
    }

    class LifeCycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity == registrar.activity()) {
                ((Application) registrar.context()).unregisterActivityLifecycleCallbacks(this);
            }
        }
    }
}
