package com.example.mudit.picscramble.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudit on 31/7/16.
 */
public class FlickrJsonFeed {

    @JsonProperty("title")
    private String title;
    @JsonProperty("link")
    private String link;
    @JsonProperty("description")
    private String description;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("generator")
    private String generator;
    @JsonProperty("items")
    private List<Item> items = new ArrayList<Item>();

    /**
     *
     * @return
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The link
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The modified
     */
    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    /**
     *
     * @param modified
     * The modified
     */
    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     *
     * @return
     * The generator
     */
    @JsonProperty("generator")
    public String getGenerator() {
        return generator;
    }

    /**
     *
     * @param generator
     * The generator
     */
    @JsonProperty("generator")
    public void setGenerator(String generator) {
        this.generator = generator;
    }

    /**
     *
     * @return
     * The items
     */
    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

}