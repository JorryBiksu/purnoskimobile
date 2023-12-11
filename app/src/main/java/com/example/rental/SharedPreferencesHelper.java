package com.example.rental;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String USER_INFO_PREF = "user_info";
    private static final String USER_ROLE_KEY = "user_role";

    public static void saveUserRole(Context context, String userRole) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ROLE_KEY, userRole);
        editor.apply();
    }

    public static String getUserRole(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_PREF, Context.MODE_PRIVATE);
        return preferences.getString(USER_ROLE_KEY, "");
    }
}
