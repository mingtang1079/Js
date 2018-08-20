package com.example.mine.me;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mine.R;
import com.example.mine.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {

    ActivityTestBinding mActivityTestBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mActivityTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        mActivityTestBinding.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

            }
        });
    }
}
