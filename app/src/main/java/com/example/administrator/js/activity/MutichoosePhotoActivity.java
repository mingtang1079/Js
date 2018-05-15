package com.example.administrator.js.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;
import com.example.administrator.js.base.adapter.ChoosePhotoAdapter;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.foamtrace.photopicker.PhotoPreviewActivity.EXTRA_RESULT;
import static com.foamtrace.photopicker.PhotoPreviewActivity.REQUEST_PREVIEW;

/**
 * Created by tangming on 2018/5/15.
 */

public abstract class MutichoosePhotoActivity extends BaseActivity implements ChoosePhotoAdapter.onHeaderOnClickListener {

    private static final int PHOTO_REQUEST_GALLERY = 20;// 从相册中选择


    @BindView(R.id.recyclerview)
    RecyclerView mRvImages;

    public ChoosePhotoAdapter mChoosePhotoAdapter;
    public ArrayList<String> mSelected = new ArrayList<>();

    @CallSuper
    @Override
    protected void initView() {

        mChoosePhotoAdapter = new ChoosePhotoAdapter(R.layout.layout_choose_image, mSelected, this, true);
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 4);
        mRvImages.setLayoutManager(mGridLayoutManager);
        mRvImages.setHasFixedSize(true);
        mRvImages.setAdapter(mChoosePhotoAdapter);
        //预览
        mChoosePhotoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                PhotoPreviewIntent intent = new PhotoPreviewIntent(mContext);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(mSelected);
                startActivityForResult(intent, REQUEST_PREVIEW);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                mChoosePhotoAdapter.notifyDataSetChanged();

            }

        } else if (requestCode == REQUEST_PREVIEW && resultCode == Activity.RESULT_OK) {
            mSelected.clear();
            mSelected.addAll(data.getStringArrayListExtra(EXTRA_RESULT));
            mChoosePhotoAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onHeaderClick() {
        RxPermissions mRxPermissions = new RxPermissions(this);
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
                            intent.setSelectedPaths(mSelected); // 已选中的照片地址， 用于回显选中状态
                            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

                        } else {
                            showToast("请开启相关权限");
                        }
                    }
                });
    }
}
