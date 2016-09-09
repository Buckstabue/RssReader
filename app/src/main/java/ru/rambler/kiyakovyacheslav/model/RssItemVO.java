package ru.rambler.kiyakovyacheslav.model;

public class RssItemVO {
    private RssItem rssItem;
    private boolean isExpanded;

    public RssItemVO(RssItem rssItem) {
        this.rssItem = rssItem;
        isExpanded = false;
    }

    public RssItem getRssItem() {
        return rssItem;
    }

    public void setRssItem(RssItem rssItem) {
        this.rssItem = rssItem;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
