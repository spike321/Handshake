package com.ece.handshake.presenters;

import android.content.Context;
import android.content.res.Resources;

import com.ece.handshake.R;
import com.ece.handshake.views.MainActivity;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

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
        if (platform.equals(res.getString(R.string.platform_name_facebook))) {

            connectFacebookAccount();

        }
    }

    private void connectFacebookAccount() {
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
        LoginManager.getInstance().logInWithReadPermissions((MainActivity) mContext, Arrays.asList("public_profile", "user_friends"));
    }
}
