package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.FileUtlis;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MutichoosePhotoActivity;
import com.example.administrator.js.base.adapter.ChoosePhotoAdapter;
import com.example.administrator.js.me.model.Zizhi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class FankuiActivity extends MutichoosePhotoActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rb_zixun)
    RadioButton mRbZixun;
    @BindView(R.id.rb_tousu)
    RadioButton mRbTousu;
    @BindView(R.id.rb_jianyi)
    RadioButton mRbJianyi;
    @BindView(R.id.rb_qita)
    RadioButton mRbQita;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.et_text)
    EditText mEtText;

    MenuItem mMenuItem;
    String type = "0";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_fankui;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void initMenu() {
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(true);
        mMenuItem.setTitle("提交");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                save();
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        initMenu();
        mToolbar.setTitle("反馈内容");
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup mRadioGroup, int mI) {
                if (mI == R.id.rb_zixun) {
                    type = "0";
                } else if (mI == R.id.rb_tousu) {
                    type = "1";

                } else if (mI == R.id.rb_jianyi) {
                    type = "2";

                } else {
                    type = "3";
                }
            }
        });

    }


    private void save() {

        List<String> mStrings = new ArrayList<>();
        for (String m :
                mSelected) {

            mStrings.add(m);
        }

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
                .flatMap(new Function<List<String>, ObservableSource<BaseModel<String>>>() {
                    @Override
                    public ObservableSource<BaseModel<String>> apply(List<String> mFiles) throws Exception {

                        Map<String, String> params = new HashMap<>();
                        params.put("content", mEtText.getText().toString());
                        params.put("userid", UserManager.getInsatance().getUser().id);
                        if (mFiles != null && mFiles.size() != 0) {
                            params.put("pic1", mFiles.toArray().toString());
                        }
                        params.put("type", type);
                        return Http.getDefault().feed(params);
                    }
                })
                .compose(RxHelper.<String>handleResult())
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        showToast("提交成功！");
                        finish();

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

}
