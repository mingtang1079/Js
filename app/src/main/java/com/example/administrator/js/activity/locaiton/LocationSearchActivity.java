package com.example.administrator.js.activity.locaiton;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AndroidException;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.LocationManager;
import com.example.administrator.js.R;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LocationSearchActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    ArrayList<PoiItem> mPoiItems = new ArrayList<>();
    AddressAdapter mAddressAdapter;

    protected SearchView mSearchView;
    protected SearchView.SearchAutoComplete mSearchAutoComplete;

    private PoiSearch mPoiSearch;
    private PoiSearch.Query mQuery;
    private PoiSearch.OnPoiSearchListener mOnPoiSearchListener;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_location_search;
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
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.location_search);
        MenuItem mMenuItem_scan = mToolbar.getMenu().findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mMenuItem_scan);
        mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(true);
        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setTextSize(14);
        mSearchView.setQueryHint("请输入地址关键字");

        RxTextView.textChanges(mSearchAutoComplete)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence mCharSequence) throws Exception {
                        if (TextUtils.isEmpty(mCharSequence)) {
                            mPoiItems.clear();
                            mAddressAdapter.notifyDataSetChanged();
                        } else {
                            doSearchQuery(mCharSequence.toString(), LocationManager.getInsatance().city, new LatLonPoint(Double.valueOf(LocationManager.getInsatance().latitude), Double.valueOf(LocationManager.getInsatance().longitude)));
                        }

                    }
                });

        mOnPoiSearchListener = new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int i) {
                if (i == 1000) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(mQuery)) {// 是否是同一条
                            if (null != mPoiItems) {
                                mPoiItems.clear();
                            }
                            mPoiItems.addAll(result.getPois());// 取得第一页的poiitem数据，页数从数字0开始
                            mAddressAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        };
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord, String city, LatLonPoint lpTemp) {
        mQuery = new PoiSearch.Query(keyWord, "", city);//第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mQuery.setPageSize(20);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(0);// 设置查第一页


        mPoiSearch = new PoiSearch(this, mQuery);
        mPoiSearch.setOnPoiSearchListener(mOnPoiSearchListener);
        if (lpTemp != null) {
            mPoiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 10000, true));//该范围的中心点-----半径，单位：米-----是否按照距离排序
        }
        mPoiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    protected void initView() {

        mAddressAdapter = new AddressAdapter(R.layout.item_address_map, mPoiItems);
        mAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                PoiItem tip = mPoiItems.get(position);
                Intent intent = new Intent();
                intent.putExtra("PoiItem", tip);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setAdapter(mAddressAdapter);
    }
}
