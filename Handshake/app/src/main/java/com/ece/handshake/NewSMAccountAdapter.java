package com.ece.handshake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;

public class NewSMAccountAdapter extends RecyclerView.Adapter<NewSMAccountAdapter.ViewHolder> {
    private String[] mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPlatformImage;
        private TextView mPlatformName;
        private RelativeLayout mRow;
        private Button mLoginButton;

        public ViewHolder(View v) {
            super(v);
            mPlatformImage = (ImageView) v.findViewById(R.id.platform_image);
            mPlatformName = (TextView) v.findViewById(R.id.platform_name);
            mRow = (RelativeLayout) v.findViewById(R.id.row);
           // mLoginButton = (LoginButton) v.findViewById(R.id.fb_login);
        }
    }

    public NewSMAccountAdapter(String[] myDataset, Context c) {
        mDataset = myDataset;
        mContext = c;
    }

    @Override
    public NewSMAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_sm_account_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        FacebookSdk.sdkInitialize(mContext);

        return vh;
    }

    @Override
    public void onBindViewHolder(NewSMAccountAdapter.ViewHolder holder, int position) {
        final String accountType = mDataset[position];
        holder.mPlatformName.setText(accountType);
        holder.mRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(accountType + "^^^^^^^^^^^^^^^^^^^^");
            }
        });
        CallbackManager callbackManager = CallbackManager.Factory.create();

        LoginButton fbLogin = (LoginButton) holder.mLoginButton;
        fbLogin.setFragment(((HandshakeActivity)mContext).getSupportFragmentManager().findFragmentByTag("add_account_fragment"));
        fbLogin.setReadPermissions("public_profile");
        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("Successfull login");
            }

            @Override
            public void onCancel() {
                System.out.println("Cancel");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Failed Login");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
