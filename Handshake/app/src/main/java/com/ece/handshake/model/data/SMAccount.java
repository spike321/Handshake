package com.ece.handshake.model.data;


public class SMAccount {
    private int mPlatformImgId;
    private String mPlatformName;
    private String mUserId;

    public SMAccount(int platformImgId, String platformName, String userId) {
        mPlatformImgId = platformImgId;
        mPlatformName = platformName;
        mUserId = userId;
    }

    public int getPlatformImgId() {
        return mPlatformImgId;
    }

    public void setPlatformImgId(int mAccountPlatformImgId) {
        this.mPlatformImgId = mAccountPlatformImgId;
    }

    public String getPlatformName() {
        return mPlatformName;
    }

    public void setPlatformName(String mAccountPlatformName) {
        this.mPlatformName = mAccountPlatformName;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}
