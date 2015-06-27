package com.ece.handshake.presenters;

import android.content.Context;

import com.ece.handshake.views.IProfilesView;

public class ProfilesPresenter implements  IProfilesPresenter{
    private IProfilesView mView;

    public ProfilesPresenter(Context context, IProfilesView profilesView) {
        this.mView = profilesView;
    }

    @Override
    public void enablePlatform(String platformName, int rowPosition) {
        //TODO: Save settings to sharedpreferences under profile name
        //mView.highlightPlatformRowGreen();
    }

    @Override
    public void disablePlatform(String platformName, int rowPosition) {
        //mView.highlightPlatformRowRed();
    }
}
