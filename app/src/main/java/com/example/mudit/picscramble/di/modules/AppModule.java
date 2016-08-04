package com.example.mudit.picscramble.di.modules;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mudit on 31/7/16.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    //provide application inject this where ever required
    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    //use where object mapper is required to convert json to pojo and vice versa
    @Provides
    ObjectMapper providesDataMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        return  mapper;
    }

}

