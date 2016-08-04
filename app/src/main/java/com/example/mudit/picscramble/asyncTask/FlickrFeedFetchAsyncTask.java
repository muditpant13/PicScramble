package com.example.mudit.picscramble.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mudit.picscramble.utils.AppConstants;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudit on 31/7/16.
 */
public class FlickrFeedFetchAsyncTask extends AsyncTask<String, String, String> {

    InputStream inputStream = null;


    private static final String TAG = FlickrFeedFetchAsyncTask.class.getName();

    @Override
    protected String doInBackground(String... params) {

        String url_select = AppConstants.FLICKR_BASE_URL + "format=" + AppConstants.FLICKR_FEED_FORMAT  + "&page=2";

        String result = "";
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(url_select);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream =new BufferedInputStream(urlConnection.getInputStream());

        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncoding", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            boolean isFirstLine = true;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }

        urlConnection.disconnect();

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}

