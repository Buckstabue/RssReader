package ru.rambler.kiyakovyacheslav.model;

import java.util.Date;

public class RssItem {
    private String title;
    private String description;
    private Date publicationDate;
    private String imageLink;
    private String websiteProvider;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getWebsiteProvider() {
        return websiteProvider;
    }

    public void setWebsiteProvider(String websiteProvider) {
        this.websiteProvider = websiteProvider;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
