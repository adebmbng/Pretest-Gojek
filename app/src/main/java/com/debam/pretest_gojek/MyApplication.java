package com.debam.pretest_gojek;

import android.app.Application;

/**
 * Created by Debam on 2/5/17.
 */

public class MyApplication extends Application {
    DIComponent dc;

    @Override
    public void onCreate() {
        super.onCreate();
        dc = DaggerDIComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public DIComponent getDc() {
        return dc;
    }
}
