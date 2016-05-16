package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rambler.kiyakovyacheslav.App;
import ru.rambler.kiyakovyacheslav.R;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter.RssViewItem;
import ru.rambler.kiyakovyacheslav.ui.rssfeed.di.RssViewComponent;

public class RssFeedFragment extends Fragment implements IRssFeedView {

    @BindView(R.id.rss_feed)
    RecyclerView rssRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_view_container)
    View emptyView;

    @BindView(R.id.content_container)
    View contentView;

    @Inject
    IRssFeedPresenter rssFeedPresenter;


    private RssItemAdapter rssItemAdapter;

    public static RssFeedFragment newInstance() {
        RssFeedFragment fragment = new RssFeedFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RssViewComponent component = App.getRssViewComponent(this);
        component.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rss_feed_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        setListeners();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        rssFeedPresenter.onViewStarted();
    }

    private void initViews() {
        rssItemAdapter = new RssItemAdapter((item, pos) -> rssFeedPresenter.onRssItemClicked(item, pos));
        rssRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rssRecyclerView.setAdapter(rssItemAdapter);
        rssRecyclerView.setHasFixedSize(false);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> rssFeedPresenter.onRefreshItemsRequested());
    }

    @Override
    public void showRssItems(List<RssViewItem> rssItems) {
        rssItemAdapter.setRssViewItems(rssItems);
        rssItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void expandRssItem(RssViewItem rssViewItem, int position) {
        rssViewItem.setExpanded(true);
        rssItemAdapter.notifyItemChanged(position);
    }

    @Override
    public void collapseRssItem(RssViewItem rssViewItem, int position) {
        rssViewItem.setExpanded(false);
        rssItemAdapter.notifyItemChanged(position);
    }

    @Override
    public void showRefreshProgressView() {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideRefreshProgressView() {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyView() {
        contentView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        rssFeedPresenter.onStop();
    }
}
