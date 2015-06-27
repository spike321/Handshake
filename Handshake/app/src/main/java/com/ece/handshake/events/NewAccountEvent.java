package com.ece.handshake.events;

import com.ece.handshake.model.data.SMAccount;

public class NewAccountEvent {
    private final SMAccount mAccount;

    public NewAccountEvent(SMAccount account) {
        this.mAccount = account;
    }

    public SMAccount getAccount() {
        return mAccount;
    }
}
