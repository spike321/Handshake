package com.ece.handshake.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ece.handshake.R;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

import java.util.Arrays;

public class HomeFragment extends Fragment{

    public HomeFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((MainActivity)getActivity()).mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_handshake, container, false);
        Button tempFBLogin = (Button) rootView.findViewById(R.id.fb_login);
        final Fragment frag = this;
        tempFBLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager.getInstance().logOut();
                LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
                LoginManager.getInstance().logInWithReadPermissions(frag, Arrays.asList("public_profile", "user_friends"));
                Profile prof = Profile.getCurrentProfile();
                AccessToken token = AccessToken.getCurrentAccessToken();
            }
        });

        Button nfcButton = (Button) rootView.findViewById(R.id.connect_button);
        nfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Profile prof = Profile.getCurrentProfile();
        if (prof != null) {
            TextView userText = (TextView) rootView.findViewById(R.id.current_fb_user);
            userText.setText(prof.getFirstName() + prof.getLastName());
        }

        return rootView;
    }
}
