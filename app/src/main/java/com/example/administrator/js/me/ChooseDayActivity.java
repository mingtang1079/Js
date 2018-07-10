package com.example.administrator.js.me;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.example.administrator.js.R;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.me.adapter.XingqiAdapter;
import com.example.administrator.js.me.model.Xingqi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseDayActivity extends MyBaseRefreshActivity<Xingqi> {

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("选择星期");
        requestData();
    }

    @Override
    public void initAdapter() {
        mAdapter = new XingqiAdapter(R.layout.item_xingqi, mList);

    }

    @Override
    public void requestData() {

        List<Xingqi> mXingqis = new ArrayList<>();
        Xingqi mXingqi1 = new Xingqi("星期一");
        Xingqi mXingqi2 = new Xingqi("星期二");
        Xingqi mXingqi3 = new Xingqi("星期三");
        Xingqi mXingqi4 = new Xingqi("星期四");
        Xingqi mXingqi5 = new Xingqi("星期五");
        Xingqi mXingqi6 = new Xingqi("星期六");
        Xingqi mXingqi7 = new Xingqi("星期日");

        mXingqis.add(mXingqi1);
        mXingqis.add(mXingqi2);
        mXingqis.add(mXingqi3);
        mXingqis.add(mXingqi4);
        mXingqis.add(mXingqi5);
        mXingqis.add(mXingqi6);
        mXingqis.add(mXingqi7);

        loadComplete(mXingqis);

    }


    @Override
    public void finish() {

        Intent mIntent = new Intent();
        mIntent.putExtra("data", (Serializable) ((BaseRecyclerViewAdapter) mAdapter).getSelectedItems());
        setResult(Activity.RESULT_OK, mIntent);

        super.finish();
    }
}
