package com.example.mudit.picscramble.presenters;

import android.app.Application;
import android.util.Log;

import com.example.mudit.picscramble.PicScrambleApp;
import com.example.mudit.picscramble.interactors.FlickrPublicJsonFeedFetchInteractor;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedFetchPresenter;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedFinishedListener;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedView;
import com.example.mudit.picscramble.models.FlickrJsonFeed;

import javax.inject.Inject;

/**
 * Created by mudit on 31/7/16.
 */

public class FlickrPublicJsonFeedFetchPresenter implements IGetFlickrPublicJsonFeedFetchPresenter,
        IGetFlickrPublicJsonFeedFinishedListener {

    private IGetFlickrPublicJsonFeedView mGetFlickrPublicJsonFeedView;
//
    @Inject
    FlickrPublicJsonFeedFetchInteractor mFlickrPublicJsonFeedFetchInteractor;

    private static final String TAG = FlickrPublicJsonFeedFetchPresenter.class.getName();

    public FlickrPublicJsonFeedFetchPresenter(Application application,
                                              IGetFlickrPublicJsonFeedView getFlickrPublicJsonFeedView) {

        //set view and inject interactor dependency
        mGetFlickrPublicJsonFeedView = getFlickrPublicJsonFeedView;


        ((PicScrambleApp)application).getAppComponent().inject(this);

        //set finished listener for transferring results
         mFlickrPublicJsonFeedFetchInteractor.setGetFlickrPublicJsonFeedFinishedListener(this);
    }

    @Override
    public void fetchFlickrJsonFeed() {
        mFlickrPublicJsonFeedFetchInteractor.fetchFlickrJsonFeed();
    }

    @Override
    public void onFlickrPublicJsonFeedFetchSuccess(FlickrJsonFeed flickrJsonFeed) {
       mGetFlickrPublicJsonFeedView.onFlickrPublicJsonFeedFetchSuccess(flickrJsonFeed);
    }

    @Override
    public void onFlickrPublicJsonFeedFetchError(String message) {
        mGetFlickrPublicJsonFeedView.onFlickrPublicJsonFeedFetchError(message);
    }
}
