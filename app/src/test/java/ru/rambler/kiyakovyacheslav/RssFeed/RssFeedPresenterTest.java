package ru.rambler.kiyakovyacheslav.RssFeed;

import com.einmalfel.earl.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.rambler.kiyakovyacheslav.BaseTest;
import ru.rambler.kiyakovyacheslav.RssFeed.di.RssViewTestComponent;
import ru.rambler.kiyakovyacheslav.TestApplication;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter.RssViewItem;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedPresenter;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssFeedView;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.IRssUrlProvider;
import ru.rambler.kiyakovyacheslav.util.RssFeedCache;
import ru.rambler.kiyakovyacheslav.util.RxUtil;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class RssFeedPresenterTest extends BaseTest {

    @Inject
    IRssFeedPresenter rssFeedPresenter;

    @Inject
    IRssFeedManager rssFeedManagerMock;

    @Inject
    IRssUrlProvider rssUrlProviderMock;

    @Inject
    RssFeedCache rssFeedCache;

    @Inject
    RxUtil rxUtilMock;


    private IRssFeedView rssFeedViewMock;

    @Before
    public void setUp() throws Exception {
        rssFeedViewMock = mock(IRssFeedView.class);
        RssViewTestComponent rssViewComponent = (RssViewTestComponent) TestApplication.getRssViewComponent(rssFeedViewMock);
        rssViewComponent.inject(this);
    }

    @Test
    public void onRefreshRequested_showsProgressView() {
        rssFeedPresenter.onRefreshItemsRequested();

        Mockito.verify(rssFeedViewMock).showRefreshProgressView();
        verify(rssFeedViewMock, never()).showError(anyString());
    }

    /**
     * Tests that if fragment was reloaded due to configuration change(e.g. screen got flipped) we don't try to load
     * items if they are cached
     */
    @Test
    public void onViewStarted_showsPreviouslyLoadedItemsIfPresent() {
        List<RssViewItem> cachedListMock = Arrays.asList(mock(RssViewItem.class));
        rssFeedCache.addItemsKeepingSortByDate(cachedListMock);

        rssFeedPresenter.onViewStarted();

        verify(rssFeedViewMock).showRssItems(cachedListMock);
        verify(rssFeedViewMock, never()).showRefreshProgressView(); // no progress view should be shown since we show cached values
        verifyZeroInteractions(rssFeedManagerMock);
        verifyZeroInteractions(rssUrlProviderMock);
        verify(rssFeedViewMock, never()).showError(anyString());
    }

    /**
     * Tests that if fragment was reloaded due to configuration change(e.g. screen got flipped) and we don't have
     * cached items, then we load new items
     */
    @Test
    public void onViewStarted_loadNewItemsIfThereAreNoCached() {
        rssFeedCache.clear();

        rssFeedPresenter.onViewStarted();

        verify(rssFeedViewMock).showRefreshProgressView();
        verify(rssUrlProviderMock).provideRssUrlList();
        verify(rssFeedViewMock, never()).showError(anyString());
    }

    @Test
    public void onRssItemClicked_expandedItemGetsCollapsed() {
        RssViewItem rssItemMock = mock(RssViewItem.class);
        int position = 0;
        boolean expanded = true;
        when(rssItemMock.isExpanded()).thenReturn(expanded);

        rssFeedPresenter.onRssItemClicked(rssItemMock, position);

        verify(rssFeedViewMock).collapseRssItem(rssItemMock, position);
        verify(rssFeedViewMock, never()).expandRssItem(any(), anyInt());
        // RssViewItem expanded state should not be affected by the presenter, it's task of view
        verify(rssItemMock, never()).setExpanded(anyBoolean());
        verify(rssFeedViewMock, never()).showError(anyString());
    }

    @Test
    public void onRssItemClicked_collapsedItemGetsExpanded() {
        RssViewItem rssItemMock = mock(RssViewItem.class);
        int position = 0;
        boolean expanded = false;
        when(rssItemMock.isExpanded()).thenReturn(expanded);

        rssFeedPresenter.onRssItemClicked(rssItemMock, position);

        verify(rssFeedViewMock).expandRssItem(rssItemMock, position);
        verify(rssFeedViewMock, never()).collapseRssItem(any(), anyInt());
        // RssViewItem expanded state should not be affected by the presenter, it's task of view
        verify(rssItemMock, never()).setExpanded(anyBoolean());
        verify(rssFeedViewMock, never()).showError(anyString());
    }

    @Test
    public void onRefreshItemsRequested_loadsItems() {
        rssFeedPresenter.onRefreshItemsRequested();

        verify(rssFeedViewMock).showRefreshProgressView();
        verify(rssUrlProviderMock).provideRssUrlList();
        verify(rssFeedViewMock, never()).showError(anyString());
    }

    @Test
    public void onRefreshItemsRequested_canShowError() {
        String url = "http://test.com/rss";
        when(rssUrlProviderMock.provideRssUrlList()).thenReturn(Arrays.asList(url));
        Observable<List<? extends Item>> testObservable = Observable.error(mock(Throwable.class));
        when(rssFeedManagerMock.downloadRssFeed(anyString())).thenReturn(testObservable);

        rssFeedPresenter.onRefreshItemsRequested();

        verify(rssFeedViewMock, times(1)).showError(anyString());
        verify(rssFeedViewMock).hideRefreshProgressView();
    }

    @Test
    public void onRefreshItemsRequested_canShowDownloadedItems() {
        String url = "http://test.com/rss";
        when(rssUrlProviderMock.provideRssUrlList()).thenReturn(Arrays.asList(url));
        List<? extends Item> testItems = Arrays.asList(mock(Item.class));
        Observable<List<? extends Item>> testObservable = Observable.just(testItems);
        when(rssFeedManagerMock.downloadRssFeed(anyString())).thenReturn(testObservable);

        rssFeedPresenter.onRefreshItemsRequested();

        verify(rssFeedViewMock).showRssItems(nonEmptyList());
        verify(rssFeedViewMock).hideRefreshProgressView();
        verify(rssFeedCache).addItemsKeepingSortByDate(anyList()); // the downloaded items must be cached
        verify(rssFeedViewMock, never()).showError(anyString());
    }
}
