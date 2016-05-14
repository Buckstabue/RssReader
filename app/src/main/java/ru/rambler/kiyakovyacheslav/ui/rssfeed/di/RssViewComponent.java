package ru.rambler.kiyakovyacheslav.ui.rssfeed.di;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.di.ActivityScope;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedFragment;

@ActivityScope
@Component(modules = {RssViewModule.class})
public interface RssViewComponent {
    void inject(RssFeedFragment rssFeedFragment);
}
