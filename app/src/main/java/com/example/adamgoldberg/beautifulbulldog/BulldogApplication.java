package com.example.adamgoldberg.beautifulbulldog;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Adam Goldberg on 9/15/2017.
 */

public class BulldogApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
