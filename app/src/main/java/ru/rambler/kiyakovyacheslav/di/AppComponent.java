package ru.rambler.kiyakovyacheslav.di;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.RssFeedPresenter;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(RssFeedPresenter rssFeedPresenter);
}
