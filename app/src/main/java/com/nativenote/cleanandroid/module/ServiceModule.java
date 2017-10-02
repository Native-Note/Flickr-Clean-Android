package com.nativenote.cleanandroid.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nativenote.cleanandroid.BuildConfig;
import com.nativenote.cleanandroid.applications.ApplicationScope;
import com.nativenote.cleanandroid.network.DateTimeConverter;
import com.nativenote.cleanandroid.network.NetworkService;
import com.nativenote.cleanandroid.network.Service;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module(includes = NetworkModule.class)
public class ServiceModule {

    @Provides
    @ApplicationScope
    public NetworkService networkService(Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @ApplicationScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    @ApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BuildConfig.APIBASEURL)
                .build();
    }
}
