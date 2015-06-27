package com.ece.handshake.presenters;


public interface IProfilesPresenter {
    //TODO: Need to add parameter for different profiles
    void enablePlatform(String platformName, int rowPosition);

    void disablePlatform(String platformName, int rowPosition);

}
