package com.example.administrator.js.me;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.FileUtlis;
import com.appbaselib.utils.PreferenceUtils;
import com.appbaselib.view.RatioImageView;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

@Route(path = "/me/RealNameVerifyActivity")
public class RealNameVerifyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_shenfenzheng)
    EditText mEtShenfenzheng;

    @BindView(R.id.iv_shenfengzheng)
    RatioImageView mIvShenfengzheng;
    @BindView(R.id.iv_shengfenzhengback)
    RatioImageView mIvShengfenzhengback;
    @BindView(R.id.iv_gongpai)
    RatioImageView mIvGongpai;
    @BindView(R.id.iv_zhengshu)
    RatioImageView mIvZhengshu;
    @BindView(R.id.tv_sure)
    TextView mTvSure;

    Uri sfzz;//正面
    Uri sfzf;//反面
    Uri zm;//证明
    Uri zs;//整数

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_real_name_verify;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("实名认证");


        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mEtName);
        Observable<CharSequence> mCharSequenceObservablePassword = RxTextView.textChanges(mEtShenfenzheng);
        Observable.combineLatest(mObservablePhone, mCharSequenceObservablePassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2) throws Exception {
                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mTvSure.setEnabled(mBoolean);
            }
        });
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {


        submit();

    }

    private void submit() {

        List<String> mStrings = new ArrayList<>();
        mStrings.add(FileUtlis.getRealFilePath(mContext, sfzz));
        mStrings.add(FileUtlis.getRealFilePath(mContext, sfzf));
        mStrings.add(FileUtlis.getRealFilePath(mContext, zm));
        mStrings.add(FileUtlis.getRealFilePath(mContext, zs));

        Observable.just(mStrings)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(mContext).load(list).get();
                    }
                })
                .flatMap(new Function<List<File>, ObservableSource<BaseModel<String>>>() {
                    @Override
                    public ObservableSource<BaseModel<String>> apply(List<File> mFiles) throws Exception {
                        Map<String, RequestBody> params = new HashMap<>();
                        //以下参数是伪代码，参数需要换成自己服务器支持的
                        params.put("realname", convertToRequestBody(mEtName.getText().toString()));
                        params.put("idnumber", convertToRequestBody(mEtShenfenzheng.getText().toString()));

                        List<MultipartBody.Part> partList = filesToMultipartBodyParts(mFiles);
                        return Http.getDefault().verifyzizhi(params, partList);
                    }
                })
                .compose(RxHelper.<String>handleResult())
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        showToast("认证成功！");
                        finish();

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("multipartFiles", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
