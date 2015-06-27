package com.ece.handshake.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ece.handshake.helper.MediaPlatformHelper;
import com.ece.handshake.R;
import com.ece.handshake.presenters.NewAccountPresenter;

import java.util.ArrayList;

public class NewAccountAdapter extends RecyclerView.Adapter<NewAccountAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private NewAccountPresenter presenter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPlatformImage;
        private TextView mPlatformName;
        private RelativeLayout mRow;

        public ViewHolder(View v) {
            super(v);
            mPlatformImage = (ImageView) v.findViewById(R.id.platform_image);
            mPlatformName = (TextView) v.findViewById(R.id.platform_name);
            mRow = (RelativeLayout) v.findViewById(R.id.row);
        }
    }

    public NewAccountAdapter(ArrayList<String> myDataset, Context context) {
        mDataset = myDataset;
        presenter = new NewAccountPresenter(context);
    }

    @Override
    public NewAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_sm_account_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewAccountAdapter.ViewHolder holder, int position) {
        final String platform = mDataset.get(position);
        holder.mPlatformName.setText(platform);
        holder.mPlatformImage.setImageDrawable(MediaPlatformHelper.getAccountImageResource(platform));

        holder.mRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.connectAccount(platform);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
