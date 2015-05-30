package com.ece.handshake;


public class SocialMediaAccount {
    private int mAccountPlatformImgId;
    private String mAccountPlatformName;

    private String mUserId;

    public SocialMediaAccount(int platformImgId, String platformName, String userId) {
        mAccountPlatformImgId = platformImgId;
        mAccountPlatformName = platformName;
        mUserId = userId;
    }
}
