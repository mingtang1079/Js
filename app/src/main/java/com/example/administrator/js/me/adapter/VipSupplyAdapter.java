package com.example.administrator.js.me.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.VipSupply;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VipSupplyAdapter extends BaseRecyclerViewAdapter<VipSupply> {
    public VipSupplyAdapter(int layoutResId, List<VipSupply> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final VipSupply item) {

        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);

        //年龄
        if (item.age != null || item.sex.equals("0")) {
            TextView mTextView = helper.getView(R.id.tv_age);
            helper.setVisible(R.id.tv_age, true);
            helper.setText(R.id.tv_age, item.age + "");
            if (item.sex.equals("1")) {
                //男性
                mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));

                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTextView.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTextView.setCompoundDrawables(drawable, null, null, null);

                mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));

            }
        } else {
            helper.setVisible(R.id.tv_age, false);

        }

        helper.setText(R.id.tv_course, item.ctypename + item.csum);
        helper.setText(R.id.time, item.distance + " " + item.updateDate);
//a1已提交b2通过b3完成付款,b4不通过
        TextView mTextViewPass = helper.getView(R.id.pass);
        TextView mTextViewRefuse = helper.getView(R.id.jujue);

        if ("b1".equals(item.status)) {
            mTextViewPass.setVisibility(View.VISIBLE);
            mTextViewRefuse.setVisibility(View.VISIBLE);
            mTextViewRefuse.setEnabled(true);
        } else if ("b4".equals(item.status)) {
            mTextViewPass.setVisibility(View.GONE);
            mTextViewRefuse.setVisibility(View.VISIBLE);
            mTextViewRefuse.setEnabled(false);
            mTextViewRefuse.setText("已拒绝");

        } else if ("b2".equals(item.status)) {
            mTextViewPass.setVisibility(View.GONE);
            mTextViewRefuse.setVisibility(View.VISIBLE);
            mTextViewRefuse.setEnabled(false);
            mTextViewRefuse.setText("已通过");

        }

        mTextViewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                save(true, item.id, helper.getLayoutPosition());
            }
        });
        mTextViewRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                save(false,item.id,helper.getLayoutPosition());
            }
        });

    }

    private void save(boolean mB, String id, final int p) {

        Http.getDefault().passOrRefuse(id, UserManager.getInsatance().getUser().id, mB ? "b2" : "b4")
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {
                        remove(p);
                    }

                    @Override
                    protected void onFail(String message) {
                        ToastUtils.showShort(mContext, message);
                    }
                });
    }
}