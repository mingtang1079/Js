package com.example.administrator.js.me;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.JsonUtil;
import com.appbaselib.widget.SquareImageView;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.BarcodeModel;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.utils.QRUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BarcodeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_verify)
    TextView mTvVerify;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.iv_barcode)
    SquareImageView mIvBarcode;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_barcode;
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

        mToolbar.setTitle("我的二维码");

        final User mUser = UserManager.getInsatance().getUser();
        if (mUser != null) {
            ImageLoader.load(mContext, mUser.img, mIvHead);
            mTvName.setText(mUser.nickname);
            mTvId.setText("ID：" + mUser.no);

        }

        mIvBarcode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvBarcode.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = mIvBarcode.getWidth();
                BarcodeModel mBarcodeModel = new BarcodeModel(mUser.role, mUser.id);
                String content = JsonUtil.objectToString(mBarcodeModel);
                try {
                 Bitmap mBitmap =QRUtils.encodeToQR(content, width);
                    mIvBarcode.setImageBitmap(mBitmap);

                } catch (Exception mE) {
                    mE.printStackTrace();
                }
            }
        });

    }
}
