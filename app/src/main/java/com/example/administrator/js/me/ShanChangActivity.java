package com.example.administrator.js.me;

import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.presenter.ZizhiPresenter;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/me/ShanChangActivity")
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
    @BindView(R.id.ll_eight)
    LinearLayout mLlEight;
    @BindView(R.id.iv_eight)
    ImageView mIvEight;
    @BindView(R.id.ll_nine)
    LinearLayout mLlNine;
    @BindView(R.id.iv_nine)
    ImageView mIvNine;

    SparseArray<String> mStringSparseArray = new SparseArray<>(7);
    MenuItem mMenuItem;

    @Autowired
    VerifyUser mVerifyUser;

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
        if (mVerifyUser != null && mVerifyUser.skillname != null) {
            String[] mStrings = mVerifyUser.skillname.split(",");
            for (String mS : mStrings) {
                if (mS.equals("增肌")) {
                    mStringSparseArray.put(0, "增肌");
                    mIvOne.setVisibility(View.VISIBLE);
                } else if (mS.equals("减脂")) {
                    mStringSparseArray.put(1, "减脂");
                    mIvTwo.setVisibility(View.VISIBLE);
                } else if (mS.equals("塑形")) {
                    mStringSparseArray.put(2, "塑形");
                    mIvThree.setVisibility(View.VISIBLE);
                } else if (mS.equals("康复")) {
                    mStringSparseArray.put(3, "康复");
                    mIvFour.setVisibility(View.VISIBLE);
                } else if (mS.equals("体态纠正")) {
                    mStringSparseArray.put(4, "体态纠正");
                    mIvFive.setVisibility(View.VISIBLE);
                } else if (mS.equals("拉伸放松")) {
                    mStringSparseArray.put(5, "拉伸放松");
                    mIvSix.setVisibility(View.VISIBLE);
                } else if (mS.equals("拳击")) {
                    mStringSparseArray.put(6, "拳击");
                    mIvSeven.setVisibility(View.VISIBLE);
                }else if (mS.equals("功能性训练")) {
                    mStringSparseArray.put(7, "功能性训练");
                    mIvEight.setVisibility(View.VISIBLE);
                } else if (mS.equals("竞技健美")) {
                    mStringSparseArray.put(8, "竞技健美");
                    mIvNine.setVisibility(View.VISIBLE);
                }
            }

        }


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


    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four, R.id.ll_five, R.id.ll_six, R.id.ll_seven,R.id.ll_eight,R.id.ll_nine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_one:

                if (mIvOne.getVisibility() == View.VISIBLE) {
                    mIvOne.setVisibility(View.GONE);
                    mStringSparseArray.delete(0);

                } else {
                    mStringSparseArray.put(0, "增肌");
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
                    mStringSparseArray.put(4, "体态纠正");

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
                    mStringSparseArray.put(6, "拳击");

                    mIvSeven.setVisibility(View.VISIBLE);

                }
                break;

            case  R.id.ll_eight:
                if (mIvEight.getVisibility() == View.VISIBLE) {
                    mIvEight.setVisibility(View.GONE);
                    mStringSparseArray.delete(7);

                } else {
                    mStringSparseArray.put(7, "功能性训练");

                    mIvEight.setVisibility(View.VISIBLE);

                }
                break;
            case  R.id.ll_nine:
                if (mIvNine.getVisibility() == View.VISIBLE) {
                    mIvNine.setVisibility(View.GONE);
                    mStringSparseArray.delete(8);

                } else {
                    mStringSparseArray.put(8, "竞技健美");

                    mIvNine.setVisibility(View.VISIBLE);

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
