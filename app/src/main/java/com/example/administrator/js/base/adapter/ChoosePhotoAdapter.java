package com.example.administrator.js.base.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.appbaselib.widget.SquareImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.example.administrator.js.R;

/**
 * Created by tangming on 2017/6/22.
 */

public class ChoosePhotoAdapter extends BaseRecyclerViewAdapter<String> {

    ImageView mImageViewAdd;//选择图片的控件
    public View header;
    private ImageView mImageView;
    boolean isShow;
    onHeaderOnClickListener mOnHeaderOnClickListener;

    public ChoosePhotoAdapter(int layoutResId, List<String> data, Context mContext, boolean isShowDeleteButton) {
        super(layoutResId, data);
        isShow = isShowDeleteButton;
        if (mContext instanceof onHeaderOnClickListener) {
            mOnHeaderOnClickListener = (onHeaderOnClickListener) mContext;
        }
        header = ((AppCompatActivity) mContext).getLayoutInflater().inflate(R.layout.layout_add_photo, null);
        mImageView = (ImageView) header.findViewById(R.id.iv_choose_image);
        addFooterView(header);
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                handleImages();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                handleImages();
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (mOnHeaderOnClickListener != null)
                    mOnHeaderOnClickListener.onHeaderClick();
            }
        });
    }

    private void handleImages() {
        if (getData().size() == 9) {
            if (getFooterViewsCount() != 0)
                removeFooterView(header);
        } else {
            if (getData().size() == 0)
                mImageView.setImageResource(R.drawable.icon_camera);
            else
                mImageView.setImageResource(R.drawable.icon_choose_image_add);
            if (getFooterViewsCount() == 0) {
                addFooterView(header);
            }
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {


        SquareImageView mImageView = helper.getView(R.id.imageview);
        if (isShow)
            helper.setVisible(R.id.delele, true);
        else
            helper.setVisible(R.id.delele, false);
        Glide.with(mContext)
                .load(item)
                .crossFade()
                .into(mImageView);
        helper.getView(R.id.delele).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePhotoAdapter.this.remove(getData().indexOf(item));
            }
        });

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    }

    public interface onHeaderOnClickListener {
        void onHeaderClick();
    }


}
