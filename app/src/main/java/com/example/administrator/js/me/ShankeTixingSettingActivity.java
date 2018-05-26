package com.example.administrator.js.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShankeTixingSettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_tixing)
    LinearLayout mLlTixing;
    @BindView(R.id.tv_status)
    TextView mTextViewStatus;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shanke_tixing_setting;
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
        mToolbar.setTitle("课程提醒设置");

        String tixing = PreferenceUtils.getPrefString(mContext, Constans.COURSE_TIXING, "上课前提醒");
        mTextViewStatus.setText(tixing);
    }

    @OnClick(R.id.ll_tixing)
    public void onViewClicked() {

        setTime();
    }

    private void setTime() {
        final String[] mItems = new String[4];
        mItems[0] = "上课前提醒";
        mItems[1] = "1小时提醒";
        mItems[2] = "2小时前提醒";
        mItems[3] = "不提醒";

        int position = 0;
        for (int m = 0; m < mItems.length; m++) {
            if (mTextViewStatus.getText().toString().equals(mItems[m])) {
                position = m;
            }
        }

        final AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setSingleChoiceItems(mItems, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {
                mTextViewStatus.setText(mItems[mI]);
                // TODO: 2018/5/26
                PreferenceUtils.setPrefString(mContext, Constans.COURSE_TIXING, mItems[mI]);
                mDialogInterface.dismiss();
            }
        }).show();

    }
}
