package app.zogo.photogallery.application;

import android.content.Context;
import android.content.res.Resources;

import com.activeandroid.ActiveAndroid;

/**
 * Created by karuppiah on 9/28/2016.
 */
public class PhotoGalleryApplctn extends com.activeandroid.app.Application {

    private static Context context;

    public static final String TAG = PhotoGalleryApplctn.class
            .getSimpleName();

    private static PhotoGalleryApplctn mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = null;
        context = getApplicationContext();
        ActiveAndroid.initialize(this);
    }

    public static Context getGlobalContext() {
        return context;
    }

    public static Resources getAppResources() {
        return context.getResources();
    }

    public static String getAppString(int resourceId, Object... formatArgs) {
        return getAppResources().getString(resourceId, formatArgs);
    }

    public static String getAppString(int resourceId) {
        return getAppResources().getString(resourceId);
    }

    public static synchronized PhotoGalleryApplctn getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
