package com.ece.handshake;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MediaPlatformHelper {

    private static HashMap<String, Drawable> platformImgMap = new HashMap<>();

    public static void initializePlatformImgMapping(Context context) {
        final Resources res = context.getResources();
        platformImgMap.put(context.getString(R.string.facebook), res.getDrawable(R.drawable.facebook_icon));
        platformImgMap.put(context.getString(R.string.twitter), res.getDrawable(R.drawable.twitter_icon));
        platformImgMap.put(context.getString(R.string.linkedIn), res.getDrawable(R.drawable.linkedin_icon));
        platformImgMap.put(context.getString(R.string.instagram), res.getDrawable(R.drawable.instagram_icon));
        platformImgMap.put(context.getString(R.string.google_plus), res.getDrawable(R.drawable.google_plus_icon));
        platformImgMap.put(context.getString(R.string.pinterest), res.getDrawable(R.drawable.pinterest_icon));

    }

    public static ArrayList<String> getSupportedPlatforms() {
        ArrayList<String> platforms = new ArrayList<>();

        for (String platform : platformImgMap.keySet()) {
            platforms.add(platform);
        }

        Collections.sort(platforms);
        return platforms;
    }
    public static Drawable getAccountImageResource(String platform) {
        return platformImgMap.get(platform);
    }
}
