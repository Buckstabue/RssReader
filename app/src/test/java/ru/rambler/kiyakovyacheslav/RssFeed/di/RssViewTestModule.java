package ru.rambler.kiyakovyacheslav.RssFeed.di;

import dagger.Module;
import dagger.Provides;
import ru.rambler.kiyakovyacheslav.di.ActivityScope;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedPresenter;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssUrlProvider;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedPresenter;
import ru.rambler.kiyakovyacheslav.util.RssFeedCache;
import ru.rambler.kiyakovyacheslav.util.RxUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Module
public class RssViewTestModule {

    private IRssFeedView rssFeedView;

    public RssViewTestModule(IRssFeedView rssFeedView) {
        this.rssFeedView = rssFeedView;
    }

    @Provides
    @ActivityScope
    public IRssFeedPresenter provideRssFeedPresenter(IRssUrlProvider rssUrlProvider,
                                                     IRssFeedManager rssFeedManager,
                                                     RxUtil rxUtil,
                                                     RssFeedCache rssFeedCache) {
        return new RssFeedPresenter(rssFeedView, rssUrlProvider, rssFeedManager, rxUtil, rssFeedCache);
    }

    @Provides
    @ActivityScope
    public IRssUrlProvider provideRssUrlProvider() {
        return mock(IRssUrlProvider.class);
    }

    @Provides
    @ActivityScope
    public RssFeedCache provideRssFeedCache() {
        return spy(new RssFeedCache(250));
    }
}
