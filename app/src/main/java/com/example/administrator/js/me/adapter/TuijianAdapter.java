package com.example.administrator.js.me.adapter;

import android.text.TextUtils;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.Tuijian;
import com.example.administrator.js.me.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TuijianAdapter extends BaseRecyclerViewAdapter<User> {
    public TuijianAdapter(int layoutResId, List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);
        helper.setText(R.id.tv_id, "ID:"+item.no);
        // TODO: 2018/6/3 time 设置
   //     helper.setText(R.id.tv_time, item.starttime);

        //年龄
//        if (item.age != null &&item.sex!= null) {
//            TextView mTextView = helper.getView(R.id.tv_age);
//            helper.setVisible(R.id.tv_age, true);
//            helper.setText(R.id.tv_age, item.age + "");
//            if (item.sex.equals("1")) {
//                //男性
//                mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));
//
//                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                mTextView.setCompoundDrawables(drawable, null, null, null);
//            } else {
//                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                mTextView.setCompoundDrawables(drawable, null, null, null);
//
//                mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));
//
//            }
//        } else {
//            helper.setVisible(R.id.tv_age, false);
//
//        }
    }
}
