package com.nativenote.cleanandroid.network;



import com.nativenote.cleanandroid.models.FlickrFeeds;

import retrofit2.http.GET;
import rx.Observable;

public interface NetworkService {

  @GET("photos_public.gne?tags=street&format=json&nojsoncallback=1")
  Observable<FlickrFeeds> getPublicFeeds();
}
