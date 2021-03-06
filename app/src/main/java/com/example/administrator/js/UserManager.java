package com.example.administrator.js;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.appbaselib.constant.Constants;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.me.model.User;

import io.rong.imkit.RongIM;

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
        PreferenceManager.getDefaultSharedPreferences(App.mInstance).registerOnSharedPreferenceChangeListener(this);
    }

    public static final UserManager getInsatance() {
        return SingletonHolder.instance;
    }

    public User getUser() {
        if (mUser == null) {
            User mUser = PreferenceUtils.getObjectFromGson(App.mInstance, Constants.PRE_USER, User.class);
            this.mUser = mUser;
            return mUser;
        } else {
            return mUser;
        }
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    // 角色0教练1学员
    public String getRole() {
        if (mUser == null) {
            User mUser = PreferenceUtils.getObjectFromGson(App.mInstance, Constants.PRE_USER, User.class);
            this.mUser = mUser;
        }
        return mUser.role;
    }

    public void logout() {
        RongIM.getInstance().logout();
        PreferenceUtils.clearDefaultPreference(App.mInstance);
        UserManager.getInsatance().setUser(null);
    }
}
