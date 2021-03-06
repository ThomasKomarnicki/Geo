package com.doglandia.unearthed.activity;

import android.accounts.Account;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.UserAuth;
import com.doglandia.unearthed.activity.locate.PlaceLocateDefaultActivity;
import com.doglandia.unearthed.activity.locate.PlaceLocateIntroduction;
import com.doglandia.unearthed.model.User;
import com.doglandia.unearthed.server.Server;
import com.doglandia.unearthed.service.AuthSlideShowService;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AuthActivity extends CalligraphyActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "AuthActivity";
    private static final String SERVER_CLIENT_ID = "92340928633-a2lv6k929j34994pjcfmpdm9a8kc9lme.apps.googleusercontent.com";
    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    private SignInButton googleSignIn;
    private Button continueWithoutSignIn;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(UserAuth.getAuthState(this) != UserAuth.AuthState.LOGGED_OUT){
            startPlaceLocateActivity();
            return;
        }

        setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.auth_activity);



        progressBar = (ProgressBar) findViewById(R.id.auth_activity_progress);

        googleSignIn = (SignInButton) findViewById(R.id.google_sign_in_button);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked the sign-in button, so begin the sign-in process and automatically
                // attempt to resolve any errors that occur.
                mShouldResolve = true;
                mGoogleApiClient.connect();

                // Show a message to the user that we are signing in.
//                mStatusTextView.setText(R.string.signing_in);
            }
        });
        continueWithoutSignIn = (Button) findViewById(R.id.continue_without_sign_in);
        continueWithoutSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noAuthSignIn();
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }

    protected void onStart() {
        super.onStart();
//        if(mGoogleApiClient!= null) {
//            mGoogleApiClient.connect();
//        }
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d(TAG, "onConnected:" + bundle);
        mShouldResolve = false;

        // Show the signed-in UI
        onGoogleSignedIn(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                    enableSignInButtons(false);
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
//                showErrorDialog(connectionResult);
                enableSignInButtons(true);
            }
        }else{
            enableSignInButtons(true);
        }

    }

    private void enableSignInButtons(boolean enable){
        googleSignIn.setEnabled(enable);
        continueWithoutSignIn.setEnabled(enable);
        if(enable){
            progressBar.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this, AuthSlideShowService.class);
        stopService(serviceIntent);
    }

    private void onGoogleSignedIn(Bundle bundle){
        new GetIdTokenTask().execute(new Void[]{});
    }

    public class GetIdTokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
            String scopes = "audience:server:client_id:"+SERVER_CLIENT_ID; // Not the app's client ID.
//            String scopes = "oauth2:https://www.googleapis.com/auth/plus.login";
//            String scopes = "oauth2:server:client_id:"+SERVER_CLIENT_ID+":api_scope:https://www.googleapis.com/auth/plus.login";
            try {
                return GoogleAuthUtil.getToken(getApplicationContext(), account,scopes);
            } catch (IOException e) {
                Log.e(TAG, "Error retrieving ID token.", e);
                return null;
            } catch (GoogleAuthException e) {
                Log.e(TAG, "Error retrieving ID token.", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "ID token: " + result);
            if (result != null) {
                Map<String,String> map = new HashMap<>();
                map.put("auth_token",result);
                Server.getInstance().googleAuth(map, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        UserAuth.googleSignIn(AuthActivity.this,user);
                        startPlaceLocateActivity();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                // Successfully retrieved ID Token
                // ...
            } else {
                // There was some error getting the ID Token
                // ...
            }
        }

    }

    private void startPlaceLocateActivity(){
        Class activityClass;
        if(UserAuth.isFirstRun(this)){
            activityClass = PlaceLocateIntroduction.class;
        }else{
            activityClass = PlaceLocateDefaultActivity.class;
        }
        Intent intent = new Intent(this,activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void noAuthSignIn(){
        enableSignInButtons(false);
//        String androidId = Settings.Secure.ANDROID_ID;
        String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

//        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
//        Account[] accounts = AccountManager.get(this).getAccounts();
//        for (Account account : accounts) {
//            if (emailPattern.matcher(account.name).matches()) {
//                Log.d(TAG,"possible email: "+account.name);
//                String possibleEmail = account.name;
//
//            }
//        }

        Map<String,String> map = new HashMap<>();
        map.put("other_identifier",androidId);

        Server.getInstance().noAuthUser(map, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                UserAuth.otherSignIn(AuthActivity.this,user);
                startPlaceLocateActivity();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
