package io.happie.raygun;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import main.java.com.mindscapehq.android.raygun4android.RaygunClient;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunUserInfo;
import android.content.Context;
import android.util.Log;

public class RaygunNativePlugin extends CordovaPlugin {
    private final String pluginName = "RaygunNativePlugin";

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {
        Log.d(pluginName, pluginName + " called with options: " + data);
        if (action.equals("startNativeRaygun")) startNativeRaygun(data, callbackContext);
        else if(action.equals("testCrash")) testCrash();
        return true;
    }

    private void startNativeRaygun(final JSONArray data, final CallbackContext callbackContext) {
        final Context context = this.cordova.getActivity().getApplicationContext();
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                RaygunClient.init(context);
                RaygunClient.attachExceptionHandler();
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
}