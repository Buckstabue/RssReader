package ru.rambler.kiyakovyacheslav;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.di.AppComponent;
import ru.rambler.kiyakovyacheslav.di.AppScope;

@AppScope
@Component(modules = {AppTestModule.class})
public interface AppTestComponent extends AppComponent {
}
