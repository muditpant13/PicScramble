package com.example.mudit.picscramble.interfaces;

import com.example.mudit.picscramble.models.FlickrJsonFeed;

/**
 * Created by mudit on 31/7/16.
 */
public interface IGetFlickrPublicJsonFeedView {
    void onFlickrPublicJsonFeedFetchSuccess(FlickrJsonFeed flickrJsonFeed);
    void onFlickrPublicJsonFeedFetchError(String message);
}
