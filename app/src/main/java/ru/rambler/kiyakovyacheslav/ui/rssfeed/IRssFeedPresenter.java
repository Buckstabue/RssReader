package ru.rambler.kiyakovyacheslav.ui.rssfeed;


import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter;
import ru.rambler.kiyakovyacheslav.ui.base.IBasePresenter;

public interface IRssFeedPresenter extends IBasePresenter {
    void onRssItemClicked(RssItemAdapter.RssViewItem rssViewItem, int position);
    void loadRssItems();
}
