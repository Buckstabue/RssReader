package ru.rambler.kiyakovyacheslav.di.module;


import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.rambler.kiyakovyacheslav.App;
import ru.rambler.kiyakovyacheslav.di.PerApp;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssFeedManager;
import ru.rambler.kiyakovyacheslav.util.RxUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @PerApp
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @PerApp
    @Provides
    public IRssFeedManager provideRssFeedManager(OkHttpClient httpClient) {
        return new RssFeedManager(httpClient);
    }

    @PerApp
    @Provides
    public Context provideApplicationContext() {
        return app;
    }

    @PerApp
    @Provides
    public Picasso providePicasso() {
        return Picasso.with(app);
    }

    @PerApp
    @Provides
    public RxUtil provideRxUtil() {
        return new RxUtil(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
