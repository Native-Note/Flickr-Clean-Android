package com.nativenote.cleanandroid.mvp.home;

import com.nativenote.cleanandroid.component.ApplicationComponent;

import dagger.Component;

@HomeScope
@Component(modules = HomeModule.class, dependencies = ApplicationComponent.class)
public interface HomeComponent {

    void inject(HomeActivity homeActivity);
}
