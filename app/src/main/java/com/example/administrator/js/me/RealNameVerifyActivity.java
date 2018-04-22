package com.example.administrator.js.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.FileUtlis;
import com.appbaselib.utils.PreferenceUtils;
import com.appbaselib.view.RatioImageView;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.VerifyUser;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableConverter;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import top.zibin.luban.Luban;

@Route(path = "/me/RealNameVerifyActivity")
public class RealNameVerifyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    NestedScrollView mNestedScrollView;

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

    VerifyUser mVerifyUser;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_real_name_verify;
    }

    @Override
    protected View getLoadingTargetView() {
        return mNestedScrollView;
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

        mIvShenfengzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                onViewClicked(20);
            }
        });
        mIvShengfenzhengback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                onViewClicked(30);
            }
        });
        mIvGongpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                onViewClicked(40);
            }
        });


        requestData();

    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {


        submit();

    }

    @Override
    protected void requestData() {
        super.requestData();
        Http.getDefault().getAuth(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<VerifyUser>handleResult(mContext))
                .subscribe(new ResponceSubscriber<VerifyUser>() {
                    @Override
                    protected void onSucess(VerifyUser mVerifyUser) {
                        RealNameVerifyActivity.this.mVerifyUser = mVerifyUser;
                        toggleShowLoading(false);
                        setData(mVerifyUser);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });
    }

    private void setData(VerifyUser mVerifyUser) {

        if (mVerifyUser != null) {
            mEtName.setText(mVerifyUser.realname);
            mEtShenfenzheng.setText(mVerifyUser.idnumber);
            if (!TextUtils.isEmpty(mVerifyUser.idimgpath))
                ImageLoader.load(mContext, mVerifyUser.idimgpath, mIvShenfengzheng);
            if (!TextUtils.isEmpty(mVerifyUser.idimgbackpath))
                ImageLoader.load(mContext, mVerifyUser.idimgbackpath, mIvShengfenzhengback);
            if (!TextUtils.isEmpty(mVerifyUser.photopath))
                ImageLoader.load(mContext, mVerifyUser.photopath, mIvGongpai);


        }
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
                .map(new Function<List<File>, List<String>>() {
                    @Override
                    public List<String> apply(List<File> mFiles) throws Exception {

                        final List<String> results = new ArrayList();
                        for (File mFile : mFiles) {
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
                            // MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", mFile.getName(), requestFile);
                            Observable<BaseModel<String>> mObservable = Http.getDefault().uploadImage(filePart);
                            mObservable.subscribe(new Consumer<BaseModel<String>>() {
                                @Override
                                public void accept(BaseModel<String> mStringBaseModel) throws Exception {
                                    results.add(mStringBaseModel.data);
                                }
                            });

                        }

                        return results;
                    }
                })
                .flatMap(new Function<List<String>, ObservableSource<BaseModel<String>>>() {
                    @Override
                    public ObservableSource<BaseModel<String>> apply(List<String> mFiles) throws Exception {
                        Map<String, String> params = new HashMap<>();
                        User mUser = UserManager.getInsatance().getUser();
                        if (mVerifyUser != null)
                            params.put("id", mUser.id);

                        params.put("userid", mUser.id);
                        params.put("realname", mEtName.getText().toString());
                        params.put("idnumber", mEtShenfenzheng.getText().toString());
                        params.put("idimgpath", mFiles.get(0));//正面身份证
                        params.put("idimgbackpath", mFiles.get(1));//反面身份证
                        params.put("photopath", mFiles.get(2));//从业证明

                        return Http.getDefault().verifyzizhi(params);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // 从相册返回的数据

            if (requestCode == 20) {
                sfzz = Uri.parse("file://" + data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0));
                ImageLoader.load(mContext, sfzz, mIvShenfengzheng);

            } else if (requestCode == 30) {
                sfzf = Uri.parse("file://" + data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0));
                ImageLoader.load(mContext, sfzf, mIvShengfenzhengback);

            } else {
                zm = Uri.parse("file://" + data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0));
                ImageLoader.load(mContext, zm, mIvGongpai);

            }
        }
    }

    public void onViewClicked(final int requstcode) {
        RxPermissions mRxPermissions = new RxPermissions((Activity) mContext);
        mRxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean mBoolean) throws Exception {
                        if (mBoolean) {

                            PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                            intent.setSelectModel(SelectModel.SINGLE);
                            intent.setShowCarema(true); // 是否显示拍照
                            //    intent.setMaxTotal(1); // 最多选择照片数量，默认为9
                            startActivityForResult(intent, requstcode);


                        } else {
                            showToast("请开启相关权限");
                        }
                    }
                });
    }

}
