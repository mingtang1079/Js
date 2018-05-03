package com.example.administrator.js.exercise.view;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.administrator.js.R;
import com.example.administrator.js.base.adapter.BaseLifeCycleView;

/**
 * Created by tangming on 2018/5/3.
 */

public class MainHeaderView extends BaseLifeCycleView {

    public MainHeaderView(Context context) {
        super(context);
    }

    public MainHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView= LayoutInflater.from(getContext()).inflate(R.layout.view_main_header,this,false);
        addView(mView);
    }

}
