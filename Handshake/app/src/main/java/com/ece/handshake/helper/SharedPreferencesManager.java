package com.ece.handshake.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.Profile;

import java.util.Map;

public class SharedPreferencesManager {
    private final static String BASIC_USER_INFO = "BasicUserInfoFile";

    public final static String GPLUS_FIRST_NAME = "GPlusFirstName";
    public final static String GPLUS_LAST_NAME = "GPlusLastName";
    public final static String GPLUS_PHOTO = "GPlusPhoto";
    public final static String GPLUS_EMAIL = "GPlusEmail";
    public final static String GPLUS_URL = "GPlusURL";

    private final static String FB_INFO = "FBAccountDetails";

    private final static String NAME = "NAME";
    private final static String PROFILE_URI = "ProfileUrl";
    private final static String PROFILE_IMG_URI = "ProfileImgUri";

    private final static String GENERAL_USER_INFO = "GeneralUserInfo";
    private final static String IS_LOGGED_IN = "IsUserLoggedIn";

    private final static String FACEBOOK_CONNNECTED = "isFacebookConnected";
    private final static String TWITTER_CONNNECTED = "isTwitterConnected";
    private final static String LINKEDIN_CONNNECTED = "isLinkedinConnected";
    private final static String INSTAGRAM_CONNNECTED = "isInstagramConnected";
    private final static String PINTEREST_CONNNECTED = "isPinterestConnected";
    private final static String GOOGLEPLUS_CONNNECTED = "isGooglePlusConnected";


    public static void saveBasicInfo(Context context, Map<String, String> values) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(BASIC_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(GPLUS_FIRST_NAME, values.get(GPLUS_FIRST_NAME));
        editor.putString(GPLUS_LAST_NAME, values.get(GPLUS_LAST_NAME));
        editor.putString(GPLUS_EMAIL, values.get(GPLUS_EMAIL));
        editor.putString(GPLUS_URL, values.get(GPLUS_URL));
        editor.commit();
    }
    
    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(GENERAL_USER_INFO, Context.MODE_PRIVATE);
        return sharedPrefs.getBoolean(IS_LOGGED_IN, false);
    }

    public static void setIsUserLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(GENERAL_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public static void saveFacebookAccountDetails(Context context, Profile profile) {
        if (profile == null) {
            return;
        }

        setUserHasConnectedPlatform(context, FACEBOOK_CONNNECTED);
        SharedPreferences sharedPrefs = context.getSharedPreferences(FB_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(NAME, profile.getName());
        editor.putString(PROFILE_URI, profile.getLinkUri().toString());
        editor.putString(PROFILE_IMG_URI, profile.getProfilePictureUri(40, 40).toString());
        editor.commit();
    }

    private static void setUserHasConnectedPlatform(Context context, String platform) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(GENERAL_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(platform, true);
        editor.commit();
    }
}
