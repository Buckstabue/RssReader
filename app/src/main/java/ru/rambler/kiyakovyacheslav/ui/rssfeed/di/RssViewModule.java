package ru.rambler.kiyakovyacheslav.ui.rssfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.rambler.kiyakovyacheslav.di.ActivityScope;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedPresenter;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedPresenter;

@Module
public class RssViewModule {
    private IRssFeedView rssIRssFeedView;

    public RssViewModule(IRssFeedView rssIRssFeedView) {
        this.rssIRssFeedView = rssIRssFeedView;
    }

    @Provides
    @ActivityScope
    public IRssFeedPresenter provideRssFeedPresenter() {
        return new RssFeedPresenter(rssIRssFeedView);
    }
}
