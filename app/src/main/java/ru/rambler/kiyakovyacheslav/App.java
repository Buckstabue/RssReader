package ru.rambler.kiyakovyacheslav;

import android.app.Application;

import ru.rambler.kiyakovyacheslav.di.component.AppComponent;
import ru.rambler.kiyakovyacheslav.di.component.DaggerAppComponent;
import ru.rambler.kiyakovyacheslav.di.module.AppModule;

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
