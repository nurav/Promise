package com.gpv.promise;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by varun on 12/12/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
