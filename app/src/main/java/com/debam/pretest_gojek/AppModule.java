package com.debam.pretest_gojek;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;
import com.debam.pretest_gojek.utils.Constant;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        Retrofit base = new Retrofit.Builder()
                .baseUrl(Constant.URL_CONSTANT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client.build())
                .build();
        api = base.create(APIServices.class);

//        Observable<List<Contact>> listContact = api.getContacts_rx();
//        listContact.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(contacts -> Log.d("subcribe", contacts+" "));
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
