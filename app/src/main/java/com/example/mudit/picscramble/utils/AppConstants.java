package com.example.mudit.picscramble.utils;

/**
 * Created by mudit on 28/7/16.
 */
public class AppConstants {

    //flickr response format
    public static final String FLICKR_FEED_FORMAT = "json";

    //flickr base URL
    public static final String FLICKR_BASE_URL = "http://api.flickr.com/services/feeds/photos_public.gne?";

    //HTTP Client Timeouts
    public static final int HTTP_CLIENT_READ_TIMEOUT = 15; //seconds
    public static final int HTTP_CLIENT_WRITE_TIMEOUT = 15; //seconds
    public static final int HTTP_CLIENT_CONNECT_TIMEOUT = 15; //seconds view

    //for gridview parameters
    public static final int GRID_PADDING = 8;

    //max num images in game
    public static final int TOTAL_IMAGES_IN_GAME = 9;

    public static final String GAME_STATUS = "GameStatusMessage";

    public static final int IMAGE_SHOW_TIME = 15;
}
