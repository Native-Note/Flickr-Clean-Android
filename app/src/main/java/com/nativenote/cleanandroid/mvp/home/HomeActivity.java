package com.nativenote.cleanandroid.mvp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.nativenote.cleanandroid.R;
import com.nativenote.cleanandroid.applications.GlobalApplication;
import com.nativenote.cleanandroid.models.FlickrFeeds;
import com.nativenote.cleanandroid.mvp.home.adapter.FeedAdapter;
import com.nativenote.cleanandroid.mvp.home.presenter.HomePresenter;
import com.nativenote.cleanandroid.mvp.home.view.HomeView;
import com.nativenote.cleanandroid.network.Service;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    Service service;

    @Inject
    Picasso picasso;

    FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        HomeComponent component = DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this))
                .applicationComponent(((GlobalApplication) getApplication()).getComponent())
                .build();

        component.inject(this);

        HomePresenter presenter = new HomePresenter(service, this);
        presenter.getFlickrFeeds();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        feedAdapter = new FeedAdapter(this, picasso);
        recyclerView.setAdapter(feedAdapter);

    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String errorMessage) {
        Timber.e("onFailure", errorMessage);
    }

    @Override
    public void onSuccess(FlickrFeeds flickrFeeds) {
        feedAdapter.swapData(flickrFeeds.getItems());
    }
}
