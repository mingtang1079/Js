package com.example.administrator.js.activity;

import android.os.Bundle;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewNeedActivity extends MutichoosePhotoActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview_xiangmu)
    RecyclerView mRecyclerviewXiangmu;
    @BindView(R.id.et_wenzi)
    EditText mEtWenzi;

    @BindView(R.id.tv_jian)
    TextView mTvJian;
    @BindView(R.id.et_count)
    EditText mEtCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.ll_address)
    LinearLayout mLlNick;
    @BindView(R.id.btn_sure)
    Button mBtnSure;


    List<String> mStrings = new ArrayList<>();
    ItemAdapter mItemAdapter;
    FlexboxLayoutManager mFlexboxLayoutManager;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_new_need;
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
        mToolbar.setTitle("发布需求");
        mStrings.add("增肌");
        mStrings.add("减脂");
        mStrings.add("塑形");
        mStrings.add("康复");
        mStrings.add("拉伸");
        mStrings.add("其他");
        mItemAdapter = new ItemAdapter(R.layout.item_xiangmu, mStrings);
        mFlexboxLayoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW, FlexWrap.WRAP);
        mRecyclerviewXiangmu.setLayoutManager(mFlexboxLayoutManager);
        mRecyclerviewXiangmu.addItemDecoration(new ItemDividerItemDecoration());
        mRecyclerviewXiangmu.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter.setSingleChoosed(position);
            }
        });
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
    }

    @OnClick({R.id.tv_jian, R.id.tv_add, R.id.ll_address, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jian:
                int m = Integer.parseInt(mEtCount.getText().toString());
                if (m>1) {
                    mEtCount.setText(m - 1 + "");
                }
                else {
                    mEtCount.setText("1");

                }
                break;
            case R.id.tv_add:
                int m2 = Integer.parseInt(mEtCount.getText().toString());
                mEtCount.setText(m2 + 1 + "");
                break;
            case R.id.ll_address:

                break;
            case R.id.btn_sure:


                break;
        }
    }
}
