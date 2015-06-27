package com.ece.handshake.model.data;

import android.net.Uri;

public class SMAccount {
    private String mName;
    private String mPlatformName;
    private Uri mLinkUri;
    private Uri mProfilePicUri;
    private String mAccessToken;

    public SMAccount() {}

    public SMAccount(String name, String platformName, Uri linkUri, Uri profilePictureUri, String accessToken) {
        mName = name;
        mPlatformName = platformName;
        mLinkUri = linkUri;
        mProfilePicUri = profilePictureUri;
        mAccessToken = accessToken;
    }

    public String getName() {
        return mName;
    }

    public String getPlatformName() {
        return mPlatformName;
    }

    public Uri getLinkUri() {
        return mLinkUri;
    }

    public Uri getProfilePicUri() {
        return mProfilePicUri;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setPlatformName(String mPlatformName) {
        this.mPlatformName = mPlatformName;
    }

    public void setLinkUri(Uri mLinkUri) {
        this.mLinkUri = mLinkUri;
    }

    public void setProfilePicUri(Uri mProfilePicUri) {
        this.mProfilePicUri = mProfilePicUri;
    }

    public void setAccessToken(String mAccessToken) {
        this.mAccessToken = mAccessToken;
    }
}
