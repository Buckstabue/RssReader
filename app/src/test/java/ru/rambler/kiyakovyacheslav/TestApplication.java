package ru.rambler.kiyakovyacheslav;

import ru.rambler.kiyakovyacheslav.RssFeed.di.DaggerRssViewTestComponent;
import ru.rambler.kiyakovyacheslav.RssFeed.di.RssViewTestModule;
import ru.rambler.kiyakovyacheslav.di.AppComponent;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.di.RssViewComponent;

public class TestApplication extends App {
    @Override
    protected AppComponent buildAppComponent() {
        return DaggerAppTestComponent.create();
    }

    @Override
    protected RssViewComponent buildRssViewComponent(IRssFeedView rssFeedView) {
        return DaggerRssViewTestComponent.builder()
                .appTestComponent((AppTestComponent) getAppComponent())
                .rssViewTestModule(new RssViewTestModule(rssFeedView))
                .build();
    }
}
