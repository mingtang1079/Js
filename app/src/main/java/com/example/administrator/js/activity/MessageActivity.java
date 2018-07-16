package com.example.administrator.js.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    public TabLayout mTab;
    public ViewPager mViewpager;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("消息");
        mTab = (TabLayout) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewpager);
        TablayoutUtils.setTabLine(mTab, 50, 50, mContext);
    }


    protected String[] getTabTitle() {

        return new String[]{"私信"};
    }

    ;

    public List<Fragment> getFragments() {

        List<Fragment> m = new ArrayList<>();
        Uri mUri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist").appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false").build();
        ConversationListFragment mMessageFragment = new ConversationListFragment();
        mMessageFragment.setUri(mUri);
        m.add(mMessageFragment);
        return m;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


}
