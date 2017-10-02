package com.nativenote.cleanandroid.mvp.home.view;

import com.nativenote.cleanandroid.models.FlickrFeeds;

public interface HomeView {

    void showWait();

    void removeWait();

    void onFailure(String errorMessage);

    void onSuccess(FlickrFeeds flickrFeeds);
}
