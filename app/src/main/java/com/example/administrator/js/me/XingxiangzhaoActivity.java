package com.example.administrator.js.me;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.appbaselib.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;
import com.example.administrator.js.me.adapter.AddShencaiAdapter;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class XingxiangzhaoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.fl_frame)
    FrameLayout mFlFrame;

    AddShencaiAdapter mAddShencaiAdapter;
    List<String> mStrings = new ArrayList<>();
    MenuItem mMenuItem;


    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(false);
        mMenuItem.setTitle("保存");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getItemId() == R.id.btn_common) {


                }
                return true;
            }
        });
    }

    @Override
    protected void initView() {

        mAddShencaiAdapter = new AddShencaiAdapter(R.layout.item_add_shencai, mStrings);


        mAddShencaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAddShencaiAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (mStrings.size() == 0) {
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
                            intent.setSelectModel(SelectModel.SINGLE);
                            intent.setShowCarema(true); // 是否显示拍照
                            //    intent.setMaxTotal(1); // 最多选择照片数量，默认为9
                            intent.setSelectedPaths((ArrayList<String>) mAddShencaiAdapter.getData());
                            startActivityForResult(intent, requstcode);


                        } else {
                            showToast("请开启相关权限");
                        }
                    }
                });
    }
}
