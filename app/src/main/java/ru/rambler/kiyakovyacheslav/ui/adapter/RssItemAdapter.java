package ru.rambler.kiyakovyacheslav.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rambler.kiyakovyacheslav.R;
import ru.rambler.kiyakovyacheslav.model.RssItem;

public class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.RssItemViewHolder> {
    private List<RssViewItem> rssViewItems = Collections.emptyList();
    private OnRecyclerViewItemClickListener<RssViewItem> onItemClickListener = null;

    public RssItemAdapter(@Nullable OnRecyclerViewItemClickListener<RssViewItem> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setRssViewItems(List<RssViewItem> rssViewItems) {
        this.rssViewItems = rssViewItems;
    }

    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_item, parent, false);
        return new RssItemViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {
        RssViewItem rssViewItem = rssViewItems.get(position);
        holder.populate(rssViewItem);
    }

    @Override
    public int getItemCount() {
        return rssViewItems.size();
    }

    class RssItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView iconImageView;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.description)
        TextView descriptionTextView;

        @BindView(R.id.publication_date)
        TextView publicationDateTextView;

        public RssItemViewHolder(View itemView, OnRecyclerViewItemClickListener<RssViewItem> listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (listener != null) {
//                itemView.setOnClickListener(v -> {
//                    int adapterPosition = getAdapterPosition();
//                    RssViewItem rssViewItem = rssViewItems.get(adapterPosition);
//                    listener.onItemClicked(rssViewItem, adapterPosition);
//                });
            }
        }

        public void populate(RssViewItem item) {
            RssItem rssItem = item.getRssItem();
            titleTextView.setText(rssItem.getTitle());
            descriptionTextView.setText(rssItem.getDescription());
            // TODO show the publication date and icon
            // TODO handle expanded flag
        }
    }

    public static class RssViewItem {
        private RssItem rssItem;
        private boolean isExpanded;

        public RssViewItem(RssItem rssItem) {
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
}
