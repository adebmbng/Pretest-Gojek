package com.debam.pretest_gojek;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;
import com.debam.pretest_gojek.utils.Constant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Debam on 2/5/17.
 */
@Module
public class AppModule {

    private Application app;
    private ContactRepository db;
    private APIServices api;

    public AppModule(Application app) {
        this.app = app;
        db = new ContactRepository(app);
        Retrofit base = new Retrofit.Builder()
                .baseUrl(Constant.URL_CONSTANT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = base.create(APIServices.class);
    }

    @Provides
    @Singleton
    Context provideContext(){
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences provideSP(){
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    ContactRepository provideDB(){
        return db;
    }

    @Provides
    @Singleton
    APIServices provideAPI(){
        return api;
    }


}
