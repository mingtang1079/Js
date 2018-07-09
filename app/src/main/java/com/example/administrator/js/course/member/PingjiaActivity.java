package com.example.administrator.js.course.member;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.ItemAdapter;
import com.example.administrator.js.activity.ItemDividerItemDecoration;
import com.example.administrator.js.exercise.model.SmallCourseType;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangming on 2018/6/25.
 */
@Route(path = "/member/PingjiaActivity")
public class PingjiaActivity extends BaseActivity {

    @Autowired
    String id;
    @Autowired
    String tid;

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
    PingjiaAdapter mItemAdapter;
    FlexboxLayoutManager mFlexboxLayoutManager;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setNavigationIcon(R.drawable.close);
        mToolbar.setTitle("评价");

        mItemAdapter = new PingjiaAdapter(R.layout.item_xiangmu, mStrings);
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

        requestData();
    }


    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().getPinjiatags()
                .as(RxHelper.<List<String>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<List<String>>() {
                    @Override
                    protected void onSucess(List<String> mSmallCourseTypes) {
                        mItemAdapter.setNewData(mSmallCourseTypes);
                    }

                    @Override
                    protected void onFail(String message) {

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

        Map<String, Object> mMap = new HashMap<>();
        mMap.put("courseid", id);
        mMap.put("userid", UserManager.getInsatance().getUser().id);
        mMap.put("tid", tid);
        mMap.put("score", mRbPingfen.getRating());
        mMap.put("keyword", mItemAdapter.getSingleSelectedItems());
        mMap.put("praisedesc", mEtContent.getText().toString());

        Http.getDefault().savePingjia(mMap).as(RxHelper.<JsonObject>handleResult(mContext))
                .subscribe(new ResponceSubscriber<JsonObject>() {
                    @Override
                    protected void onSucess(JsonObject mJsonObject) {
                        showToast("评价成功");
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });
    }
}
