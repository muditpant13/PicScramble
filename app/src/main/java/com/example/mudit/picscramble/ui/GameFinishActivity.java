package com.example.mudit.picscramble.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mudit.picscramble.R;
import com.example.mudit.picscramble.utils.AppConstants;

/**
 * Created by mudit on 2/8/16.
 */
public class GameFinishActivity extends Activity implements View.OnClickListener{

    private Button mBtRestartGameButton;
    private String mGameFinishStatusString;
    private TextView mTvGameFinishText;

   /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.game_finish_activity);

        //get extra data that was sent to this activity
        Bundle extras = getIntent().getExtras();
        mGameFinishStatusString = extras.getString(AppConstants.GAME_STATUS);

        mBtRestartGameButton = (Button) findViewById(R.id.btRestartGame);
        mTvGameFinishText = (TextView) findViewById(R.id.tvGameFinishText);

        mBtRestartGameButton.setOnClickListener(this);
        mTvGameFinishText.setText(mGameFinishStatusString);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btRestartGame:
                Intent splashIntent = new Intent(GameFinishActivity.this,SplashScreenActivity.class);
                GameFinishActivity.this.startActivity(splashIntent);
                GameFinishActivity.this.finish();
        }

    }

    @Override
    public void onBackPressed() {
    }
}
