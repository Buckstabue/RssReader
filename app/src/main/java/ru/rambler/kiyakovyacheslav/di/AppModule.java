package ru.rambler.kiyakovyacheslav.di;


import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.rambler.kiyakovyacheslav.App;
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

    @AppScope
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @AppScope
    @Provides
    public IRssFeedManager provideRssFeedManager(OkHttpClient httpClient) {
        return new RssFeedManager(httpClient);
    }

    @AppScope
    @Provides
    public Context provideApplicationContext() {
        return app;
    }

    @AppScope
    @Provides
    public Picasso providePicasso() {
        return Picasso.with(app);
    }

    @AppScope
    @Provides
    public RxUtil provideRxUtil() {
        return new RxUtil(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
