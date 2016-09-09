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
import ru.rambler.kiyakovyacheslav.di.component.DaggerRssViewComponent;
import ru.rambler.kiyakovyacheslav.di.component.RssViewComponent;
import ru.rambler.kiyakovyacheslav.di.module.RssViewModule;
import ru.rambler.kiyakovyacheslav.model.RssItemVO;
import ru.rambler.kiyakovyacheslav.ui.adapter.RssItemAdapter;

public class RssFeedFragment extends Fragment implements IRssFeedView {
    public static final String TAG = RssFeedFragment.class.getSimpleName();

    @BindView(R.id.rss_feed)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    IRssFeedPresenter rssFeedPresenter;

    @Inject
    RssItemAdapter adapter;

    public static RssFeedFragment newInstance() {
        RssFeedFragment fragment = new RssFeedFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RssViewComponent component = DaggerRssViewComponent.builder()
                .appComponent(App.getAppComponent())
                .rssViewModule(new RssViewModule(this))
                .build();
        component.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setCallback((item, pos) -> rssFeedPresenter.onRssItemClicked(item, pos));
        swipeRefreshLayout.setOnRefreshListener(() -> rssFeedPresenter.onRefreshItemsRequested());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        rssFeedPresenter.onViewStarted();
    }

    @Override
    public void showRssItems(List<RssItemVO> rssItems) {
        adapter.setRssItemVOs(rssItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void expandRssItem(RssItemVO rssItemVO, int position) {
        rssItemVO.setExpanded(true);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void collapseRssItem(RssItemVO rssItemVO, int position) {
        rssItemVO.setExpanded(false);
        adapter.notifyItemChanged(position);
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
        adapter.setEmptyViewShown(true, recyclerView);
    }

    @Override
    public void hideEmptyView() {
        adapter.setEmptyViewShown(false, recyclerView);
    }

    @Override
    public void onStop() {
        rssFeedPresenter.onStop();
        super.onStop();
    }
}
