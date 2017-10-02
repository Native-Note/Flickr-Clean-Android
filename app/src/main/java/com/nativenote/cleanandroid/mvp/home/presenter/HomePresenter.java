package com.nativenote.cleanandroid.mvp.home.presenter;

import com.nativenote.cleanandroid.models.FlickrFeeds;
import com.nativenote.cleanandroid.mvp.home.view.HomeView;
import com.nativenote.cleanandroid.network.NetworkError;
import com.nativenote.cleanandroid.network.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {
    private Service service;
    private HomeView view;
    private CompositeSubscription subscription;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscription = new CompositeSubscription();
    }

    public void getFlickrFeeds() {
        view.showWait();

        Subscription subscription = service.getFlickrList(new Service.flickrListCallback() {
            @Override
            public void onSuccess(FlickrFeeds flickrFeeds) {
                view.removeWait();
                view.onSuccess(flickrFeeds);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });
    }

    public void onStop() {
        subscription.unsubscribe();
    }
}
