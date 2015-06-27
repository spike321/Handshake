package com.ece.handshake.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.ece.handshake.R;
import com.ece.handshake.events.NewAccountEvent;
import com.ece.handshake.events.PresenterPauseEvent;
import com.ece.handshake.events.PresenterResumeEvent;
import com.ece.handshake.events.TwitterLoginEvent;
import com.ece.handshake.model.data.SMAccount;
import com.ece.handshake.model.db.AccountsDataSource;
import com.ece.handshake.views.MainActivity;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.Arrays;

import de.greenrobot.event.EventBus;

public class NewAccountPresenter implements INewAccountPresenter {
    private final static String CLASS_NAME = NewAccountPresenter.class.getClass().getSimpleName();

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
        } else if (platform.equals(res.getString(R.string.platform_name_twitter))) {
            connectTwitterAccount();
        }
    }

    @Override
    public void resume() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void pause() {
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(NewAccountEvent event) {
        AccountsDataSource source = new AccountsDataSource(mContext);
        source.insertConnectedAccount(event.getAccount());

    }

    public void onEvent(PresenterResumeEvent event) {
        if (event.getClassName().equals(CLASS_NAME))
            resume();
    }

    public void onEvent(PresenterPauseEvent event) {
        if (event.getClassName().equals(CLASS_NAME))
            pause();
    }

    private void connectFacebookAccount() {
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
        LoginManager.getInstance().logInWithReadPermissions((MainActivity) mContext, Arrays.asList("public_profile", "user_friends"));
    }

    private void connectTwitterAccount() {
        EventBus.getDefault().post(new TwitterLoginEvent());
    }

}
