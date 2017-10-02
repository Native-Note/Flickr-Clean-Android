package com.nativenote.cleanandroid.mvp.home;

import com.nativenote.cleanandroid.network.NetworkService;
import com.nativenote.cleanandroid.network.Service;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {
    private HomeActivity activity;

    public HomeModule(HomeActivity activity) {
        this.activity = activity;
    }


    public HomeActivity getActivity() {
        return activity;
    }

    @Provides
    @SuppressWarnings("unused")
    public Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }
}
