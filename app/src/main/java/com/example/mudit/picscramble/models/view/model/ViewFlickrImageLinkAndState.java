package com.example.mudit.picscramble.models.view.model;

/**
 * Created by mudit on 1/8/16.
 */
public class ViewFlickrImageLinkAndState {

    private String mImageDownloadLink;
    private boolean mIsFoundInGame;

    public String getImageDownloadLink() {
        return mImageDownloadLink;
    }

    public void setImageDownloadLink(String imageDownloadLink) {
        mImageDownloadLink = imageDownloadLink;
    }

    public boolean isFoundInGame() {
        return mIsFoundInGame;
    }

    public void setFoundInGame(boolean foundInGame) {
        mIsFoundInGame = foundInGame;
    }

}
