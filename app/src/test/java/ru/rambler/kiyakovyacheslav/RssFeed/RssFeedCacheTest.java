package ru.rambler.kiyakovyacheslav.RssFeed;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ru.rambler.kiyakovyacheslav.BaseTest;
import ru.rambler.kiyakovyacheslav.RssFeed.di.RssViewTestComponent;
import ru.rambler.kiyakovyacheslav.TestApplication;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter.RssViewItem;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.util.RssFeedCache;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RssFeedCacheTest extends BaseTest {

    @Inject
    RssFeedCache rssFeedCache;

    @Before
    public void setUp() throws Exception {
        RssViewTestComponent rssViewComponent =
                (RssViewTestComponent) TestApplication.getRssViewComponent(mock(IRssFeedView.class));
        rssViewComponent.inject(this);
    }

    @Test
    public void emptyByDefault() throws Exception {
        assertTrue(rssFeedCache.isEmpty());
        assertTrue(rssFeedCache.getItems().isEmpty());
    }

    @Test
    public void canAdd() throws Exception {
        List<RssViewItem> items = Arrays.asList(mock(RssViewItem.class));

        rssFeedCache.addItemsKeepingSortByDate(items);
        List<RssViewItem> cachedItems = rssFeedCache.getItems();

        assertFalse(rssFeedCache.isEmpty());
        assertEquals(1, cachedItems.size());
        assertSame(items.get(0), cachedItems.get(0));
    }

    @Test
    public void clearWorks() throws Exception {
        List<RssViewItem> items = Arrays.asList(mock(RssViewItem.class));

        rssFeedCache.addItemsKeepingSortByDate(items);
        rssFeedCache.clear();
        List<RssViewItem> cachedItems = rssFeedCache.getItems();

        assertTrue(rssFeedCache.isEmpty());
        assertEquals(0, cachedItems.size());
    }

    @Test
    public void sortingByDateWorks() {
        RssViewItem mock1 = mock(RssViewItem.class, RETURNS_DEEP_STUBS);
        RssViewItem mock2 = mock(RssViewItem.class, RETURNS_DEEP_STUBS);
        RssViewItem mock3 = mock(RssViewItem.class, RETURNS_DEEP_STUBS);

        when(mock1.getRssItem().getPublicationDate()).thenReturn(new Date(100));
        when(mock2.getRssItem().getPublicationDate()).thenReturn(new Date(200));
        when(mock3.getRssItem().getPublicationDate()).thenReturn(new Date(300));

        rssFeedCache.addItemsKeepingSortByDate(Arrays.asList(mock2, mock1, mock3));
        List<RssViewItem> cachedItems = rssFeedCache.getItems();

        assertEquals(3, cachedItems.size());
        assertSame(mock1, cachedItems.get(2));
        assertSame(mock2, cachedItems.get(1));
        assertSame(mock3, cachedItems.get(0));
    }
}
