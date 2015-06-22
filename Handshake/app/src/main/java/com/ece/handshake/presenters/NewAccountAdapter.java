package com.ece.handshake.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ece.handshake.helper.MediaPlatformHelper;
import com.ece.handshake.helper.SharedPreferencesManager;
import com.ece.handshake.R;
import com.ece.handshake.views.MainActivity;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.Arrays;

public class NewAccountAdapter extends RecyclerView.Adapter<NewAccountAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context mContext;

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

    public NewAccountAdapter(ArrayList<String> myDataset, Context c) {
        mDataset = myDataset;
        mContext = c;
    }

    @Override
    public NewAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_sm_account_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewAccountAdapter.ViewHolder holder, int position) {
        final String platform = mDataset.get(position);
        holder.mPlatformName.setText(platform);
        holder.mPlatformImage.setImageDrawable(MediaPlatformHelper.getAccountImageResource(platform));

        holder.mRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectAccount(platform);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void connectAccount(String platform) {
        Resources res = mContext.getResources();

        if (platform.equals(res.getString(R.string.platform_type_facebook))) {
            LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
            LoginManager.getInstance().logInWithReadPermissions((MainActivity) mContext, Arrays.asList("public_profile", "user_friends"));
            SharedPreferencesManager.saveFacebookAccountDetails(mContext, Profile.getCurrentProfile());
        }
    }
}
