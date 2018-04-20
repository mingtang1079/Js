package com.example.administrator.js.me.presenter;


import android.content.Context;

import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangming on 2018/4/18.
 */

public class UserPresenter {

    Context mContext;
    UserResponse mUserResponse;

    public UserPresenter(Context mContext) {
        this.mContext = mContext;
        this.mUserResponse = (UserResponse) mContext;
    }

    public void updateUser(Map<String, String> mStringStringMap) {

        Http.getDefault().userEdit(mStringStringMap)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>(mContext) {
                    @Override
                    protected void onSucess(User mUser) {
                        PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);

                        if (mUserResponse != null)
                            mUserResponse.onSuccess();
                    }

                    @Override
                    protected void onFail(String message) {
                        if (mUserResponse != null)
                            mUserResponse.onFail(message);
                    }
                });

    }

    public void updateUser(String key, String value) {

        Map<String, String> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
        mStringStringMap.put(key, value);
        Http.getDefault().userEdit(mStringStringMap)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>(mContext) {
                    @Override
                    protected void onSucess(User mUser) {
                        PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);
                        if (mUserResponse != null)
                            mUserResponse.onSuccess();
                    }

                    @Override
                    protected void onFail(String message) {
                        if (mUserResponse != null)
                            mUserResponse.onFail(message);

                    }
                });

    }

    public interface UserResponse {
        void onSuccess();

        void onFail(String mes);
    }

}
