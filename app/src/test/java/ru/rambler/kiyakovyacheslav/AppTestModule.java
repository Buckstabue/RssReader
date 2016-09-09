package ru.rambler.kiyakovyacheslav;

import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.rambler.kiyakovyacheslav.di.PerApp;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssFeedManager;
import ru.rambler.kiyakovyacheslav.util.RxUtil;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

@Module
public class AppTestModule {

    @PerApp
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return mock(OkHttpClient.class);
    }

    @PerApp
    @Provides
    public IRssFeedManager provideRssFeedManager() {
        return mock(RssFeedManager.class);
    }

    @PerApp
    @Provides
    public Context provideApplicationContext() {
        return mock(Context.class);
    }

    @PerApp
    @Provides
    public Picasso providePicasso() {
        return mock(Picasso.class);
    }

    @PerApp
    @Provides
    public RxUtil provideRxUtil() {
        return new RxUtil(Schedulers.immediate(), Schedulers.immediate());
    }
}
