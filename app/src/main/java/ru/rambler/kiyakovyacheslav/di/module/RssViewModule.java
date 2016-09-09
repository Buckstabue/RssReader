package ru.rambler.kiyakovyacheslav.di.module;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import ru.rambler.kiyakovyacheslav.di.PerFragment;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssUrlProvider;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter;
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
    @PerFragment
    public IRssUrlProvider provideRssUrlProvider() {
        return new RssUrlProvider();
    }

    @Provides
    @PerFragment
    public IRssFeedPresenter provideRssFeedPresenter(IRssUrlProvider rssUrlProvider,
                                                     IRssFeedManager rssFeedManager,
                                                     RxUtil rxUtil,
                                                     RssFeedCache rssFeedCache) {
        return new RssFeedPresenter(rssIRssFeedView, rssUrlProvider, rssFeedManager, rxUtil, rssFeedCache);
    }

    @Provides
    @PerFragment
    public RssFeedCache provideRssFeedCache() {
        return new RssFeedCache(RssFeedPresenter.EXPECTED_RSS_ITEMS_NUMBER);
    }

    @Provides
    @PerFragment
    public RssItemAdapter providRssItemAdapter(Picasso picasso) {
        return new RssItemAdapter(picasso);
    }
}
