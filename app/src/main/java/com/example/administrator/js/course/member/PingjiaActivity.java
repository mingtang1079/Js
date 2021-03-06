package com.example.administrator.js.course.member;

import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.ItemAdapter;
import com.example.administrator.js.activity.ItemDividerItemDecoration;
import com.example.administrator.js.activity.ItemGridDividerItemDecoration;
import com.example.administrator.js.exercise.model.SmallCourseType;
import com.example.administrator.js.utils.StringUtils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
    AppCompatRatingBar mRbPingfen;
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

        mItemAdapter = new PingjiaAdapter(R.layout.item_pingjia, mStrings);
        mFlexboxLayoutManager = new FlexboxLayoutManager(mContext);
        mFlexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        mFlexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        mFlexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        mFlexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRecyclerview.setLayoutManager(mFlexboxLayoutManager);
        mRecyclerview.addItemDecoration(new ItemGridDividerItemDecoration());
        mRecyclerview.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter.switchSelectedState(position);
            }
        });

        requestData();
    }


    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().getPinjiatags()
                .flatMap(new Function<BaseModel<List<String>>, ObservableSource<BaseModel<Pingjia>>>() {
                    @Override
                    public ObservableSource<BaseModel<Pingjia>> apply(BaseModel<List<String>> mListBaseModel) throws Exception {
                        if (mListBaseModel != null && mListBaseModel.data != null) {
                            mStrings.addAll(mListBaseModel.data);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mItemAdapter.notifyDataSetChanged();

                                }
                            });
                        }
                        return Http.getDefault().getPingjia(id, UserManager.getInsatance().getUser().id);
                    }
                })
                .as(RxHelper.<Pingjia>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Pingjia>() {
                    @Override
                    protected void onSucess(Pingjia mPingjia) {

                        if (mPingjia != null) {

                            mRbPingfen.setRating(mPingjia.score);
                            if (!TextUtils.isEmpty(mPingjia.keyword)) {
                                List<String> mRe = StringUtils.stringToList(mPingjia.keyword.toString());
                                for (String mS : mItemAdapter.getData()) {

                                    for (String mS1 : mRe) {
                                        if (mS.equals(mS1)) {
                                            mItemAdapter.switchSelectedState(mStrings.indexOf(mS));
                                        }
                                    }

                                }
                            }
                            mEtContent.setText(mPingjia.praisedesc);
                        }

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

//
//        Http.getDefault().getPingjia(id, UserManager.getInsatance().getUser().id)
//                .as(RxHelper.<Pingjia>handleResult(mContext))
//                .subscribe(new ResponceSubscriber<Pingjia>() {
//                    @Override
//                    protected void onSucess(Pingjia mPingjia) {
//
//                        mRbPingfen.setRating(mPingjia.score);
//                        for (String mS : mItemAdapter.getData()) {
//                            if (mS.equals(mPingjia.keyword)) {
//                                mItemAdapter.setSingleChoosed(mStrings.indexOf(mS));
//                                break;
//                            }
//                        }
//                        mEtContent.setText(mPingjia.praisedesc);
//
//                    }
//
//                    @Override
//                    protected void onFail(String message) {
//
//                    }
//                });


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
        mMap.put("score", Integer.valueOf((int) mRbPingfen.getRating()));
        if (mItemAdapter.getSelectedItemCount() != 0) {
            StringUtils.listToString(mItemAdapter.getSelectedItems());
            mMap.put("keyword", StringUtils.listToString(mItemAdapter.getSelectedItems()));
        }
        mMap.put("praisedesc", mEtContent.getText().toString());

        Http.getDefault().savePingjia(mMap)
                .as(RxHelper.<Pingjia>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Pingjia>(mContext) {
                    @Override
                    protected void onSucess(Pingjia mJsonObject) {
                        showToast("评价成功");
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });
    }
}
