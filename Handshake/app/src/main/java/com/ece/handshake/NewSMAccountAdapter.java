package com.ece.handshake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.Arrays;

public class NewSMAccountAdapter extends RecyclerView.Adapter<NewSMAccountAdapter.ViewHolder> {
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

    public NewSMAccountAdapter(ArrayList<String> myDataset, Context c) {
        mDataset = myDataset;
        mContext = c;
    }

    @Override
    public NewSMAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_sm_account_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewSMAccountAdapter.ViewHolder holder, int position) {
        final String accountType = mDataset.get(position);
        holder.mPlatformName.setText(accountType);
        holder.mPlatformImage.setImageDrawable(MediaPlatformHelper.getAccountImageResource(accountType));

        holder.mRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(accountType + "^^^^^^^^^^^^^^^^^^^^");

                LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
                LoginManager.getInstance().logInWithReadPermissions((MainActivity)mContext, Arrays.asList("public_profile", "user_friends"));
                Profile prof = Profile.getCurrentProfile();
                AccessToken token = AccessToken.getCurrentAccessToken();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
