package com.example.administrator.js.course.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

public class YuyueCourseActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue_course);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yuyue_course;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {

    }
}
