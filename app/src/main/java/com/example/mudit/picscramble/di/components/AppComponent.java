package com.example.mudit.picscramble.di.components;

import com.example.mudit.picscramble.PicScrambleApp;
import com.example.mudit.picscramble.di.modules.AppModule;
import com.example.mudit.picscramble.di.modules.RetrofitModule;
import com.example.mudit.picscramble.interactors.FlickrPublicJsonFeedFetchInteractor;
import com.example.mudit.picscramble.presenters.FlickrPublicJsonFeedFetchPresenter;
import com.example.mudit.picscramble.ui.PicScrambleGameActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mudit on 31/7/16.
 */
@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class})
public interface AppComponent {
    //write where to inject
    void inject(PicScrambleApp picScrambleApp);
    void inject (FlickrPublicJsonFeedFetchInteractor flickrPublicJsonFeedFetchInteractor);
    void inject (PicScrambleGameActivity picScrambleGameActivity);
    void inject (FlickrPublicJsonFeedFetchPresenter flickrPublicJsonFeedFetchPresenter);
}
