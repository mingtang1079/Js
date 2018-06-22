package com.example.administrator.js.vipandtrainer;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BuySiJiaoKeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_choose_price)
    TextView mTvChoosePrice;
    @BindView(R.id.recyclerview_type)
    RecyclerView mRecyclerviewType;
    @BindView(R.id.recyclerview_small_type)
    RecyclerView mRecyclerviewSmallType;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;
    @BindView(R.id.tv_course_name)
    TextView mTvCourseName;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_jian)
    TextView mTvJian;
    @BindView(R.id.et_count)
    EditText mEtCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_youhui_price)
    TextView mTvYouhuiPrice;
    @BindView(R.id.tv_all_price)
    TextView mTvAllPrice;
    @BindView(R.id.btn_sure)
    Button mBtnSure;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_buy_si_jiao_ke;
    }

    @Override
    protected View getLoadingTargetView() {
        return mNestedScrollView;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("购买私教课");

        mEtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                if (TextUtils.isEmpty(mCharSequence)) {
                    mEtCount.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });

        toggleShowLoading(true);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
    }

    private void  setData(User mData)
    {




    }

    @OnClick({R.id.ll_address, R.id.tv_jian, R.id.tv_add, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                break;
            case R.id.tv_jian:
                int m = Integer.parseInt(mEtCount.getText().toString());
                if (m > 1) {
                    mEtCount.setText(m - 1 + "");
                } else {
                    mEtCount.setText("1");

                }
                break;
            case R.id.tv_add:

                int m2 = Integer.parseInt(mEtCount.getText().toString());
                mEtCount.setText(m2 + 1 + "");
                break;
            case R.id.btn_sure:
                break;
        }
    }
}
