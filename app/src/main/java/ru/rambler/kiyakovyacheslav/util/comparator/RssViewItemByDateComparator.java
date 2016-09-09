package ru.rambler.kiyakovyacheslav.util.comparator;

import java.util.Comparator;
import java.util.Date;

import ru.rambler.kiyakovyacheslav.model.RssItemVO;

/**
 * Used to sort rss items by their dates in descending order
 */
public class RssViewItemByDateComparator implements Comparator<RssItemVO> {

    @Override
    public int compare(RssItemVO lhs, RssItemVO rhs) {
        Date lhsDate = lhs.getRssItem().getPublicationDate();
        if (lhsDate == null) {
            return 1;
        }
        Date rhsDate = rhs.getRssItem().getPublicationDate();
        if (rhsDate == null) {
            return -1;
        }
        return -lhsDate.compareTo(rhsDate);
    }
}
