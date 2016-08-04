package com.example.mudit.picscramble.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mudit.picscramble.R;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedFetchPresenter;
import com.example.mudit.picscramble.interfaces.IGetFlickrPublicJsonFeedView;
import com.example.mudit.picscramble.models.FlickrJsonFeed;
import com.example.mudit.picscramble.models.view.model.ViewFlickrImageLinkAndState;
import com.example.mudit.picscramble.presenters.FlickrPublicJsonFeedFetchPresenter;
import com.example.mudit.picscramble.ui.adapters.RecyclerViewGridAdapter;
import com.example.mudit.picscramble.ui.decorator.RecyclerViewSpacesItemDecoration;
import com.example.mudit.picscramble.utils.AppConstants;
import com.example.mudit.picscramble.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PicScrambleGameActivity extends Activity implements IGetFlickrPublicJsonFeedView {

    private IGetFlickrPublicJsonFeedFetchPresenter mGetFlickrPublicJsonFeedFetchPresenter;
    private Application mApplication;

    private List<ViewFlickrImageLinkAndState> mViewFlickrImageLinkAndStateList = new ArrayList<>();

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private ImageView mIvImageToGuessHolder;

    private RecyclerView.Adapter mRecycilerViewGridAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewGridAdapter mRecyclerViewGridAdapterInstance;

    //for countdown
    private CountDownTimer mCountDownTimer;
    private int mCountdownProgress = 0;
    private Activity mActivity;
    private int mCurrentPositionImageToFind = -1;

    private TextView mTvCountDownTimerTime;
    private RelativeLayout mRlProgressBarLayout;

    List<String> mImageFetchedInGameList = new ArrayList<>();
    List<Integer> mRandomImagePositionList = new ArrayList<>();

    private Context mContext;

    private static final String TAG = PicScrambleGameActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvImageGridView);
        mProgressBar = (ProgressBar) findViewById(R.id.pbTimer);
        mIvImageToGuessHolder = (ImageView) findViewById(R.id.ivImageToGuessHolder);
        mTvCountDownTimerTime = (TextView) findViewById(R.id.tvCountDownTimerTime);
        mRlProgressBarLayout = (RelativeLayout) findViewById(R.id.rlProgressBarLayout);

        mIvImageToGuessHolder.setVisibility(View.INVISIBLE);
        mRlProgressBarLayout.setVisibility(View.INVISIBLE);

        //set progress of progress bar
        mProgressBar.setProgress(mCountdownProgress);

        mContext = this.getBaseContext();


        mApplication = getApplication();
        mGetFlickrPublicJsonFeedFetchPresenter = new FlickrPublicJsonFeedFetchPresenter(mApplication
                , this);

        int spacingInPixels = (int) getPaddingValueForGrid();

        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(spacingInPixels));


        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mRecycilerViewGridAdapter = new RecyclerViewGridAdapter(mViewFlickrImageLinkAndStateList, this, this);
        mRecyclerViewGridAdapterInstance = (RecyclerViewGridAdapter) mRecycilerViewGridAdapter;


        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mRecycilerViewGridAdapter);


        mGetFlickrPublicJsonFeedFetchPresenter.fetchFlickrJsonFeed();


    }

    private float getPaddingValueForGrid() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstants.GRID_PADDING, r.getDisplayMetrics());
        return padding;
    }

    private void getKRandomNumbersFromN(FlickrJsonFeed flickrJsonFeed) {
        Set<Integer> randomKIntegerSet = AppUtils.getKRandomIntegersFromN
                (flickrJsonFeed.getItems().size(), 9);

        //clear the list here for restart of game
        mImageFetchedInGameList.clear();
        mRandomImagePositionList.clear();

        for (int i = 0; i < flickrJsonFeed.getItems().size(); i++) {
            if (randomKIntegerSet.contains((i + 1))) {
                ViewFlickrImageLinkAndState viewFlickrImageLinkAndState = new ViewFlickrImageLinkAndState();
                viewFlickrImageLinkAndState.setFoundInGame(false);
                viewFlickrImageLinkAndState.setImageDownloadLink
                        (flickrJsonFeed.getItems().get(i).getMedia().getM());
                mViewFlickrImageLinkAndStateList.add(viewFlickrImageLinkAndState);

                //add the image here so we can identify if the image has been identified
                mImageFetchedInGameList.add
                        (flickrJsonFeed.getItems().get(i).getMedia().getM());
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecycilerViewGridAdapter.notifyDataSetChanged();
            }
        });

    }

    public void finishGame(){
        Bundle gameFinishData = new Bundle();
        gameFinishData.putString(AppConstants.GAME_STATUS,
                "Congratulations game finished. Press the Restart button to play again.");
        Intent gameFinishIntent = new Intent(PicScrambleGameActivity.this, GameFinishActivity.class);
        gameFinishIntent.putExtras(gameFinishData);
        PicScrambleGameActivity.this.startActivity(gameFinishIntent);
    }

    public void startGameAfterImagesAreLoaded() {

        mCountdownProgress = AppConstants.IMAGE_SHOW_TIME;

        mProgressBar.setProgress(mCountdownProgress);
        mRlProgressBarLayout.setVisibility(View.VISIBLE);
        mTvCountDownTimerTime.setText(String.valueOf(AppConstants.IMAGE_SHOW_TIME));


        mCountDownTimer = new CountDownTimer(15000, 1000) {

            int progress = AppConstants.IMAGE_SHOW_TIME;

            @Override
            public void onTick(long millisUntilFinished) {
                mCountdownProgress--;
                progress -- ;
                mProgressBar.setProgress(mCountdownProgress);
                mTvCountDownTimerTime.setText(String.valueOf(progress));

            }

            @Override
            public void onFinish() {

                mCountdownProgress--;
                mProgressBar.setProgress(mCountdownProgress);
                mRlProgressBarLayout.setVisibility(View.INVISIBLE);
                mTvCountDownTimerTime.setText(String.valueOf(progress));
                mRecyclerViewGridAdapterInstance.setIsInitialLoadOfImages(false);
                mRecyclerViewGridAdapterInstance.setIsGameInProgress(true);
                mRecycilerViewGridAdapter.notifyDataSetChanged();

                //show first image here take the value from hashset
                mCurrentPositionImageToFind = 0;
                mRandomImagePositionList = AppUtils.getKRandomIntegerList(9, 9);

                showImageToFind();
            }
        };
        mCountDownTimer.start();
    }

    public void showImageToFind() {

        mIvImageToGuessHolder.setVisibility(View.VISIBLE);

        int position = mRandomImagePositionList.get(mCurrentPositionImageToFind);

        Uri uri = Uri.parse(mImageFetchedInGameList.get(position - 1));

        Picasso.with(mContext)
                .load(uri)
                .resize(200, 200)
                .into(mIvImageToGuessHolder);
        
        mCurrentPositionImageToFind ++;

        mRecyclerViewGridAdapterInstance.setCurrentImageToFind(mImageFetchedInGameList.get(position - 1));

    }

    @Override
    public void onFlickrPublicJsonFeedFetchSuccess(final FlickrJsonFeed flickrJsonFeed) {

        //get random integers here and then add items from these to viewFlickrList
        Thread getRandomKIntegersFromN = new Thread(new Runnable() {
            @Override
            public void run() {
                getKRandomNumbersFromN(flickrJsonFeed);
            }
        });
        getRandomKIntegersFromN.start();
    }

    @Override
    public void onFlickrPublicJsonFeedFetchError(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToastLong(mActivity.getResources().getString
                        (R.string.feed_not_loaded), mActivity);
                Bundle gameFinishData = new Bundle();
                gameFinishData.putString(AppConstants.GAME_STATUS,
                        "Oops there was some error in fetching data. Press Restart to try again.");
                Intent gameFinishIntent = new Intent(PicScrambleGameActivity.this, GameFinishActivity.class);
                gameFinishIntent.putExtras(gameFinishData);
                PicScrambleGameActivity.this.startActivity(gameFinishIntent);

            }
        });
    }

    //back press do nothing
    @Override
    public void onBackPressed() {
    }
}
