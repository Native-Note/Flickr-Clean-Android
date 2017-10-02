package com.nativenote.cleanandroid.network;

import com.nativenote.cleanandroid.models.FlickrFeeds;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getFlickrList(final flickrListCallback callback) {
        return networkService.getPublicFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends FlickrFeeds>>() {
                    @Override
                    public Observable<? extends FlickrFeeds> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<FlickrFeeds>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(FlickrFeeds flickrFeeds) {
                        callback.onSuccess(flickrFeeds);
                    }
                });
    }

    public interface flickrListCallback {
        void onSuccess(FlickrFeeds flickrFeeds);

        void onError(NetworkError networkError);
    }
}
