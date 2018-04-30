package com.example.administrator.js.me.presenter;

import android.content.Context;

import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.ToastUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.Zizhi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;

/**
 * Created by tangming on 2018/4/26.
 */

public class ZizhiPresenter {

    Context mContext;

    ZizhiResponse mZizhiResponse;

    public ZizhiPresenter(Context mContext) {
        this.mContext = mContext;
        mZizhiResponse = (ZizhiResponse) mContext;
    }

    public void updateZizhi(String key, String value) {
        Map<String, String> mStringStringMap = new HashMap<>();
        mStringStringMap.put("userid", UserManager.getInsatance().getUser().id);
        mStringStringMap.put(key, value);
        Http.getDefault().editZizhi(mStringStringMap)
                .as(RxHelper.<Zizhi>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Zizhi>(mContext) {
                    @Override
                    protected void onSucess(Zizhi mZizhi) {

                        ToastUtils.showShort(mContext, "保存成功！");
                        if (mZizhiResponse != null)
                            mZizhiResponse.onSuccess();
                    }

                    @Override
                    protected void onFail(String message) {
                        ToastUtils.showShort(mContext, message);

                        if (mZizhiResponse != null)
                            mZizhiResponse.onFail(message);
                    }
                });

    }


    public interface ZizhiResponse {
        void onSuccess();

        void onFail(String mes);
    }

}
