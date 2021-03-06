package com.ece.handshake.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ece.handshake.model.db.AccountsContract.AccountsEntry;

public class AccountsDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Accounts.db";

    private static final String TEXT_TYPE = " text";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AccountsEntry.TABLE_NAME + " (" +
                    AccountsEntry._ID + " INTEGER PRIMARY KEY autoincrement," +
                    AccountsEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    AccountsEntry.COLUMN_PLATFORM_NAME + TEXT_TYPE + COMMA_SEP +
                    AccountsEntry.COLUMN_LINK_URI + TEXT_TYPE + COMMA_SEP +
                    AccountsEntry.COLUMN_PIC_URI + TEXT_TYPE + COMMA_SEP +
                    AccountsEntry.COLUMN_TOKEN + TEXT_TYPE+
            " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AccountsEntry.TABLE_NAME;

    public AccountsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
