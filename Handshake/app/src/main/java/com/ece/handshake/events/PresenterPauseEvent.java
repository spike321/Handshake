package com.ece.handshake.events;

public class PresenterPauseEvent {
    private String mClassName;

    public PresenterPauseEvent(String className) {
        this.mClassName = className;
    }

    public String getClassName() {
        return mClassName;
    }
}
