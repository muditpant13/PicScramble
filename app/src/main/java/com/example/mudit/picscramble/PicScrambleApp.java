package com.example.mudit.picscramble;

import android.app.Application;

import com.example.mudit.picscramble.di.components.AppComponent;
import com.example.mudit.picscramble.di.components.DaggerAppComponent;
import com.example.mudit.picscramble.di.modules.AppModule;
import com.example.mudit.picscramble.di.modules.RetrofitModule;
import com.example.mudit.picscramble.utils.AppConstants;

/**
 * Created by mudit on 28/7/16.
 */
public class PicScrambleApp extends Application {

    private AppComponent mAppComponent = createAppComponent();

    private final String TAG = PicScrambleApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        getAppComponent().inject(this);

    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule())
                .appModule(new AppModule(this))
                .build();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
