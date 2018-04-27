package com.example.administrator.js.me;

import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.me.presenter.ZizhiPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ShanChangActivity extends BaseActivity implements ZizhiPresenter.ZizhiResponse {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_one)
    LinearLayout mLlOne;
    @BindView(R.id.iv_one)
    ImageView mIvOne;
    @BindView(R.id.ll_two)
    LinearLayout mLlTwo;
    @BindView(R.id.iv_two)
    ImageView mIvTwo;
    @BindView(R.id.ll_three)
    LinearLayout mLlThree;
    @BindView(R.id.iv_three)
    ImageView mIvThree;
    @BindView(R.id.ll_four)
    LinearLayout mLlFour;
    @BindView(R.id.iv_four)
    ImageView mIvFour;
    @BindView(R.id.ll_five)
    LinearLayout mLlFive;
    @BindView(R.id.iv_five)
    ImageView mIvFive;
    @BindView(R.id.ll_six)
    LinearLayout mLlSix;
    @BindView(R.id.iv_six)
    ImageView mIvSix;
    @BindView(R.id.ll_seven)
    LinearLayout mLlSeven;
    @BindView(R.id.iv_seven)
    ImageView mIvSeven;

    SparseArray<String> mStringSparseArray = new SparseArray<>(7);
    MenuItem mMenuItem;

    ZizhiPresenter mZizhiPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shan_chang;
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

        mToolbar.setTitle("擅长领域");
        mZizhiPresenter = new ZizhiPresenter(this);

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("保存");

        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                StringBuilder mStringBuilder = new StringBuilder();

                for (int i = 0, nsize = mStringSparseArray.size(); i < nsize; i++) {
                    String value = mStringSparseArray.valueAt(i);
                    if (i == 0) {
                        mStringBuilder.append(value);
                    } else {
                        mStringBuilder.append("," + value);
                    }

                }

                mZizhiPresenter.updateZizhi("skillname", mStringBuilder.toString());

                return true;
            }
        });
    }


    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four, R.id.ll_five, R.id.ll_six, R.id.ll_seven})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_one:

                if (mIvOne.getVisibility() == View.VISIBLE) {
                    mIvOne.setVisibility(View.GONE);
                    mStringSparseArray.delete(0);

                } else {
                    mStringSparseArray.put(0, "肌肉");
                    mIvOne.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.ll_two:

                if (mIvTwo.getVisibility() == View.VISIBLE) {
                    mIvTwo.setVisibility(View.GONE);
                    mStringSparseArray.delete(1);

                } else {
                    mStringSparseArray.put(1, "减脂");

                    mIvTwo.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.ll_three:

                if (mIvThree.getVisibility() == View.VISIBLE) {
                    mIvThree.setVisibility(View.GONE);
                    mStringSparseArray.delete(2);

                } else {
                    mStringSparseArray.put(2, "塑形");

                    mIvThree.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.ll_four:

                if (mIvFour.getVisibility() == View.VISIBLE) {
                    mIvFour.setVisibility(View.GONE);
                    mStringSparseArray.delete(3);

                } else {
                    mStringSparseArray.put(3, "康复");

                    mIvFour.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.ll_five:

                if (mIvFive.getVisibility() == View.VISIBLE) {
                    mIvFive.setVisibility(View.GONE);
                    mStringSparseArray.delete(4);

                } else {
                    mStringSparseArray.put(4, "拳击");

                    mIvFive.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.ll_six:

                if (mIvSix.getVisibility() == View.VISIBLE) {
                    mIvSix.setVisibility(View.GONE);
                    mStringSparseArray.delete(5);

                } else {
                    mStringSparseArray.put(5, "拉伸放松");

                    mIvSix.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.ll_seven:

                if (mIvSeven.getVisibility() == View.VISIBLE) {
                    mIvSeven.setVisibility(View.GONE);
                    mStringSparseArray.delete(6);

                } else {
                    mStringSparseArray.put(6, "瑜伽");

                    mIvSeven.setVisibility(View.VISIBLE);

                }

                break;
        }
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onFail(String mes) {

    }
}
