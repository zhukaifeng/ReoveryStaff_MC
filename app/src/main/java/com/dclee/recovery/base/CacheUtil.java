package com.dclee.recovery.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.dclee.recovery.pojo.LoginResult;

public class CacheUtil {
    private static SharedPreferences cachePreference;
    public static final String KEY_ACCESS_TOKEN = "key_access_token";
    public static final String KEY_TOKEN_TYPE = "key_token_type";

    public static void init(Context context) {
        cachePreference = context.getSharedPreferences("RecoveryStaff", Context.MODE_PRIVATE);
    }

    public static void set(String key, String value) {
        cachePreference.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return cachePreference.getString(key, null);
    }

    public static void login(LoginResult loginInfo) {
        set(KEY_ACCESS_TOKEN, loginInfo.getAccess_token());
        set(KEY_TOKEN_TYPE, loginInfo.getToken_type());
    }

    public static String getAccessToken() {
        return getString(KEY_ACCESS_TOKEN);
    }

    public static String getTokenType() {
        return getString(KEY_TOKEN_TYPE);
    }

    public static void clearTokenType() {
        set(KEY_ACCESS_TOKEN, "");
        set(KEY_TOKEN_TYPE, "");
    }

}
