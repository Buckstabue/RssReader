package ru.rambler.kiyakovyacheslav.util;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtil {
    private Scheduler ioScheduler;
    private Scheduler mainThreadScheduler;

    public RxUtil(Scheduler ioScheduler, Scheduler mainThreadScheduler) {
        this.ioScheduler = ioScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    public <T> Observable<T> prepareIOObservable(Observable<T> observable) {
        mainThreadScheduler = AndroidSchedulers.mainThread();
        ioScheduler = Schedulers.io();
        return observable
                .observeOn(mainThreadScheduler)
                .subscribeOn(ioScheduler);
    }


}
