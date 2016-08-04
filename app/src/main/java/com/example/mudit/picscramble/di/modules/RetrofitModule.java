package com.example.mudit.picscramble.di.modules;

import android.text.TextUtils;
import android.util.Log;

import com.example.mudit.picscramble.rest.RetrofitClient;
import com.example.mudit.picscramble.utils.AppConstants;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by mudit on 31/7/16.
 */
@Module
public class RetrofitModule {


    private final String TAG = RetrofitModule.class.getName();

    public RetrofitModule() {

    }

    @Singleton
    @Provides
    RetrofitClient provideRetrofitClient(Retrofit retrofit) {
        RetrofitClient retrofitClient;
        retrofitClient = retrofit.create(RetrofitClient.class);
        return retrofitClient;
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstants.FLICKR_BASE_URL)
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        //todo replace literals with constants
        client.setReadTimeout(AppConstants.HTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS);
        client.setWriteTimeout(AppConstants.HTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        client.setConnectTimeout(AppConstants.HTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                Log.d(TAG,"hostname is: " + hostname);
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                Log.d(TAG,"hostname verified: " + hv.verify("localhost.opinioapp.com", session) );
                return hv.verify("localhost.opinioapp.com", session);
            }
        });
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                return chain.proceed(original);
            }
        });

        return client;
    }

}
