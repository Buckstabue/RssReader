package ru.rambler.kiyakovyacheslav.util;

import rx.schedulers.Schedulers;

public class TestRxUtil extends RxUtil {
    public TestRxUtil() {
        super(Schedulers.immediate(), Schedulers.immediate());
    }
}
