package com.nativenote.cleanandroid.component;

import com.nativenote.cleanandroid.applications.ApplicationScope;
import com.nativenote.cleanandroid.module.ContextModule;
import com.nativenote.cleanandroid.module.PicassoModule;
import com.nativenote.cleanandroid.module.ServiceModule;
import com.nativenote.cleanandroid.network.NetworkService;
import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = {ServiceModule.class, PicassoModule.class, ContextModule.class})
public interface ApplicationComponent {

    Picasso getPicasso();

    NetworkService getNetworkService();
}
