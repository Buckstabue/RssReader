package ru.rambler.kiyakovyacheslav;

import android.app.Application;

import ru.rambler.kiyakovyacheslav.di.AppComponent;
import ru.rambler.kiyakovyacheslav.di.AppModule;
import ru.rambler.kiyakovyacheslav.di.DaggerAppComponent;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.di.DaggerRssViewComponent;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.di.RssViewComponent;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.di.RssViewModule;

public class App extends Application {
    private static AppComponent appComponent;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = buildAppComponent();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static RssViewComponent getRssViewComponent(IRssFeedView rssFeedView) {
        return app.buildRssViewComponent(rssFeedView);
    }

    protected RssViewComponent buildRssViewComponent(IRssFeedView rssFeedView) {
        return DaggerRssViewComponent.builder()
                .appComponent(appComponent)
                .rssViewModule(new RssViewModule(rssFeedView))
                .build();
    }
}
