package ru.rambler.kiyakovyacheslav.ui.rssfeed.di;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.di.ActivityScope;
import ru.rambler.kiyakovyacheslav.di.AppComponent;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedFragment;

@ActivityScope
@Component(modules = {RssViewModule.class}, dependencies = {AppComponent.class})
public interface RssViewComponent {
    void inject(RssFeedFragment rssFeedFragment);
}
