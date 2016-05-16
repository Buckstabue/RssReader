package ru.rambler.kiyakovyacheslav.model.mapper;

import android.support.annotation.Nullable;

import com.einmalfel.earl.Enclosure;
import com.einmalfel.earl.Item;

import java.util.List;

import ru.rambler.kiyakovyacheslav.model.RssItem;
import ru.rambler.kiyakovyacheslav.util.StringUtil;
import rx.Observable;

public class RssItemMapper {

    public static RssItem convertItemToRssItem(Item item, String websiteProvider) {
        RssItem rssItem = new RssItem();
        rssItem.setTitle(StringUtil.trim(item.getTitle()));
        rssItem.setDescription(StringUtil.trim(item.getDescription()));
        rssItem.setPublicationDate(item.getPublicationDate());
        rssItem.setWebsiteProvider(websiteProvider);
        String imageLink = item.getImageLink();
        if (imageLink != null) {
            rssItem.setImageLink(imageLink.trim());
        } else {
            rssItem.setImageLink(getImageLinkFromEnclosure(item));
        }
        return rssItem;
    }

    public static List<RssItem> convertItemsToRssItems(List<? extends Item> items, String websiteProvider) {
        return Observable.from(items)
                .map(item -> convertItemToRssItem(item, websiteProvider))
                .toList()
                .toBlocking()
                .first();
    }

    /**
     * Attempts to find out an image in item.getEnclosures()
     *
     * @return image link from enclosures or null
     */
    @Nullable
    private static String getImageLinkFromEnclosure(Item item) {
        for (Enclosure enclosure : item.getEnclosures()) {
            String type = enclosure.getType();
            if (type != null && type.startsWith("image/")) {
                return enclosure.getLink().trim();
            }
        }
        return null;
    }
}
