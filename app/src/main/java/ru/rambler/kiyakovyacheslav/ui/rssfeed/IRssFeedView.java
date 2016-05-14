package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import java.util.List;

import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter.RssViewItem;
import ru.rambler.kiyakovyacheslav.ui.base.IBaseView;

public interface IRssFeedView extends IBaseView {
    void showRssItems(List<RssViewItem> rssItems);
    void expandRssItem(RssViewItem rssViewItem, int position);
    void collapseRssItem(RssViewItem rssViewItem, int position);
    void showRefreshProgressView();
    void hideRefreshProgressView();
}
