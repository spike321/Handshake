package com.ece.handshake.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.ece.handshake.R;
import com.ece.handshake.views.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class NewAccountPresenter implements INewAccountPresenter {
    private Context mContext;
    private Resources res;

    public NewAccountPresenter(Context context) {
        this.mContext = context;
        this.res = context.getResources();
    }

    @Override
    public void connectAccount(String platform) {
        if (platform.equals(res.getString(R.string.platform_type_facebook))) {
            connectFacebookAccount();

        }
    }

    private void connectFacebookAccount() {
        LoginManager.getInstance().registerCallback(CallbackManager.Factory.create(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(mContext, "Succesfully Facebook Login", Toast.LENGTH_LONG).show();
                Profile prof = Profile.getCurrentProfile();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mContext, "Cancelled Facebook Login", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(mContext, "Failed Facebook Login: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
        LoginManager.getInstance().logInWithReadPermissions((MainActivity) mContext, Arrays.asList("public_profile", "user_friends"));
    }
}
