package ru.rambler.kiyakovyacheslav.di;


import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import ru.rambler.kiyakovyacheslav.App;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssFeedManager;

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
}
