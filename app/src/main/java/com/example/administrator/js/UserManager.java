package com.example.administrator.js;

import android.content.SharedPreferences;

import com.appbaselib.constant.Constants;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.me.model.User;

/**
 * Created by tangming on 2018/4/19.
 */

public class UserManager implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onSharedPreferenceChanged(SharedPreferences mSharedPreferences, String mS) {
        reload();
    }

    private void reload() {
        mUser = PreferenceUtils.getObjectFromGson(App.mInstance, Constants.PRE_USER, User.class);
    }

    private User mUser;

    private static class SingletonHolder {
        private static final UserManager instance = new UserManager();
    }

    private UserManager() {
    }

    public static final UserManager getInsatance() {
        return SingletonHolder.instance;
    }

    public User getUser() {
        if (mUser == null)

            return PreferenceUtils.getObjectFromGson(App.mInstance, Constants.PRE_USER, User.class);
        else
            return mUser;
    }
}
