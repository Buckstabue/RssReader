package ru.rambler.kiyakovyacheslav.di;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedPresenter;
import ru.rambler.kiyakovyacheslav.util.RxUtil;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(RssFeedPresenter rssFeedPresenter);
    void inject(RssItemAdapter rssItemAdapter);

    RxUtil rxUtil();
    IRssFeedManager rssFeedManager();
}
