package com.doglandia.geogame;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.User;
import com.doglandia.geogame.server.typeAdapter.PlaceTypeAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    private static final String IS_FIRST_RUN = "is_first_run";

    private static final String TAG = "UserAuth";

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

    public static String getAuthUserToken(){
        return authUser.getAuthToken();
    }

    public static int getAuthUserCurrentLocation(){
//        Log.d(TAG,"getting current location id = "+authUser.getCurrentLocationId());
        return authUser.getCurrentLocationId();
    }

    public static void spinUp(Context context){
        Gson gson = getGson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        authUser = gson.fromJson(sharedPreferences.getString(GEO_SERVER_USER,""),User.class);
        Log.d(TAG,"creating auth user:");
        Log.d(TAG,sharedPreferences.getString(GEO_SERVER_USER,""));

    }

    /* called when geo server responds with google auth result*/
    public static void googleSignIn(Context context, User user){
        authUser = user;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GEO_SERVER_USER_ID,user.getId());
        editor.putString(AUTH_TYPE,AuthTypes.GOOGLE.name());
        editor.putString(GOOGLE_AUTH_TOKEN,user.getGoogleAuthId());
        editor.putString(AUTH_STATE,AuthState.GOOGLE_SIGNED_IN.name());
        editor.commit();

        saveAuthUser(context);
    }

    public static void otherSignIn(Context context, User user){
        authUser = user;
        Gson gson = getGson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GEO_SERVER_USER_ID, user.getId());
        editor.putString(AUTH_TYPE,AuthTypes.OTHER.name());
        editor.putString(GEO_SERVER_USER,gson.toJson(user));
        editor.putString(AUTH_STATE,AuthState.OTHER_SIGNED_IN.name());
        editor.commit();

        saveAuthUser(context);
    }

    public static AuthState getAuthState(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return AuthState.valueOf(sharedPreferences.getString(AUTH_STATE, AuthState.LOGGED_OUT.name()));
    }

    public static void setCurrentLocation(int currentLocationId, Context context){
        authUser.setCurrentLocationId(currentLocationId);
        saveAuthUser(context);
    }

    private static void saveAuthUser(Context context){
        Gson gson = getGson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GEO_SERVER_USER, gson.toJson(authUser));
        editor.commit();
    }

    private static Gson getGson(){
        return new GsonBuilder()
                .registerTypeAdapter(Place.class,new PlaceTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public static boolean isFirstRun(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(IS_FIRST_RUN,false) != true;
    }

    public static void finishedFirstRun(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_RUN,true);
    }





}
