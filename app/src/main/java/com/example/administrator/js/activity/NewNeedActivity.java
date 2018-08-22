package com.example.administrator.js.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.AdressHelper;
import com.appbaselib.utils.DialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.locaiton.NewNeedAndType;
import com.example.administrator.js.course.model.Item;
import com.example.administrator.js.exercise.model.SmallCourseType;
import com.example.administrator.js.me.model.NewNeed;
import com.example.administrator.js.utils.StringUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

public class NewNeedActivity extends MutichoosePhotoActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    ScrollView mScrollView;

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
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLlNick;
    @BindView(R.id.btn_sure)
    TextView mBtnSure;


    List<SmallCourseType> mStrings = new ArrayList<>();
    ItemAdapter mItemAdapter;
    GridLayoutManager mFlexboxLayoutManager;

    NewNeed mNewNeed = new NewNeed();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_new_need;
    }

    @Override
    protected View getLoadingTargetView() {
        return mScrollView;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("发布需求");
//        mStrings.add("增肌");
//        mStrings.add("减脂");
//        mStrings.add("塑形");
//        mStrings.add("康复");
//        mStrings.add("拉伸");
//        mStrings.add("其他");
        mItemAdapter = new ItemAdapter(R.layout.item_xiangmu, mStrings);
        mFlexboxLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerviewXiangmu.setLayoutManager(mFlexboxLayoutManager);
        mRecyclerviewXiangmu.addItemDecoration(new ItemGridDividerItemDecoration());
        mRecyclerviewXiangmu.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mItemAdapter.getSelectedItemCount() == 3 && !mItemAdapter.isSelected(position)) {
                    return;
                } else {
                    mItemAdapter.switchSelectedState(position);
                }
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
                    mNewNeed.csum = 1;
                } else {
                    mNewNeed.csum = Integer.valueOf(mCharSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
        RxTextView.textChanges(mEtWenzi)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence mCharSequence) throws Exception {
                        mNewNeed.detail = mCharSequence.toString();
                    }
                });

        mItemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                StringBuilder mStringBuilder = new StringBuilder();
                for (int j = 0; j < mItemAdapter.getSelectedItems().size(); j++) {
                    SmallCourseType value = mItemAdapter.getSelectedItems().get(j);
                    if (j == 0) {
                        mStringBuilder.append(value.id);
                    } else {
                        mStringBuilder.append("," + value.id);
                    }
                }
                mNewNeed.coursetypeids = mStringBuilder.toString();
            }
        });

        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();

        Observable<BaseModel<NewNeed>> mBaseModelObservable = Http.getDefault().getNeed(UserManager.getInsatance().getUser().id);

