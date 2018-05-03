package com.example.administrator.js.exercise.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.js.R;
import com.example.administrator.js.base.adapter.BaseLifeCycleView;

/**
 * Created by tangming on 2018/5/3.
 */

public class VipResultView extends BaseLifeCycleView {
    public VipResultView(Context context) {
        super(context);
    }

    public VipResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VipResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_vip_result, this, false);
        addView(mView);
    }
}
