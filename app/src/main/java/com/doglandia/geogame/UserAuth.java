package com.doglandia.geogame;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.doglandia.geogame.model.User;
import com.google.gson.Gson;

/**
 * Created by Thomas on 7/18/2015.
 */

/* Manages user authentication state, includes intermediate states where auth may have failed */
public class UserAuth {

    public static final String AUTH_TYPE = "auth_type";
    public static final String GOOGLE_AUTH_TOKEN = "google_auth_token";
    public static final String GEO_SERVER_USER_ID = "user_id";
    public static final String AUTH_STATE = "auth_state";
    public static final String GEO_SERVER_USER = "user";

    private static User authUser;

    public enum AuthTypes{
        GOOGLE, OTHER
    }

    public enum AuthState{
        LOGGED_OUT, OTHER_SIGNED_IN, GOOGLE_SIGNED_IN,
        OTHER_CREATE_USER_FAILED, GOOGLE_CREATE_USER_FAILED
    }

    public static int getAuthUserId(){
        return authUser.getId();
    }

    public static int getAuthUserCurrentLocation(){
        return authUser.getCurrentLocationId();
    }

    public static void spinUp(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        authUser = gson.fromJson(sharedPreferences.getString(GEO_SERVER_USER,""),User.class);
    }

    /* called when geo server responds with google auth result*/
    public static void googleSignIn(Context context, User user){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GEO_SERVER_USER_ID,user.getId());
        editor.putString(AUTH_TYPE,AuthState.GOOGLE_SIGNED_IN.name());
        editor.putString(GOOGLE_AUTH_TOKEN,user.getGoogleAuthId());
        editor.putString(GEO_SERVER_USER,gson.toJson(user));
        editor.commit();
    }

    public static void otherSignIn(Context context, User user){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GEO_SERVER_USER_ID, user.getId());
        editor.putString(AUTH_TYPE,AuthState.OTHER_SIGNED_IN.name());
        editor.putString(GEO_SERVER_USER,gson.toJson(user));
        editor.commit();
    }

    public static AuthState getAuthState(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return AuthState.valueOf(sharedPreferences.getString(AUTH_STATE, AuthState.LOGGED_OUT.name()));
    }





}
