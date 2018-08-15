package com.example.administrator.js.me;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MutichoosePhotoActivity;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.model.Zizhi;
import com.example.administrator.js.me.presenter.ZizhiPresenter;
import com.example.administrator.js.utils.StringUtils;
import com.example.administrator.js.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
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

@Route(path = "/me/ZhengshuActivity")
public class ZhengshuActivity extends MutichoosePhotoActivity implements ZizhiPresenter.ZizhiResponse {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Autowired
    VerifyUser mVerifyUser;

    ZizhiPresenter mZizhiPresenter;
    MenuItem mMenuItem;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_zhengshu;
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
        super.initView();
        mToolbar.setTitle("资质证书");
        mZizhiPresenter = new ZizhiPresenter(this);
        inteMenu();
        if (mVerifyUser != null) {
            if (!TextUtils.isEmpty(mVerifyUser.certpath)) {

                mChoosePhotoAdapter.addData(StringUtils.stringToList(mVerifyUser.certpath));


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

    private void submit() {

        Observable.just(mSelected)
                .map(new Function<ArrayList<String>, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> apply(ArrayList<String> mStrings) throws Exception {
                        //筛选已经上传过的数据（编辑状态会存在上传过的照片）
                        ArrayList<String> m = new ArrayList<>();
                        for (String mS :
                                mStrings) {
                            if (!mS.startsWith("http")) {
                                m.add(mS);
                            }
                        }
                        return m;
                    }
                })
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

                        //mstrings 此时有可能比 mselected小,因为筛选过的
                        if (mFiles.size() < mSelected.size()) {
                            ArrayList<String> mOrigin = new ArrayList<>();
                            for (String mS :
                                    mSelected) {
                                if (mS.startsWith("http")) {
                                    mOrigin.add(mS);
                                }
                            }

                            mFiles.addAll(0, mOrigin);
                        }


                        if (mFiles != null && mFiles.size() != 0) {

                            String url = Utils.list2String(mFiles);
                            params.put("certpath", url);
                        }
                        params.put("userid", UserManager.getInsatance().getUser().id);
                        return Http.getDefault().editZizhi(params);
                    }
                })
                .as(RxHelper.<Zizhi>handleResult(mContext))
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
    public void onSuccess() {

    }

    @Override
    public void onFail(String mes) {

    }
}
