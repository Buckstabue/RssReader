package ru.rambler.kiyakovyacheslav.ui.rssfeed;


import ru.rambler.kiyakovyacheslav.model.RssItemVO;
import ru.rambler.kiyakovyacheslav.ui.base.IBasePresenter;

public interface IRssFeedPresenter extends IBasePresenter {
    void onRssItemClicked(RssItemVO rssItemVO, int position);
    void onRefreshItemsRequested();
    void onViewStarted();
}
