package com.example.administrator.js.course.member;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.appbaselib.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.ItemAdapter;
import com.example.administrator.js.activity.ItemDividerItemDecoration;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangming on 2018/6/25.
 */

public class PingjiaActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.rb_pingfen)
    RatingBar mRbPingfen;
    @BindView(R.id.btn_sure)
    Button mBtnSure;

    List<String> mStrings = new ArrayList<>();
    ItemAdapter mItemAdapter;
    FlexboxLayoutManager mFlexboxLayoutManager;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setNavigationIcon(R.drawable.close);
        mToolbar.setTitle("评价");
        mStrings.add("达到预期");
        mStrings.add("教练有过迟到");
        mStrings.add("任职认真");
        mStrings.add("非正常接触");

        mItemAdapter = new ItemAdapter(R.layout.item_xiangmu, mStrings);
        mFlexboxLayoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW, FlexWrap.WRAP);
        mRecyclerview.setLayoutManager(mFlexboxLayoutManager);
        mRecyclerview.addItemDecoration(new ItemDividerItemDecoration());
        mRecyclerview.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter.setSingleChoosed(position);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pingjia;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
    }
}
