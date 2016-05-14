package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ru.rambler.kiyakovyacheslav.App;
import ru.rambler.kiyakovyacheslav.model.IRssFeedManager;
import ru.rambler.kiyakovyacheslav.model.RssItem;
import ru.rambler.kiyakovyacheslav.model.mapper.RssItemMapper;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter.RssViewItem;
import ru.rambler.kiyakovyacheslav.ui.base.BasePresenter;
import ru.rambler.kiyakovyacheslav.util.RxUtil;
import ru.rambler.kiyakovyacheslav.util.comparator.RssViewItemByDateComparator;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class RssFeedPresenter extends BasePresenter implements IRssFeedPresenter {
    private static final String[] RSS_SOURCES = {
//            "http://lenta.ru/rss",
            "http://www.gazeta.ru/export/rss/lenta.xml"
    };

    @Inject
    IRssFeedManager rssFeedManager;

    private IRssFeedView rssFeedView;


    public RssFeedPresenter(IRssFeedView rssFeedView) {
        this.rssFeedView = rssFeedView;
        App.getAppComponent().inject(this);
    }

    @Override
    public void onRssItemClicked(RssViewItem rssViewItem, int position) {
        if (rssViewItem.isExpanded()) {
            rssFeedView.collapseRssItem(rssViewItem, position);
        } else {
            rssFeedView.expandRssItem(rssViewItem, position);
        }
    }

    @Override
    public void loadRssItems() {
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
        List<Observable<List<RssItem>>> downloadObservableList = new ArrayList<>(RSS_SOURCES.length);
        for (String rssUrl : RSS_SOURCES) {
            String website = parseSourceWebsite(rssUrl);
            Observable<List<RssItem>> observable = rssFeedManager.downloadRssFeed(rssUrl)
                    .map(items -> RssItemMapper.convertItemsToRssItems(items, website));
            downloadObservableList.add(RxUtil.prepareIOObservable(observable));
        }
        return Observable.mergeDelayError(downloadObservableList);
    }

    private String parseSourceWebsite(String url) {
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return url;
        }
    }

    private class RssFeedSubscriber implements Observer<List<RssItem>> {
        // TODO set default list size based on experience
        private final List<RssViewItem> rssViewList = new ArrayList<>();
        private final RssViewItemByDateComparator rssViewItemByDateComparator = new RssViewItemByDateComparator();

        @Override
        public void onCompleted() {
            rssFeedView.hideRefreshProgressView();
        }

        @Override
        public void onError(Throwable e) {
            rssFeedView.hideRefreshProgressView();
            rssFeedView.showError(e.getMessage());
        }

        @Override
        public void onNext(List<RssItem> rssItems) {
            showItemsInUI(rssItems);
        }

        private void showItemsInUI(List<RssItem> rssItems) {
            List<RssViewItem> newItems = Observable.from(rssItems)
                    .map(RssViewItem::new)
                    .toList()
                    .toBlocking()
                    .first();
            rssViewList.addAll(newItems);
            // sorting all the items by their date
            Collections.sort(rssViewList, rssViewItemByDateComparator);
            rssFeedView.showRssItems(rssViewList);
        }
    }
}
