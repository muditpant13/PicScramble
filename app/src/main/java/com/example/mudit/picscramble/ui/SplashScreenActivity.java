package com.example.mudit.picscramble.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mudit.picscramble.R;
import com.example.mudit.picscramble.utils.AppConstants;
import com.example.mudit.picscramble.utils.AppUtils;

/**
 * Created by mudit on 2/8/16.
 */
public class SplashScreenActivity extends Activity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Context mContext;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_activity);

        mContext = this.getBaseContext();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //check for internet
                if (AppUtils.isNetworkAvailable(mContext)) {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, PicScrambleGameActivity.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                } else {
                    Bundle gameFinishData = new Bundle();
                    gameFinishData.putString(AppConstants.GAME_STATUS,
                            "This app needs an internet connection..");
                    Intent gameFinishIntent = new Intent(SplashScreenActivity.this, GameFinishActivity.class);
                    gameFinishIntent.putExtras(gameFinishData);
                    SplashScreenActivity.this.startActivity(gameFinishIntent);
                }
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    //disable back press
    @Override
    public void onBackPressed() {
    }

}
