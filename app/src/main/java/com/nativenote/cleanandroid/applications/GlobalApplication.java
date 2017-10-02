package com.nativenote.cleanandroid.applications;

import android.app.Application;

import com.nativenote.cleanandroid.component.ApplicationComponent;
import com.nativenote.cleanandroid.component.DaggerApplicationComponent;
import com.nativenote.cleanandroid.module.ContextModule;
import com.nativenote.cleanandroid.network.NetworkService;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class GlobalApplication extends Application {
    ApplicationComponent component;

    NetworkService service;
    Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        service = component.getNetworkService();
        picasso = component.getPicasso();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
