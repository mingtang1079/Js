package com.example.administrator.js.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.FileUtlis;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.adapter.AddShencaiAdapter;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.presenter.ZizhiPresenter;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

import static com.foamtrace.photopicker.PhotoPreviewActivity.EXTRA_RESULT;
import static com.foamtrace.photopicker.PhotoPreviewActivity.REQUEST_PREVIEW;

@Route(path = "/me/XingxiangzhaoActivity")
public class XingxiangzhaoActivity extends BaseActivity implements ZizhiPresenter.ZizhiResponse {

    private static final int PHOTO_REQUEST_GALLERY = 20;// 从相册中选择

    @Autowired
    VerifyUser mVerifyUser;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.fl_frame)
    FrameLayout mFlFrame;
    @BindView(R.id.tv_save)
    TextView mTextViewSave;

    AddShencaiAdapter mAddShencaiAdapter;
    ArrayList<String> mSelected;
    MenuItem mMenuItem;

    ZizhiPresenter mZizhiPresenter;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void initMenu() {
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(false);
        mMenuItem.setTitle("添加");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                onViewClicked(20);
                return true;
            }
        });
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("形象照");
        initMenu();
        if (mVerifyUser != null) {
            ArrayList<String> mStrings = new ArrayList<>(Arrays.asList(mVerifyUser.photopath.split(",")));
            mSelected = mStrings;
        } else {
            mSelected = new ArrayList<>();
        }

        mAddShencaiAdapter = new AddShencaiAdapter(R.layout.item_add_shencai, mSelected);
        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 2);
        mRecyclerview.setLayoutManager(layoutManage);
        mRecyclerview.setAdapter(mAddShencaiAdapter);
        mAddShencaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(mContext);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(mSelected);
                startActivityForResult(intent, REQUEST_PREVIEW);
            }
        });
        mAddShencaiAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (mSelected.size() == 0) {
                    mRecyclerview.setVisibility(View.GONE);
                    mFlFrame.setVisibility(View.VISIBLE);
                    mMenuItem.setEnabled(false);
                } else {
                    mRecyclerview.setVisibility(View.VISIBLE);
                    mFlFrame.setVisibility(View.GONE);
                    mMenuItem.setEnabled(true);

                }
            }
        });
        mAddShencaiAdapter.notifyDataSetChanged();
        mZizhiPresenter = new ZizhiPresenter(this);
        mTextViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                save();
            }
        });
    }

    private void save() {
            if (mMenuItem.getItemId() == R.id.btn_common) {


                Observable.just(mSelected)
                        .map(new Function<ArrayList<String>, ArrayList<String>>() {
                            @Override
                            public ArrayList<String> apply(ArrayList<String> mStrings) throws Exception {
                                //筛选已经上传过的数据（编辑状态会存在上传过的照片）

                                ArrayList<String> m=new ArrayList<>();
                                for (String mS :
                                        mStrings) {
                                    if (!mS.startsWith("http")){
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
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<String>>() {
                            @Override
                            public void accept(List<String> mStrings) throws Exception {

                                //mstrings 此时有可能比 mselected小
                                if (mStrings.size()<mSelected.size())
                                {
                                    ArrayList<String> mOrigin=new ArrayList<>();
                                    for (String mS :
                                            mSelected) {
                                        if (mS.startsWith("http")){
                                            mOrigin.add(mS);
                                        }
                                    }

                                    mStrings.addAll(0,mOrigin);
                                }
                                StringBuilder mStringBuilder = new StringBuilder();
                                for (int i = 0, nsize = mStrings.size(); i < nsize; i++) {
                                    String value = mStrings.get(i);
                                    if (i == 0) {
                                        mStringBuilder.append(value);
                                    } else {
                                        mStringBuilder.append("," + value);
                                    }

                                }

                                mZizhiPresenter.updateZizhi("photopath", mStringBuilder.toString());

                            }
                        });
            }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_xingxiangzhao;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick(R.id.fl_frame)
    public void onViewClicked() {

        onViewClicked(20);


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
                            intent.setSelectModel(SelectModel.MULTI);
                            intent.setShowCarema(true); // 是否显示拍照
                            intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                            intent.setSelectedPaths((ArrayList<String>) mAddShencaiAdapter.getData());
                            startActivityForResult(intent, requstcode);


                        } else {
                            showToast("请开启相关权限");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                if (mSelected == null) {
                    mSelected = new ArrayList<>();
                }
                mSelected.clear();
                mSelected.addAll(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                mAddShencaiAdapter.notifyDataSetChanged();

            }

        } else if (requestCode == REQUEST_PREVIEW && resultCode == Activity.RESULT_OK) {
            mSelected.clear();
            mSelected.addAll(data.getStringArrayListExtra(EXTRA_RESULT));
            mAddShencaiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onFail(String mes) {

    }
}
