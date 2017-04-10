
package duelyst.counterplay.app;

import android.os.Bundle;
import org.apache.cordova.*;

public class MainActivity extends CordovaActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     */
    @Override
    public void onLowMemory() {
        LOG.d("MemoryWarning", "onLowMemory");
        this.appView.loadUrl("javascript:cordova.fireDocumentEvent('memorywarning');");
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     * @param level the memory-related event that was raised.
     */
    @Override
    public void onTrimMemory(int level) {
        LOG.d("MemoryWarning", "onTrimMemory", level);
        this.appView.loadUrl("javascript:cordova.fireDocumentEvent('memorywarning');");
    }
}
