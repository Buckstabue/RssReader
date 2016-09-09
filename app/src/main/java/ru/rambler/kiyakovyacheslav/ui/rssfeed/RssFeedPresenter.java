package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssItem;
import ru.rambler.kiyakovyacheslav.model.mapper.RssItemMapper;
import ru.rambler.kiyakovyacheslav.model.RssItemVO;
import ru.rambler.kiyakovyacheslav.ui.base.BasePresenter;
import ru.rambler.kiyakovyacheslav.util.RssFeedCache;
import ru.rambler.kiyakovyacheslav.util.RxUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class RssFeedPresenter extends BasePresenter implements IRssFeedPresenter {
    private static final String TAG = RssFeedPresenter.class.getSimpleName();

    /**
     * This constant is used to create a list which will hold all the news items. Set it to the expected number of items
     * to avoid recreating the list
     */
    public static final int EXPECTED_RSS_ITEMS_NUMBER = 250;

    private IRssFeedManager rssFeedManager;
    private RxUtil rxUtil;
    private RssFeedCache rssFeedCache;
    private IRssFeedView rssFeedView;
    private IRssUrlProvider rssUrlProvider;

    public RssFeedPresenter(IRssFeedView rssFeedView,
                            IRssUrlProvider rssUrlProvider,
                            IRssFeedManager rssFeedManager,
                            RxUtil rxUtil,
                            RssFeedCache rssFeedCache) {
        this.rssFeedView = rssFeedView;
        this.rssUrlProvider = rssUrlProvider;
        this.rssFeedManager = rssFeedManager;
        this.rxUtil = rxUtil;
        this.rssFeedCache = rssFeedCache;
    }

    @Override
    public void onRssItemClicked(RssItemVO rssItemVO, int position) {
        if (rssItemVO.isExpanded()) {
            rssFeedView.collapseRssItem(rssItemVO, position);
        } else {
            rssFeedView.expandRssItem(rssItemVO, position);
        }
    }

    @Override
    public void onRefreshItemsRequested() {
        rssFeedView.hideEmptyView();
        loadRssItems();
    }

    @Override
    public void onViewStarted() {
        if (rssFeedCache.isEmpty()) {
            // if the view was started for the first time
            loadRssItems();
        } else {
            // if the view was started due to a configuration change
            rssFeedView.showRssItems(rssFeedCache.getItems());
        }
    }

    private void loadRssItems() {
        rssFeedView.showRefreshProgressView();
        Observable<List<RssItem>> downloadRssItemsObservable = createDownloadRssItemsObservable();
        Subscription subscription = downloadRssItemsObservable.subscribe(new RssFeedSubscriber());
        addSubscription(subscription);
    }

    /**
     * Creates an observable which emits RssItems obtained from RSS_SOURCES as soon as at least one rss feed is processed
     * each emitted list is independent of the previous one, so you should gather and merge them by your own in
     * the subscriber. It uses Observable.mergeDelayError, therefore error events will be sent only after all
     * successfully processed rss feeds
     */
    private Observable<List<RssItem>> createDownloadRssItemsObservable() {
        List<String> urls = rssUrlProvider.provideRssUrlList();
        List<Observable<List<RssItem>>> downloadObservableList = new ArrayList<>(urls.size());
        for (String rssUrl : urls) {
            String website = parseSourceWebsite(rssUrl);
            Observable<List<RssItem>> observable = rssFeedManager.downloadRssFeed(rssUrl)
                    .map(items -> RssItemMapper.toRssItems(items, website));
            downloadObservableList.add(rxUtil.prepareIOObservable(observable));
        }
        return Observable.mergeDelayError(downloadObservableList);
    }

    private String parseSourceWebsite(String url) {
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            Log.e(TAG, "parseSourceWebsite: error", e);
            return url;
        }
    }

    private void onNewRssItemsReceived(List<RssItem> rssItems) {
        if (rssItems.isEmpty()) {
            rssFeedView.showEmptyView();
        } else {
            rssFeedView.hideEmptyView();
            List<RssItemVO> newItems = RssItemMapper.toRssItemVOs(rssItems);
            rssFeedCache.addItemsKeepingSortByDate(newItems);
            rssFeedView.showRssItems(rssFeedCache.getItems());
        }

    }

    private class RssFeedSubscriber implements Observer<List<RssItem>> {
        //        private final List<RssItemVO> rssViewList = new ArrayList<>(EXPECTED_RSS_ITEMS_NUMBER);
        private boolean isFirstEmit = true;

        @Override
        public void onCompleted() {
            rssFeedView.hideRefreshProgressView();
        }

        @Override
        public void onError(Throwable e) {
            rssFeedView.hideRefreshProgressView();
            rssFeedView.showError(e.getMessage());
            if (rssFeedCache.isEmpty()) {
                rssFeedView.showEmptyView();
            }
        }

        @Override
        public void onNext(List<RssItem> rssItems) {
            if (isFirstEmit) {
                rssFeedCache.clear();
                isFirstEmit = false;
            }
            onNewRssItemsReceived(rssItems);
        }
    }
}
