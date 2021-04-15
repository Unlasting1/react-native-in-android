package com.example.myapplication;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.Timer;
import java.util.TimerTask;

public class BridgeModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public BridgeModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    @Override
    public String getName() {
        return "ZH-Bridge";
    }
    @ReactMethod
    public void measureLayout(
            final Promise promise
    ) {
        try {
            final WritableMap map = Arguments.createMap();

            map.putDouble("relativeX", '1');
            map.putDouble("relativeY", '2');
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
//                    WritableMap params = Arguments.createMap();
//                    params.putString("eventProperty", "someValue");
//                    sendEvent(reactContext, "EventReminder", params);
                    promise.resolve(map);
                }
            };
            timer.schedule(task,0,10000);

        } catch (IllegalViewOperationException e) {
            promise.reject(E_LAYOUT_ERROR, e);
        }
    }

    public void sendEvent(ReactContext reactContext,
                          String eventName,
                          WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}