package com.manuelramonlopez.raygun;

import android.content.Context;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.mindscapehq.android.raygun4android.RaygunClient;
import main.java.com.mindscapehq.android.raygun4android.RaygunOnBeforeSend;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunMessage;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunUserInfo;

public class RaygunNativePlugin extends CordovaPlugin implements RaygunOnBeforeSend {
    private final String pluginName = "RaygunNativePlugin";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.d(pluginName, pluginName + " initialized");


    }

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {
        Log.d(pluginName, pluginName + " called with options: " + data);

        if (action.equals("startNativeRaygun")) {
            startNativeRaygun(data, callbackContext);
        } else if(action.equals("testCrash")) {
            testCrash();
        } else if(action.equals("setUser")) {
            setUser(data, callbackContext);
        }
        return true;
    }

    @Override
    public RaygunMessage onBeforeSend(RaygunMessage message) {
        Log.i("OnBeforeSend", "About to post to Raygun, returning the payload as is...");
        return message;
    }

    @Override
    public RaygunMessage OnBeforeSend(RaygunMessage message) {
        Log.i("OnBeforeSend", "About to post to Raygun, returning the payload as is...");
        return message;
    }

    private void startNativeRaygun(final JSONArray data, final CallbackContext callbackContext) {
        final Context context = this.cordova.getActivity().getApplicationContext();
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                RaygunClient.init(context);
                RaygunClient.attachExceptionHandler();
                RaygunClient.setOnBeforeSend(RaygunNativePlugin.this);
                try {
                    JSONObject obj = data.getJSONObject(0);
                    String message;
                    if (obj.has("user")) {
                        RaygunUserInfo user = new RaygunUserInfo();
                        user.setFullName(obj.getString("user"));
                        RaygunClient.setUser(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void testCrash() {
        Integer integers[] = new Integer[Integer.MAX_VALUE];
        integers[2147483647] = 2147483647+1;
        testCrash();
        throw new RuntimeException("This is a crash");
    }

    private void setUser(final JSONArray data, final CallbackContext callbackContext) {
       final Context context = this.cordova.getActivity().getApplicationContext();
       this.cordova.getThreadPool().execute(new Runnable() {
           @Override
           public void run() {
               RaygunClient.init(context);
               RaygunClient.attachExceptionHandler();
               try {
                   JSONObject obj = data.getJSONObject(0);
                   String message;

                   RaygunUserInfo user = new RaygunUserInfo();

                   user.setIdentifier(obj.getString("user"));
                   user.setAnonymous(obj.getBoolean("isAnonymous"));
                   user.setEmail(obj.getString("email"));
                   user.setFirstName(obj.getString("firstName"));
                   user.setFullName(obj.getString("fullName"));

                   RaygunClient.setUser(user);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
   }
}
