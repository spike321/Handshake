package com.ece.handshake.events;


public class NewAccountEvent {
    private String platform;

    public NewAccountEvent(String platform) {
        this.platform = platform;
    }
}
