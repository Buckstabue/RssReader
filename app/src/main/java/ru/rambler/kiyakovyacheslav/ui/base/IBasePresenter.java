package ru.rambler.kiyakovyacheslav.ui.base;

import rx.Subscription;

public interface IBasePresenter {
    void addSubscription(Subscription subscription);
    void onStop();
}
