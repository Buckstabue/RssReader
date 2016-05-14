package ru.rambler.kiyakovyacheslav.model;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Item;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class RssFeedManager implements IRssFeedManager {
    private OkHttpClient httpClient;

    @Inject
    public RssFeedManager(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Observable<List<? extends Item>> downloadRssFeed(final String url) {
        return Observable.fromCallable(() -> {
            byte[] bytes = downloadContent(url);
            return EarlParser.parseOrThrow(new ByteArrayInputStream(bytes), 0).getItems();
        });
    }

    private byte[] downloadContent(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response.body().bytes();
    }
}
