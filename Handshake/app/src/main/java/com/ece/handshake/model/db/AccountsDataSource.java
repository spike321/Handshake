package com.ece.handshake.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ece.handshake.model.data.SMAccount;
import com.ece.handshake.model.db.AccountsContract.AccountsEntry;

import java.util.ArrayList;
import java.util.List;

public class AccountsDataSource {
    private SQLiteDatabase db;
    private AccountsDbHelper dbHelper;

    public AccountsDataSource(Context context) {
        dbHelper = new AccountsDbHelper(context);
    }

    public long insertConnectedAccount(SMAccount account) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AccountsEntry.COLUMN_NAME, account.getName());
        values.put(AccountsEntry.COLUMN_PLATFORM_NAME, account.getPlatformName());
        values.put(AccountsEntry.COLUMN_LINK_URI, account.getLinkUri().toString());
        values.put(AccountsEntry.COLUMN_PIC_URI, account.getProfilePicUri().toString());
        values.put(AccountsEntry.COLUMN_TOKEN, account.getAccessToken());

        long newRowId = db.insertOrThrow(AccountsDbHelper.DATABASE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public List<SMAccount> getAccounts() {
        db = dbHelper.getReadableDatabase();

        String[] projection = {AccountsEntry.COLUMN_NAME, AccountsEntry.COLUMN_LINK_URI,
                AccountsEntry.COLUMN_PIC_URI, AccountsEntry.COLUMN_TOKEN};

        String sortOrder = AccountsEntry.COLUMN_PLATFORM_NAME + " DESC";

        Cursor c = db.query(
                AccountsEntry.TABLE_NAME,  // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );
        List<SMAccount> list = new ArrayList<>();

        while(c.moveToNext()) {
            SMAccount account = new SMAccount();
            account.setName(c.getString(c.getColumnIndex(AccountsEntry.COLUMN_NAME)));
            account.setPlatformName(c.getString(c.getColumnIndex(AccountsEntry.COLUMN_PLATFORM_NAME)));
            account.setLinkUri(Uri.parse(c.getString(c.getColumnIndex(AccountsEntry.COLUMN_LINK_URI))));
            account.setProfilePicUri(Uri.parse(c.getString(c.getColumnIndex(AccountsEntry.COLUMN_PIC_URI))));
            account.setAccessToken(c.getString(c.getColumnIndex(AccountsEntry.COLUMN_TOKEN)));

            list.add(account);
        }
        c.close();
        db.close();
        return list;
    }
}
