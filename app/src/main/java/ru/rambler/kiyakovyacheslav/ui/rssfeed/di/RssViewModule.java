package ru.rambler.kiyakovyacheslav.ui.rssfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.rambler.kiyakovyacheslav.di.ActivityScope;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssUrlProvider;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedPresenter;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssUrlProvider;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedPresenter;
import ru.rambler.kiyakovyacheslav.util.RssFeedCache;
import ru.rambler.kiyakovyacheslav.util.RxUtil;

@Module
public class RssViewModule {
    private IRssFeedView rssIRssFeedView;

    public RssViewModule(IRssFeedView rssIRssFeedView) {
        this.rssIRssFeedView = rssIRssFeedView;
    }


    @Provides
    @ActivityScope
    public IRssUrlProvider provideRssUrlProvider() {
        return new RssUrlProvider();
    }

    @Provides
    @ActivityScope
    public IRssFeedPresenter provideRssFeedPresenter(IRssUrlProvider rssUrlProvider,
                                                     IRssFeedManager rssFeedManager,
                                                     RxUtil rxUtil,
                                                     RssFeedCache rssFeedCache) {
        return new RssFeedPresenter(rssIRssFeedView, rssUrlProvider, rssFeedManager, rxUtil, rssFeedCache);
    }

    @Provides
    @ActivityScope
    public RssFeedCache provideRssFeedCache() {
        return new RssFeedCache(RssFeedPresenter.EXPECTED_RSS_ITEMS_NUMBER);
    }
}
