package ru.rambler.kiyakovyacheslav.util.comparator;

import java.util.Comparator;
import java.util.Date;

import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter.RssViewItem;

/**
 * Used to sort rss items by their dates
 */
public class RssViewItemByDateComparator implements Comparator<RssViewItem> {

    @Override
    public int compare(RssViewItem lhs, RssViewItem rhs) {
        Date lhsDate = lhs.getRssItem().getPublicationDate();
        if (lhsDate == null) {
            return -1;
        }
        Date rhsDate = rhs.getRssItem().getPublicationDate();
        if (rhsDate == null) {
            return 1;
        }
        return lhsDate.compareTo(rhsDate);
    }
}
