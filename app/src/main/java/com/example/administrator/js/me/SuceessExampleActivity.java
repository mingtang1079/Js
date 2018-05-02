package com.example.administrator.js.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.FileUtlis;
import com.appbaselib.view.RatioImageView;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.model.Zizhi;
import com.example.administrator.js.me.presenter.ZizhiPresenter;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

@Route(path = "/me/SuceessExampleActivity")

public class SuceessExampleActivity extends BaseActivity implements ZizhiPresenter.ZizhiResponse {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_text)
    EditText mEtText;
    @BindView(R.id.iv_image)
    RatioImageView mIvImage;
    @BindView(R.id.fl_frame)
    FrameLayout mFlFrame;
    @BindView(R.id.tv_tips)
    TextView mTextViewTips;
    @Autowired
    VerifyUser mVerifyUser;

    Uri sfzz;

    ZizhiPresenter mZizhiPresenter;
    MenuItem mMenuItem;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_suceess_example;
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

        mToolbar.setTitle("成功案列");
        mZizhiPresenter = new ZizhiPresenter(this);
        inteMenu();
        if (mVerifyUser != null) {
            if (mVerifyUser.succase != null)
                mEtText.setText(mVerifyUser.succase);
            if (!TextUtils.isEmpty(mVerifyUser.succaseimage)) {
                mTextViewTips.setVisibility(View.GONE);
                ImageLoader.load(mContext, mVerifyUser.succaseimage, mIvImage);
            }

        }
    }

    private void inteMenu() {

        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("保存");

        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {

                submit();

                return true;
            }
        });

    }


    @OnClick(R.id.iv_image)
    public void onViewClicked() {

        onViewClicked(20);
    }

    private void submit() {

        List<String> mStrings = new ArrayList<>();
        mStrings.add(FileUtlis.getRealFilePath(mContext, sfzz));

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

                        if (mFiles == null)
                            return null;
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
                .flatMap(new Function<List<String>, ObservableSource<BaseModel<Zizhi>>>() {
                    @Override
                    public ObservableSource<BaseModel<Zizhi>> apply(List<String> mFiles) throws Exception {

                        Map<String, String> params = new HashMap<>();
                        params.put("succase", mEtText.getText().toString());
                        if (mFiles != null && mFiles.size() != 0)
                            params.put("succaseimage", mFiles.get(0));
                        params.put("userid", UserManager.getInsatance().getUser().id);
                        return Http.getDefault().editZizhi(params);
                    }
                })
                .compose(RxHelper.<Zizhi>handleResult())
                .subscribe(new ResponceSubscriber<Zizhi>(mContext) {
                    @Override
                    protected void onSucess(Zizhi mS) {

                        showToast("保存成功！");
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
                ImageLoader.load(mContext, sfzz, mIvImage);
                mTextViewTips.setVisibility(View.GONE);

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

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(String mes) {

    }
}
