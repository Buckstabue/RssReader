package ru.rambler.kiyakovyacheslav.di.component;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.di.PerFragment;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedFragment;
import ru.rambler.kiyakovyacheslav.di.module.RssViewModule;

@PerFragment
@Component(modules = {RssViewModule.class}, dependencies = {AppComponent.class})
public interface RssViewComponent {
    void inject(RssFeedFragment rssFeedFragment);
}