//                .as(RxHelper.<NewNeed>handleResult(mContext))
//                .subscribe(new ResponceSubscriber<NewNeed>() {
//                    @Override
//                    protected void onSucess(NewNeed m) {
//                        if (m != null) {
//                            mNewNeed = m;
//                        }
//                        setData(mNewNeed);
//                    }
//
//                    @Override
//                    protected void onFail(String message) {
//
//                    }
//                });

        Observable<BaseModel<List<SmallCourseType>>> mBaseModelObservable1 = Http.getDefault().getSmallcourseType();

        Observable.zip(mBaseModelObservable, mBaseModelObservable1, new BiFunction<BaseModel<NewNeed>, BaseModel<List<SmallCourseType>>, NewNeedAndType>() {
            @Override
            public NewNeedAndType apply(BaseModel<NewNeed> mNewNeedBaseModel, BaseModel<List<SmallCourseType>> mListBaseModel) throws Exception {

                if (!mListBaseModel.status) {

                    new Throwable(mNewNeedBaseModel.msg);
                }

                if (!mNewNeedBaseModel.status) {

                    new Throwable(mNewNeedBaseModel.msg);
                }

                return new NewNeedAndType(mNewNeedBaseModel.data, mListBaseModel.data);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<NewNeedAndType>() {
            @Override
            public void onNext(NewNeedAndType mNewNeedAndType) {
                mItemAdapter.setNewData(mNewNeedAndType.mSmallCourseTypes);

                if (mNewNeedAndType.mNewNeed != null) {
                    mNewNeed = mNewNeedAndType.mNewNeed;
                    mBtnSure.setText("保存");
                }
                setData(mNewNeed);
            }

            @Override
            public void onError(Throwable e) {
                showToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });


//        Http.getDefault().getSmallcourseType()
//                .as(RxHelper.<List<SmallCourseType>>handleResult(mContext))
//                .subscribe(new ResponceSubscriber<List<SmallCourseType>>() {
//                    @Override
//                    protected void onSucess(List<SmallCourseType> mSmallCourseTypes) {
//                        mItemAdapter.setNewData(mSmallCourseTypes);
//                    }
//
//                    @Override
//                    protected void onFail(String message) {
//
//                    }
//                });

    }

    private void setData(NewNeed mNewNeedBaseModel) {

        mEtWenzi.setText(mNewNeedBaseModel.detail);
        mEtCount.setText(mNewNeedBaseModel.csum == null ? "1" : mNewNeedBaseModel.csum + "");
        if (StringUtils.stringToList(mNewNeedBaseModel.detailimg) != null) {
            mSelected.addAll(StringUtils.stringToList(mNewNeedBaseModel.detailimg));
        }
        mTvAddress.setText(mNewNeedBaseModel.areaname);
        if (!TextUtils.isEmpty(mNewNeedBaseModel.coursetypeids)) {
            List<String> mStrings = StringUtils.stringToList(mNewNeedBaseModel.coursetypeids);
            for (int i = 0; i < mItemAdapter.getData().size(); i++) {
                for (String mS : mStrings)
                    if (mS.equals(mItemAdapter.getData().get(i).id)) {
                        mItemAdapter.switchSelectedState(i);
                    }
            }
        }
    }

    @OnClick({R.id.tv_jian, R.id.tv_add, R.id.ll_address, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.ll_address:
                AdressHelper.showAddressSelector(mContext, new OnItemClickListener() {
                    @Override
                    public void itemClick(City mProvice, City mCity, City mCounty) {
                        mTvAddress.setText(mProvice.name + " " + mCity.name + " " + mCounty.name);

                        mNewNeed.areacode = mCounty.id;

                    }
                });
                break;
            case R.id.btn_sure:
                if (TextUtils.isEmpty(mNewNeed.areacode)) {
                    showToast("请选择地址");
                    return;
                }

                if (UserManager.getInsatance().getUser().areacode.equals(mNewNeed.areacode)) {
                    save();
                } else {

                    DialogUtils.getDefaultDialog(mContext, "", "您的上课地址更换到" + mTvAddress.getText().toString() + ",确定更新并发布吗？", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface mDialogInterface, int mI) {

                            save();

                        }
                    }).show();

                }

                break;
        }
    }

    String areacode;
    String coursetypeids;

    @SuppressLint("CheckResult")
    private void save() {

        if (TextUtils.isEmpty(mNewNeed.coursetypeids)) {
            showToast("请选择需求类型");
            return;
        }
        if (TextUtils.isEmpty(mNewNeed.detail)) {
            showToast("请填写详细描述");
            return;
        }

        final Map<String, Object> mStringObjectMap = new HashMap<>();
        mStringObjectMap.put("userid", UserManager.getInsatance().getUser().id);
        mStringObjectMap.put("coursetypeids", mNewNeed.coursetypeids);
        mStringObjectMap.put("areacode", mNewNeed.areacode);
        mStringObjectMap.put("csum", mNewNeed.csum);
        mStringObjectMap.put("detail", mNewNeed.detail);

        //  mStringObjectMap.put("detailimg", mNewNeed.detailimg);

        Observable.just(mSelected)
                .map(new Function<ArrayList<String>, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> apply(ArrayList<String> mStrings) throws Exception {
                        //筛选已经上传过的数据（编辑状态会存在上传过的照片）
                        ArrayList<String> m = new ArrayList<>();
                        for (String mS :
                                mStrings) {
                            if (!mS.startsWith("http")) {
                                m.add(mS);
                            }
                        }
                        return m;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(mContext).load(list).get();
                    }
                })
                .map(new Function<List<File>, List<String>>() {
                    @Override
                    public List<String> apply(List<File> mFiles) throws Exception {

                        final List<String> results = new ArrayList();
                        for (File mFile : mFiles) {
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
                            // MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", mFile.getName(), requestFile);
                            Observable<BaseModel<String>> mObservable = Http.getDefault().uploadImage(filePart);
//                            mObservable.subscribe(new Consumer<BaseModel<String>>() {
//                                @Override
//                                public void accept(BaseModel<String> mStringBaseModel) throws Exception {
//                                    results.add(mStringBaseModel.data);
//                                }
//                            });
                            results.add(mObservable.blockingFirst().data);

                        }
                        return results;
                    }
                }).flatMap(new Function<List<String>, ObservableSource<BaseModel<NewNeed>>>() {
            @Override
            public ObservableSource<BaseModel<NewNeed>> apply(List<String> mStrings) throws Exception {

                //mstrings 此时有可能比 mselected小
                if (mStrings.size() < mSelected.size()) {
                    ArrayList<String> mOrigin = new ArrayList<>();
                    for (String mS :
                            mSelected) {
                        if (mS.startsWith("http")) {
                            mOrigin.add(mS);
                        }
                    }

                    mStrings.addAll(0, mOrigin);
                }

                if (!TextUtils.isEmpty(StringUtils.listToString(mStrings))) {
                    mStringObjectMap.put("detailimg", StringUtils.listToString(mStrings));
                }
                return Http.getDefault().publishNeed(mStringObjectMap);
            }
        }).as(RxHelper.<NewNeed>handleResult(mContext))
                .subscribe(new ResponceSubscriber<NewNeed>(mContext) {
                    @Override
                    protected void onSucess(NewNeed mS) {
                        showToast("发布成功！");
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

}
