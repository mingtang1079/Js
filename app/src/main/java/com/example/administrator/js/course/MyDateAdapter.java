package com.example.administrator.js.course;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.TextView;

import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.administrator.js.R;
import com.example.administrator.js.course.model.CourseItem;
import com.example.administrator.js.course.model.Item;
import com.example.administrator.js.course.model.TimeItem;
import com.example.administrator.js.course.model.ViewItem;
import com.example.administrator.js.utils.TimeUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyDateAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {

    public MyDateAdapter() {
        super(null);
        //Step.1
        setMultiTypeDelegate(new MultiTypeDelegate<Item>() {
            @Override
            protected int getItemType(Item entity) {
                //根据你的实体类来判断布局类型
                if (entity instanceof ViewItem)
                    return 1;
                else if (entity instanceof TimeItem)
                    return 2;
                else return 3;
            }
        });
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(1, R.layout.item_course_one)
                .registerItemType(2, R.layout.item_course_two)
                .registerItemType(3, R.layout.item_course_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item entity) {
        //Step.3
        switch (helper.getItemViewType()) {
            case 1:
                // do something
                ViewItem mViewItem = (ViewItem) entity;
                helper.setText(R.id.name, mViewItem.name);
                break;
            case 2:
                // do something
                TimeItem mTimeItem = (TimeItem) entity;
                helper.setText(R.id.tv_name, mTimeItem.name);
                break;
            case 3:


                CourseModel item = (CourseModel) entity;

                setData(item, helper);

                break;
        }
    }

    private void setData(CourseModel item, BaseViewHolder helper) {

        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);
        helper.setText(R.id.tv_skill, item.ctypename + "(" + item.coursetypenames + ")");
        helper.setText(R.id.tv_time_juli, TimeUtils.getTime(item.starttime));
        //   helper.setText(R.id.tv_time_juli, item.starttime);
        //年龄
        if (item.sex != null) {
            TextView mTextView = helper.getView(R.id.tv_age);
            helper.setVisible(R.id.tv_age, true);
            if (item.age != null) {
                helper.setText(R.id.tv_age, " "+item.age );
            }
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
    }
}
