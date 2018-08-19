package com.example.administrator.js.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.example.administrator.js.R;

import uk.co.senab.photoview.DefaultOnDoubleTapListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

@Route(path = "/activity/LookBigImageActivity")
public class LookBigImageActivity extends BaseActivity {

    @Autowired
    String url;

    @Override

    protected int getContentViewLayoutID() {
        return R.layout.activity_look_big_image;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {
        PhotoView imageView = (PhotoView) findViewById(R.id.iv_pager);

        ImageLoader.load(this, url, imageView);

        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });

    }
}
