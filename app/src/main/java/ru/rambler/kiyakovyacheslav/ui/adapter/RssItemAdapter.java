package ru.rambler.kiyakovyacheslav.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rambler.kiyakovyacheslav.R;
import ru.rambler.kiyakovyacheslav.model.RssItem;
import ru.rambler.kiyakovyacheslav.model.RssItemVO;

public class RssItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int RSS_ITEM_VIEW_TYPE = 0;
    private final int EMPTY_VIEW_TYPE = 1;

    private final Picasso picasso;

    private List<RssItemVO> rssItemVOs = Collections.emptyList();
    private Callback callback;
    private boolean isEmptyViewShown = false;

    public RssItemAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    public void setRssItemVOs(List<RssItemVO> rssItemVOs) {
        this.rssItemVOs = rssItemVOs;
    }

    public void setEmptyViewShown(boolean emptyViewShown, RecyclerView recyclerView) {
        if (this.isEmptyViewShown == emptyViewShown) {
            return;
        }
        this.isEmptyViewShown = emptyViewShown;
        recyclerView.post(() -> notifyDataSetChanged());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case RSS_ITEM_VIEW_TYPE:
                return new RssItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_rss_news, parent, false));
            case EMPTY_VIEW_TYPE:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_empty_view, parent, false));
            default:
                throw new IllegalStateException("Unknown view type: " + viewType);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isEmptyViewShown) {
            return EMPTY_VIEW_TYPE;
        }
        return RSS_ITEM_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RssItemViewHolder) {
            RssItemVO rssItemVO = rssItemVOs.get(position);
            ((RssItemViewHolder) holder).populate(rssItemVO);
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        if (isEmptyViewShown) {
            return 1;
        }
        return rssItemVOs.size();
    }

    class RssItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView iconImageView;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.description)
        TextView descriptionTextView;

        @BindView(R.id.website)
        TextView websiteTextView;

        public RssItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (callback == null) {
                    return;
                }
                int adapterPosition = getAdapterPosition();
                RssItemVO rssItemVO = rssItemVOs.get(adapterPosition);
                callback.onItemClicked(rssItemVO, adapterPosition);
            });
        }

        public void populate(RssItemVO item) {
            RssItem rssItem = item.getRssItem();
            titleTextView.setText(rssItem.getTitle());
            descriptionTextView.setText(rssItem.getDescription());
            descriptionTextView.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);
            websiteTextView.setText(item.getRssItem().getWebsiteProvider());
            String iconUrl = item.getRssItem().getImageLink();
            if (iconUrl == null) {
                iconImageView.setImageResource(R.drawable.black_figure);
            } else {
                picasso.load(iconUrl)
                        .resizeDimen(R.dimen.rss_item_icon_width, R.dimen.rss_item_icon_height)
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.black_figure)
                        .into(iconImageView);
            }
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface Callback {
        void onItemClicked(RssItemVO rssItem, int position);
    }
}
