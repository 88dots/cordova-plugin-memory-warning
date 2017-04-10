package com.counterplay.memory;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.os.Build;
import android.content.Context;
import android.content.Intent;

public class CordovaPluginMemoryWarning extends CordovaPlugin {

    private static final String TAG = "CordovaPluginMemoryWarning";
    private ActivityManager activityManager;

    @Override
    protected void pluginInitialize() {
        // create activity manager to request memory state from system
        Activity activity = cordova.getActivity();
        activityManager = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
    }

    /**
     * Executes the request and returns PluginResult.
     * @param action 		The action to execute.
     * @param args 			JSONArry of arguments for the plugin.
     * @param callbackContext		The callback context used when calling back into JavaScript.
     * @return 				A PluginResult object with a status and message.
     */
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("isMemoryUsageUnsafe")) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        MemoryInfo memoryInfo = new MemoryInfo();
                        activityManager.getMemoryInfo(memoryInfo);

                        if (memoryInfo.lowMemory) {
                            LOG.d(TAG, "Low memory");
                        }

                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, memoryInfo.lowMemory));
                    } catch (Exception e) {
                        LOG.e(TAG, "Error occured while checking memory usage", e);
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION, "Could not check memory usage"));
                    }
                }
            });
        }

        return true;
    }
}
