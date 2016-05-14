package ru.rambler.kiyakovyacheslav.model.mapper;

import com.einmalfel.earl.Item;

import java.util.ArrayList;
import java.util.List;

import ru.rambler.kiyakovyacheslav.model.RssItem;

public class RssItemMapper {

    public static RssItem convertItemToRssItem(Item item, String websiteProvider) {
        RssItem rssItem = new RssItem();
        rssItem.setTitle(item.getTitle());
        rssItem.setDescription(item.getDescription());
        rssItem.setImageLink(item.getImageLink());
        rssItem.setPublicationDate(item.getPublicationDate());
        rssItem.setWebsiteProvider(websiteProvider);
        return rssItem;
    }

    public static List<RssItem> convertItemsToRssItems(List<? extends Item> items, String websiteProvier) {
        List<RssItem> result = new ArrayList<>(items.size());
        for (Item item : items) {
            result.add(convertItemToRssItem(item, websiteProvier));
        }
        return result;
    }
}
