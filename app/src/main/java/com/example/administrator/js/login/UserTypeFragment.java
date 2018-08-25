package com.example.administrator.js.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.appbaselib.base.BaseFragment;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.EventMessage;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/4/10.
 */

public class UserTypeFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.fl_xueyuan)
    FrameLayout mFlXueyuan;
    @BindView(R.id.fl_jiaolian)
    FrameLayout mFlJiaolian;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_user_type;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.fl_xueyuan, R.id.fl_jiaolian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                if (mOnbackClickListener instanceof OnbackClickListener)
                    mOnbackClickListener.onBackClick(1);

                break;
            case R.id.fl_xueyuan:
                if (mOnbackClickListener instanceof OnbackClickListener)
                    mOnbackClickListener.onBackClick(2);
                EventBus.getDefault().postSticky(new EventMessage.UsertypeMessage(1));


                break;
            case R.id.fl_jiaolian:
                if (mOnbackClickListener instanceof OnbackClickListener)
                    mOnbackClickListener.onBackClick(2);
                EventBus.getDefault().postSticky(new EventMessage.UsertypeMessage(0));

                break;
        }
    }

    OnbackClickListener mOnbackClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnbackClickListener = (OnbackClickListener) activity;
    }
}
