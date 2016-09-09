package ru.rambler.kiyakovyacheslav.di.component;

import com.squareup.picasso.Picasso;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.di.PerApp;
import ru.rambler.kiyakovyacheslav.di.module.AppModule;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.util.RxUtil;

@PerApp
@Component(modules = {AppModule.class})
public interface AppComponent {

    RxUtil rxUtil();

    IRssFeedManager rssFeedManager();

    Picasso picasso();
}
