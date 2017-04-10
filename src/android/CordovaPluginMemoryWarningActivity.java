package org.apache.cordova.memory;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

import org.apache.cordova.LOG;

public class CordovaPluginMemoryWarningActivity implements ComponentCallbacks2 {

    private static final String TAG = "CordovaPluginMemoryWarning";

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {}

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     */
    @Override
    public void onLowMemory() {
        LOG.d(TAG, "onLowMemory");
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     * @param level the memory-related event that was raised.
     */
    @Override
    public void onTrimMemory(int level) {
        LOG.d(TAG, "onTrimMemory", level);
    }
}
