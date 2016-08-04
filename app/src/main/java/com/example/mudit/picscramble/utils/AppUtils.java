package com.example.mudit.picscramble.utils;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by mudit on 1/8/16.
 */
//This class handles general utility functions that can be accesses by the entire app
public class AppUtils {

    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context _context) {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) {
            // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    //for taking 9 random positions out of 20 fetched from flickr feed
    public static Set<Integer> getKRandomIntegersFromN (int max, int numbersNeeded){
        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < numbersNeeded)
        {
            Integer next = rng.nextInt(max) + 1;
            // As we're adding to a set, this will automatically do a containment check
            generated.add(next);
        }
        return generated;
    }

    public static List<Integer> getKRandomIntegerList(int max, int numbersNeeded){
        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();
        List<Integer> generatedList =  new ArrayList<>();
        while (generated.size() < numbersNeeded)
        {
            Integer next = rng.nextInt(max) + 1;
            // As we're adding to a set, this will automatically do a containment check

            if(!generated.contains(next)){
                generatedList.add(next);
            }

            generated.add(next);

        }
        return generatedList;
    }

    public static void showToastShort(String msg, Context ctx) {
        try {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToastLong(String msg, Context ctx) {
        try {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
