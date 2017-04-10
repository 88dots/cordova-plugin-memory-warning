package org.apache.cordova.memory;

import android.content.ComponentCallbacks2;

import org.apache.cordova.LOG;

public class CordovaPluginMemoryWarningActivity implements ComponentCallbacks2 {
    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     */
    @Override
    public void onLowMemory() {
        LOG.d("MemoryActivity", "onLowMemory");
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     * @param level the memory-related event that was raised.
     */
    @Override
    public void onTrimMemory(int level) {
        LOG.d("MemoryActivity", "onTrimMemory", level);
    }
}