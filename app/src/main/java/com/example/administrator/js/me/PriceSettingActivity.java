package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.Price;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/me/PriceSettingActivity")
public class PriceSettingActivity extends BaseActivity {

    @Autowired
    String type;
    @Autowired
    Price mPrice;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_price_type)
    TextView mTvPriceType;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.seekbar)
    AppCompatSeekBar mSeekbar;
    @BindView(R.id.tv_min_price)
    TextView mTextViewMinPrice;
    @BindView(R.id.tv_max_price)
    TextView mTextViewMaxPrice;
    @BindView(R.id.btn_sure)
    Button mButtonSure;


    int minValue;
    int maxValue;

    String canshuName;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_price_setting;
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

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar mSeekBar, int mI, boolean mB) {
                int value;
                if (mI == 0) {
                    value = minValue;
                } else if (mI == maxValue) {

                    value = maxValue;

                } else if (mI < minValue) {
                    value = minValue + mI;
                } else {
                    value = mI;
                }
                mTvPrice.setText(value + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar mSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar mSeekBar) {

            }
        });

        mButtonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                save();
            }
        });


        if ("pricea".equals(type)) {
            mToolbar.setTitle("常规课");
            canshuName = "pricea";
            if (mPrice != null && mPrice.userprice != null&&!TextUtils.isEmpty(mPrice.userprice.pricea)) {
                mTvPrice.setText(mPrice.userprice.pricea);
                mSeekbar.setProgress(Integer.parseInt(mPrice.userprice.pricea));
            }
            if (mPrice != null && mPrice.range != null) {
                mTextViewMinPrice.setText(mPrice.range.amin + "");
                mTextViewMaxPrice.setText(mPrice.range.amax + "");
                mSeekbar.setMax(mPrice.range.amax);
                minValue = mPrice.range.amin;
                maxValue = mPrice.range.amax;
            }


        } else if ("priceb".equals(type)) {
            mToolbar.setTitle("特色课");
            canshuName = "priceb";

            if (mPrice != null && mPrice.userprice != null&&!TextUtils.isEmpty(mPrice.userprice.priceb)) {
                mTvPrice.setText(mPrice.userprice.priceb);
                mSeekbar.setProgress(Integer.parseInt(mPrice.userprice.priceb));

            }
            if (mPrice != null && mPrice.range != null) {
                mTextViewMinPrice.setText(mPrice.range.bmin + "");
                mTextViewMaxPrice.setText(mPrice.range.bmax + "");
                mSeekbar.setMax(mPrice.range.bmax);
                minValue = mPrice.range.bmin;
                maxValue = mPrice.range.bmax;

            }

        } else {
            mToolbar.setTitle("康复课");
            canshuName = "pricec";

            if (mPrice != null && mPrice.userprice != null&&!TextUtils.isEmpty(mPrice.userprice.pricec)) {
                mTvPrice.setText(mPrice.userprice.pricec);
                mSeekbar.setProgress(Integer.parseInt(mPrice.userprice.pricec));

            }
            if (mPrice != null && mPrice.range != null) {
                mTextViewMinPrice.setText(mPrice.range.cmin + "");
                mTextViewMaxPrice.setText(mPrice.range.cmax + "");
                mSeekbar.setMax(mPrice.range.cmax);
                minValue = mPrice.range.cmin;
                maxValue = mPrice.range.cmax;

            }
        }

    }

    private void save() {
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("id", UserManager.getInsatance().getUser().id);
        mMap.put(canshuName, Integer.valueOf(mTvPrice.getText().toString()));

        Http.getDefault().saveprice(mMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>() {
                    @Override
                    protected void onSucess(String mS) {
                        showToast("保存成功！");
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);

                    }
                });

    }


}
