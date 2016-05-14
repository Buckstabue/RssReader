package ru.rambler.kiyakovyacheslav.model;

import com.einmalfel.earl.Item;

import java.util.List;

import rx.Observable;

public interface IRssFeedManager {
    Observable<List<? extends Item>> downloadRssFeed(final String url);
}
