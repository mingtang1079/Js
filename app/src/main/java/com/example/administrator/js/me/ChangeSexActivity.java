package com.example.administrator.js.me;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.constant.Constants;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.presenter.UserPresenter;

import butterknife.BindView;

/**
 * @author hht
 * @Description: TODO
 * @date 2017/2/8 0008
 */
@Route(path = "/me/ChangeSexActivity")

public class ChangeSexActivity extends BaseActivity implements  UserPresenter.UserResponse {

    @Autowired
    String sex;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ImageView iv_chooseMan, iv_chooseWoman;
    private RelativeLayout rl_chooseman, rl_choosewoman;


    MenuItem mMenuItem;
    UserPresenter mUserPresenter;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("修改性别");

        iv_chooseMan = (ImageView) findViewById(R.id.iv_chooseMan);
        iv_chooseWoman = (ImageView) findViewById(R.id.iv_chooseWoman);
        rl_chooseman = (RelativeLayout) findViewById(R.id.rl_chooseman);
        rl_choosewoman = (RelativeLayout) findViewById(R.id.rl_choosewoman);

        mUserPresenter = new UserPresenter(this);
        if ("1".equals(UserManager.getInsatance().getUser().sex))
            iv_chooseMan.setVisibility(View.VISIBLE);
        else if ("2".equals(UserManager.getInsatance().getUser().sex))
            iv_chooseWoman.setVisibility(View.VISIBLE);

        rl_chooseman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_chooseWoman.setVisibility(View.GONE);
                iv_chooseMan.setVisibility(View.VISIBLE);
                mMenuItem.setEnabled(true);
                sex = "1";

            }
        });

        rl_choosewoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_chooseMan.setVisibility(View.GONE);
                iv_chooseWoman.setVisibility(View.VISIBLE);
                mMenuItem.setEnabled(true);
                sex = "2";

            }
        });
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_change_sex;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }



    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(false);
        mMenuItem.setTitle("确定");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getItemId() == R.id.btn_common) {
                    mUserPresenter.updateUser("sex", sex);

                }
                return true;
            }
        });
    }

    @Override
    public void onSuccess() {
        User mUser = UserManager.getInsatance().getUser();
        mUser.sex = sex;
        PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onFail(String mes) {
        showToast(mes);

    }
}
