package com.example.administrator.test.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.FileUtlis;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.test.R;
import com.example.administrator.test.R2;
import com.example.administrator.test.presenter.UserPresenter;
import com.example.core.App;
import com.example.core.UserManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.DefaultSubscriber;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

@Route(path = "/me/ChangeUserHeadActivity")

public class ChangeUserHeadActivity extends BaseActivity implements UserPresenter.UserResponse, Toolbar.OnMenuItemClickListener {

    private static final int PHOTO_REQUEST_GALLERY = 20;// 从相册中选择

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.imageview)
    PhotoView mImageview;

    UserPresenter mUserPresenter;
    Uri mPortriat;
    MenuItem mMenuItem;


    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void initView() {

        mToolbar.setTitle("修改头像");
        mUserPresenter = new UserPresenter(this);
        ImageLoader.load(this, UserManager.getInsatance().getUser().img, mImageview, new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mImageview.setImageDrawable(getResources().getDrawable(R.drawable.icon_camera));
                return true;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        });
        mImageview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                onViewClicked();
            }
        });

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(false);
        mMenuItem.setTitle("完成");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.btn_common) {
            uploadPortriat();
        }
        return true;
    }

    private void uploadPortriat() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径

                Uri mUri = Uri.parse("file://" + data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0));
                UCrop.Options options = new UCrop.Options();
                options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));

                UCrop.of(mUri, Uri.fromFile(new File(getCacheDir(), DateUtils.getCurrentTimeInString() + ".jpg")))
                        .withOptions(options)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(1000, 1000)
                        .start(this);
            }

        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            mPortriat = UCrop.getOutput(data);
            mMenuItem.setEnabled(true);
            ImageLoader.load(mContext, mPortriat, mImageview);

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            showToast(cropError.getMessage());
        }
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_change_head;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    public void onViewClicked() {
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
                            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);


                        } else {
                            showToast("请开启相关权限");
                        }
                    }
                });
    }


    @Override
    public void onSuccess() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onFail(String mes) {
        showToast(mes);
    }


}
