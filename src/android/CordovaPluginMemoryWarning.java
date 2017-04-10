package org.apache.cordova.memory;

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

import MemoryActivity;

public class CordovaPluginMemoryWarning extends CordovaPlugin {

    private Intent memoryIntent;

    /**
     * Constructor.
     */
    public CordovaPluginMemoryWarning() {
        Context context = cordova.getActivity().getApplicationContext();
        this.memoryIntent = new Intent(context, CordovaPluginMemoryWarningActivity.class);
        cordova.getActivity().startActivity(this.memoryIntent);
    }

    /**
     * Destroyed.
     */
    public void onDestroy() {
        // TODO: is there any need to explicitly stop memory activity?
    }

    /**
     * Destroy on navigate.
     */
    @Override
    public void onReset() {
        onDestroy();
    }

    /**
     * Executes the request and returns PluginResult.
     * @param action 		The action to execute.
     * @param args 			JSONArry of arguments for the plugin.
     * @param callbackContext		The callback context used when calling back into JavaScript.
     * @return 				A PluginResult object with a status and message.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("isMemoryUsageUnsafe")) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Activity activity = cordova.getActivity();
                        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
                        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                        activityManager.getMemoryInfo(memoryInfo);

                        if (memoryInfo.lowMemory) {
                            LOG.d("CordovaPluginMemoryWarning", "Low memory");
                        }

                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, memoryInfo.lowMemory));
                    } catch (Exception e) {
                        LOG.e("CordovaPluginMemoryWarning", "Error occured while checking memory usage", e);
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION, "Could not check memory usage"));
                    }
                }
            });
        }

        return true;
    }
}
