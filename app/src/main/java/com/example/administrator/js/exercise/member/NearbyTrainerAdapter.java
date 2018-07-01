package com.example.administrator.js.exercise.member;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.NullUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NearbyTrainerAdapter extends BaseRecyclerViewAdapter<User> {

    public NearbyTrainerAdapter(int layoutResId, List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);
        helper.setText(R.id.tv_skill, item.skillname);
        helper.setText(R.id.tv_time_juli, item.distancefmt);

        //年龄
        if (item.age != null && !TextUtils.isEmpty(item.sex)) {
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

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/vip/VipUserDetailActivity")
                        .withString("id", getData().get(position).id)
                        .navigation(mContext);
            }
        });

        helper.setText(R.id.tv_score, "评分：" + item.score);
        helper.setText(R.id.tv_degree, item.degree);
        helper.setText(R.id.tv_price, "￥ "+ NullUtils.fillNull(item.courseprice));
    }
}
