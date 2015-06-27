package com.ece.handshake.events;

public class PresenterResumeEvent {
    private String mClassName;

    public PresenterResumeEvent(String className) {
        this.mClassName = className;
    }

    public String getClassName() {
        return mClassName;
    }
}
