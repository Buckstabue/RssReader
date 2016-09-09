package ru.rambler.kiyakovyacheslav;

import dagger.Component;
import ru.rambler.kiyakovyacheslav.di.component.AppComponent;
import ru.rambler.kiyakovyacheslav.di.PerApp;

@PerApp
@Component(modules = {AppTestModule.class})
public interface AppTestComponent extends AppComponent {
}
