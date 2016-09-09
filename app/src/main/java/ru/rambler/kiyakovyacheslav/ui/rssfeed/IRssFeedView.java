package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import java.util.List;

import ru.rambler.kiyakovyacheslav.model.RssItemVO;
import ru.rambler.kiyakovyacheslav.ui.base.IBaseView;

public interface IRssFeedView extends IBaseView {
    void showRssItems(List<RssItemVO> rssItems);

    void expandRssItem(RssItemVO rssItemVO, int position);

    void collapseRssItem(RssItemVO rssItemVO, int position);

    void showRefreshProgressView();

    void hideRefreshProgressView();

    void showEmptyView();

    void hideEmptyView();
}
