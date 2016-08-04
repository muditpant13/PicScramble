package com.example.mudit.picscramble.rest;

import com.example.mudit.picscramble.models.FlickrJsonFeed;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.http.Url;

/**
 * Created by mudit on 28/7/16.
 */
public interface RetrofitClient {
    @GET
    Call <FlickrJsonFeed> getFlickrPublicFeedInJson(@Url String flickrPublicFeedLoadUrl);
}
