package com.example.mudit.picscramble.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mudit.picscramble.R;
import com.example.mudit.picscramble.models.view.model.ViewFlickrImageLinkAndState;
import com.example.mudit.picscramble.ui.PicScrambleGameActivity;
import com.example.mudit.picscramble.utils.AppConstants;
import com.example.mudit.picscramble.utils.AppUtils;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by mudit on 1/8/16.
 */
public class RecyclerViewGridAdapter extends RecyclerView.Adapter<RecyclerViewGridAdapter.ViewHolder> {

    private List<ViewFlickrImageLinkAndState> mViewFlickrImageLinkAndStateList;
    private Context mContext;
    private int mNumImagesLoaded = 0;
    private PicScrambleGameActivity mPicScrambleGameActivity;
    private boolean mIsGameInProgress = false;
    private boolean mIsInitialLoadOfImages = true;
    private String mCurrentImageToFind;
    private int mNumImagesFound = 0;

    public RecyclerViewGridAdapter(List<ViewFlickrImageLinkAndState> viewFlickrImageLinkAndStateList,
                                   Context context, PicScrambleGameActivity picScrambleGameActivity) {
        mViewFlickrImageLinkAndStateList = viewFlickrImageLinkAndStateList;
        mContext = context;
        mPicScrambleGameActivity = picScrambleGameActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivGridViewImageItem;

        public ViewHolder(View v) {
            super(v);
            ivGridViewImageItem = (ImageView) v.findViewById(R.id.ivGridViewImageItem);
        }
    }

    @Override
    public RecyclerViewGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.grid_view_image_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewGridAdapter.ViewHolder holder, final int position) {

        Uri uri = Uri.parse(mViewFlickrImageLinkAndStateList.get(position).getImageDownloadLink());

        Log.d("URI is", "Uri is " + uri);

        if (mIsInitialLoadOfImages) {

            Picasso.with(mContext)
                    .load(uri)
                    .resize(200, 200)
                    .into(holder.ivGridViewImageItem, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            mNumImagesLoaded++;
                            if (mNumImagesLoaded >= 9) {
                                mPicScrambleGameActivity.startGameAfterImagesAreLoaded();
                                mIsInitialLoadOfImages = false;
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            if (mViewFlickrImageLinkAndStateList.get(position).isFoundInGame()) {
                Picasso.with(mContext)
                        .load(uri)
                        .resize(200, 200)
                        .into(holder.ivGridViewImageItem);
            } else {
                Picasso.with(mContext)
                        .load(R.drawable.placeholder)
                        .resize(200, 200)
                        .into(holder.ivGridViewImageItem);
            }
        }

        holder.ivGridViewImageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsGameInProgress) {
                    if (mCurrentImageToFind.equals(mViewFlickrImageLinkAndStateList.
                            get(position).getImageDownloadLink())) {

                        //AppUtils.showToastShort("Bingo you have guessed right..",mContext);

                        mViewFlickrImageLinkAndStateList.get(position).setFoundInGame(true);
                        notifyDataSetChanged();

                        mNumImagesFound++;
                        if (mNumImagesFound == AppConstants.TOTAL_IMAGES_IN_GAME) {
                            mIsGameInProgress = false;
                            mPicScrambleGameActivity.finishGame();
                        } else {
                            mPicScrambleGameActivity.showImageToFind();
                        }


                    }else{
                        //AppUtils.showToastShort("Oops wrong guess.. Try Again",mContext);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mViewFlickrImageLinkAndStateList.size();
    }

    public void setIsInitialLoadOfImages(boolean isInitialLoadOfImages) {
        mIsInitialLoadOfImages = isInitialLoadOfImages;
    }

    public void setCurrentImageToFind(String imageToFind) {
        mCurrentImageToFind = imageToFind;
    }

    public void setIsGameInProgress(boolean isGameInProgress) {
        mIsGameInProgress = isGameInProgress;
    }
}
