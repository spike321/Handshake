package com.ece.handshake;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {
    private String[] mDataset;

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        public AccountViewHolder(View v) {
            super(v);
        }
    }

    public AccountsAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AccountsAdapter.AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_row, parent, false);
        AccountViewHolder vh = new AccountViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AccountViewHolder accountViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
