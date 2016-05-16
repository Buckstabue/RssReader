package ru.rambler.kiyakovyacheslav.model;

import java.util.Arrays;
import java.util.List;

import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssUrlProvider;

public class RssUrlProvider implements IRssUrlProvider {
    private static final List<String> RSS_SOURCES = Arrays.asList(
            "http://lenta.ru/rss",
            "http://gazeta.ru/export/rss/lenta.xml"
    );

    @Override
    public List<String> provideRssUrlList() {
        return RSS_SOURCES;
    }
}
