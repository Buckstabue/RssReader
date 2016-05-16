package ru.rambler.kiyakovyacheslav.RssFeed.di;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.AppTestComponent;
import ru.rambler.kiyakovyacheslav.RssFeed.RssFeedCacheTest;
import ru.rambler.kiyakovyacheslav.RssFeed.RssFeedPresenterTest;
import ru.rambler.kiyakovyacheslav.di.ActivityScope;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.di.RssViewComponent;

@ActivityScope
@Component(modules = {RssViewTestModule.class}, dependencies = {AppTestComponent.class})
public interface RssViewTestComponent extends RssViewComponent {
    void inject(RssFeedPresenterTest rssFeedPresenterTest);
    void inject(RssFeedCacheTest rssFeedCacheTest);
}
