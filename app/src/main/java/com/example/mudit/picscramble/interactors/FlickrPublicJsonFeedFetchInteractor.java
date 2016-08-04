package com.example.mudit.picscramble.interactors;

import android.util.Log;

import com.example.mudit.picscramble.asyncTask.FlickrFeedFetchAsyncTask;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedFetchInteractor;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedFinishedListener;
import com.example.mudit.picscramble.models.FlickrJsonFeed;
import com.example.mudit.picscramble.utils.AppConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;

/**
 * Created by mudit on 31/7/16.
 */
public class FlickrPublicJsonFeedFetchInteractor implements IGetFlickrPublicJsonFeedFetchInteractor {

    private
    ObjectMapper mMapper;

    private IGetFlickrPublicJsonFeedFinishedListener mGetFlickrPublicJsonFeedFinishedListener;

    public static final String TAG = FlickrPublicJsonFeedFetchInteractor.class.getName();


    public void setGetFlickrPublicJsonFeedFinishedListener
            (IGetFlickrPublicJsonFeedFinishedListener getFlickrPublicJsonFeedFinishedListener) {

        mGetFlickrPublicJsonFeedFinishedListener = getFlickrPublicJsonFeedFinishedListener;
    }


    @Inject
    public FlickrPublicJsonFeedFetchInteractor(ObjectMapper mapper) {
        mMapper = mapper;
    }

    static JsonNode convertJsonFormatFromObject(JSONObject json) {
        ObjectNode ret = JsonNodeFactory.instance.objectNode();

        @SuppressWarnings("unchecked")
        Iterator<String> iterator = json.keys();
        for (; iterator.hasNext();) {
            String key = iterator.next();
            Object value;
            try {
                value = json.get(key);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            if (json.isNull(key))
                ret.putNull(key);
            else if (value instanceof String)
                ret.put(key, (String) value);
            else if (value instanceof Integer)
                ret.put(key, (Integer) value);
            else if (value instanceof Long)
                ret.put(key, (Long) value);
            else if (value instanceof Double)
                ret.put(key, (Double) value);
            else if (value instanceof Boolean)
                ret.put(key, (Boolean) value);
            else if (value instanceof JSONObject)
            {
                try {
                    ret.put(key, convertJsonFormatFromObject((JSONObject) value));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            else if (value instanceof JSONArray)
                ret.put(key, convertJsonFormatFromArray((JSONArray) value));
            else
                throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
        }
        return ret;
    }

    static JsonNode convertJsonFormatFromArray(JSONArray json) {
        ArrayNode ret = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < json.length(); i++) {
            Object value;
            try {
                value = json.get(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            if (json.isNull(i))
                ret.addNull();
            else if (value instanceof String)
                ret.add((String) value);
            else if (value instanceof Integer)
                ret.add((Integer) value);
            else if (value instanceof Long)
                ret.add((Long) value);
            else if (value instanceof Double)
                ret.add((Double) value);
            else if (value instanceof Boolean)
                ret.add((Boolean) value);
            else if (value instanceof JSONObject)
                ret.add(convertJsonFormatFromObject((JSONObject) value));
            else if (value instanceof JSONArray)
                ret.add(convertJsonFormatFromArray((JSONArray) value));
            else
                throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
        }
        return ret;
    }


    @Override
    public void fetchFlickrJsonFeed() {

        final String flickrFeedFetchUrl = AppConstants.FLICKR_BASE_URL + "format=" + AppConstants.FLICKR_FEED_FORMAT;

        Log.d(TAG, "Flickr feed fetch Url " + flickrFeedFetchUrl);

        String result = "";

        try {
            result = new FlickrFeedFetchAsyncTask()
                    .execute()
                    .get();


        } catch (Exception e) {
            e.printStackTrace();
        }

        //remove the extra characters from string
        result = result.substring(15);
        int lastValue = result.lastIndexOf(")");
        StringBuilder sb = new StringBuilder(result);
        sb.deleteCharAt(lastValue);
        String resultFinal = sb.toString();;

        //update this string heretFinal =

        JSONObject json = null;

        try {
           json = new JSONObject(resultFinal);

        }catch (JSONException e){
            e.printStackTrace();
        }

        FlickrJsonFeed flickrJsonFeed = null;

        try {

            JsonNode jsonNode = convertJsonFormatFromObject(json);
            ObjectMapper mapper = new ObjectMapper();
            flickrJsonFeed = mapper.readValue(new TreeTraversingParser(jsonNode), FlickrJsonFeed.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, flickrJsonFeed.getLink());

        mGetFlickrPublicJsonFeedFinishedListener.onFlickrPublicJsonFeedFetchSuccess(flickrJsonFeed);

    }
}