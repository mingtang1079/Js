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

public class ExerciseKnowlegaView extends BaseLifeCycleView {

    public ExerciseKnowlegaView(Context context) {
        super(context);
    }

    public ExerciseKnowlegaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExerciseKnowlegaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView= LayoutInflater.from(getContext()).inflate(R.layout.view_exercise_knowledge,this,false);
        addView(mView);

    }
}
