package com.ece.handshake.model.db;

import android.provider.BaseColumns;

public class AccountsContract {
    public AccountsContract() {}

    public static abstract class AccountsEntry implements BaseColumns {
        public static final String TABLE_NAME = "connected_accounts";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PLATFORM_NAME = "platformName";
        public static final String COLUMN_LINK_URI = "profileLinkUri";
        public static final String COLUMN_PIC_URI = "profilePicUri";
        public static final String COLUMN_TOKEN = "accessToken";
    }
}