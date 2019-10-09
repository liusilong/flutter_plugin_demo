package com.lsl.flutter_plugin_demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class PluginDelegate implements
        Application.ActivityLifecycleCallbacks,
        PluginRegistry.RequestPermissionsResultListener,
        PluginRegistry.ActivityResultListener {

    private final Context context;
    private final Application application;

    public PluginDelegate(PluginRegistry.Registrar registrar) {
        this.context = registrar.context();
        this.application = (Application) context;
    }

    public void methodA(MethodCall call, MethodChannel.Result result){
        // do something...
    }

    public void methodB(MethodCall call, MethodChannel.Result result){
        // do something...
    }

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
        application.unregisterActivityLifecycleCallbacks(this);
    }

    @Override
    public boolean onRequestPermissionsResult(int i, String[] strings, int[] ints) {
        return false;
    }

    @Override
    public boolean onActivityResult(int i, int i1, Intent intent) {
        return false;
    }
}
