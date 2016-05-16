package ru.rambler.kiyakovyacheslav;

import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.rambler.kiyakovyacheslav.di.AppScope;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssFeedManager;
import ru.rambler.kiyakovyacheslav.util.RxUtil;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

@Module
public class AppTestModule {

    @AppScope
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return mock(OkHttpClient.class);
    }

    @AppScope
    @Provides
    public IRssFeedManager provideRssFeedManager() {
        return mock(RssFeedManager.class);
    }

    @AppScope
    @Provides
    public Context provideApplicationContext() {
        return mock(Context.class);
    }

    @AppScope
    @Provides
    public Picasso providePicasso() {
        return mock(Picasso.class);
    }

    @AppScope
    @Provides
    public RxUtil provideRxUtil() {
        return new RxUtil(Schedulers.immediate(), Schedulers.immediate());
    }
}
