package ru.rambler.kiyakovyacheslav;

import android.app.Application;

import ru.rambler.kiyakovyacheslav.di.AppComponent;
import ru.rambler.kiyakovyacheslav.di.AppModule;
import ru.rambler.kiyakovyacheslav.di.DaggerAppComponent;

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
