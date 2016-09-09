package ru.rambler.kiyakovyacheslav.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.rambler.kiyakovyacheslav.model.RssItemVO;
import ru.rambler.kiyakovyacheslav.util.comparator.RssViewItemByDateComparator;

public class RssFeedCache {
    private final List<RssItemVO> cachedItems;
    private final RssViewItemByDateComparator rssViewItemByDateComparator = new RssViewItemByDateComparator();

    public RssFeedCache(int defaultSize) {
        if (defaultSize <= 0) {
            this.cachedItems = new ArrayList<>();
        } else {
            this.cachedItems = new ArrayList<>(defaultSize);
        }
    }

    /**
     * Adds the provided items to the cache keeping sorting by date in descending order
     */
    public void addItemsKeepingSortByDate(List<RssItemVO> items) {
        cachedItems.addAll(items);
        // sorting all the items by date in descending order
        Collections.sort(cachedItems, rssViewItemByDateComparator);

    }

    public List<RssItemVO> getItems() {
        return cachedItems;
    }

    public void clear() {
        cachedItems.clear();
    }

    public boolean isEmpty() {
        return cachedItems.isEmpty();
    }
}
