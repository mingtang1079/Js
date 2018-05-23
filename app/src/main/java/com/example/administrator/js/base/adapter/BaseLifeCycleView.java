package com.example.administrator.js.base.adapter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by tangming on 2018/5/3.
 */

public class BaseLifeCycleView extends FrameLayout implements LifecycleObserver {

    public BaseLifeCycleView(Context context) {
        super(context);
        init(context);

    }

    public BaseLifeCycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public BaseLifeCycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    @CallSuper
    public void init(Context mContext) {
        if (mContext instanceof SupportActivity) {
            LifecycleOwner mLifecycleOwnerm = (LifecycleOwner) mContext;
            mLifecycleOwnerm.getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory() {

    }


}