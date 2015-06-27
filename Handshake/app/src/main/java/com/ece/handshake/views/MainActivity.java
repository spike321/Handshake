package com.ece.handshake.views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.ece.handshake.events.NewAccountEvent;
import com.ece.handshake.events.TwitterLoginEvent;
import com.ece.handshake.helper.MediaPlatformHelper;
import com.ece.handshake.R;
import com.ece.handshake.helper.SharedPreferencesManager;
import com.ece.handshake.model.data.SMAccount;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.greenrobot.event.EventBus;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NfcAdapter.CreateNdefMessageCallback{

    public CallbackManager mCallbackManager;
    private NfcAdapter mNfcAdapter;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private TwitterAuthConfig mAuthConfig;
    private void initPlatforms() {
        FacebookSdk.sdkInitialize(this);

        mAuthConfig = new TwitterAuthConfig("SScUHc4jfNsporiWTQcKMerBx", "acMW4LyCZpcL0AWGmgfu1MhYkVMQM2ETn7KCeuzKygD2JC1DIi");
        Fabric.with(this, new Twitter(mAuthConfig));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlatformHelper.initializePlatformImgMapping(this);
        checkIsLoggedIn();
        initPlatforms();
        initCallbacks();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);

        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        /************ NFC Setup ****************/
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        mNfcAdapter.setNdefPushMessageCallback(this, this);
    }

    private void initCallbacks() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                        Profile.setCurrentProfile(newProfile);
                        Toast.makeText(getApplicationContext(), "Succesfull Facebook Login", Toast.LENGTH_LONG).show();
                        Profile profile = Profile.getCurrentProfile();
                        SMAccount account = new SMAccount(profile.getName(), getString(R.string.platform_name_facebook), profile.getLinkUri(), profile.getProfilePictureUri(64, 64), AccessToken.getCurrentAccessToken().getToken());
                        EventBus.getDefault().post(new NewAccountEvent(account));
                        this.stopTracking();
                    }
                };
                mProfileTracker.startTracking();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelled Facebook Login", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Failed Facebook Login: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
//64206
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO:Replace hardcoded requestcodes
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE == requestCode) {
            mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }
    }

    TwitterLoginButton mTwitterLoginButton;
    public void onEvent(TwitterLoginEvent event) {
        mTwitterLoginButton = new TwitterLoginButton(this);
        mTwitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(getApplicationContext(), "Successfully Twitter login", Toast.LENGTH_LONG).show();
                
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(getApplicationContext(), "Failed Twitter login", Toast.LENGTH_LONG).show();

            }
        });
        mTwitterLoginButton.performClick();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String text = ("Zeki Sherif");
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMimeRecord("application/vnd.com.ece.handshake.beam", text.getBytes())
                        /**
                         * The Android Application Record (AAR) is commented out. When a device
                         * receives a push with an AAR in it, the application specified in the AAR
                         * is guaranteed to run. The AAR overrides the tag dispatch system.
                         * You can add it back in to guarantee that this
                         * activity starts when receiving a beamed message. For now, this code
                         * uses the tag dispatch system.
                         */
                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
                });
        return msg;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());

            Intent i = new Intent();
            i.setAction("do_nothing");
            setIntent(i);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onResume();
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        System.out.println(new String(msg.getRecords()[0].getPayload()));

        Intent newContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        newContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        newContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, new String(msg.getRecords()[0].getPayload()));
        startActivity(newContactIntent);
    }

    /**
     * Creates a custom MIME type encapsulated in an NDEF record
     *
     * @param mimeType
     */
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.drawer_item_connected_accounts:
                fragmentManager.beginTransaction().replace(R.id.container, new AccountsFragment()).commit();
                break;
            case R.id.drawer_item_connection_profiles:
                fragmentManager.beginTransaction().replace(R.id.container, new ProfilesFragment()).commit();
                break;
            case R.id.drawer_item_settings:
                //TODO: Make Settings page
                break;
            case R.id.drawer_item_logout:
                //TODO: Logout of gplus and take user to login page
                break;
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/*
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.handshake, menu);
            restoreActionBar();
            return true;
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }


    private void checkIsLoggedIn() {
        if (!SharedPreferencesManager.isUserLoggedIn(this)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    private void facebookHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ece.handshake",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
    }

}
