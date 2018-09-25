package com.example.administrator.js.activity.locaiton

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.appbaselib.app.AppManager
import com.appbaselib.base.BaseActivity
import com.appbaselib.base.Navigator
import com.appbaselib.network.ResponceSubscriber
import com.appbaselib.rx.RxHelper
import com.appbaselib.utils.LogUtils
import com.example.administrator.js.Http
import com.example.administrator.js.LocationManager
import com.example.administrator.js.R
import com.example.administrator.js.UserManager
import com.example.administrator.js.activity.ChatActivity
import com.example.administrator.js.activity.MessageActivity
import com.example.administrator.js.activity.NewNeedActivity
import com.example.administrator.js.activity.SystemMessageActivity
import com.example.administrator.js.constant.EventMessage
import com.example.administrator.js.course.MainCourseFragment
import com.example.administrator.js.me.MeFragment
import com.example.administrator.js.me.member.MeMemberFragment
import com.example.administrator.js.me.model.User
import com.example.administrator.js.vipandtrainer.MainVipFragment
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import kotlinx.android.synthetic.main.activity_main_student.*
import org.greenrobot.eventbus.EventBus
import java.util.*

@Route(path = "/activity/MainActivity")
open class MainActivity : BaseActivity() {

    internal var mNavigator = Navigator(supportFragmentManager, R.id.content)

    lateinit var mMeFragment: Fragment
    var mExerciseFragment = com.example.administrator.js.exercise.ExerciseFragment();
    var mMainVipFragment = MainVipFragment();
    var mMainCourseFragment = MainCourseFragment();

    override fun getToolbar(): Toolbar? {

        return null
    }

    override fun getContentViewLayoutID(): Int {
        return if (UserManager.getInsatance().user != null) {
            if ("0" == UserManager.getInsatance().user.role) {

                R.layout.activity_main_teacher

            } else {
                R.layout.activity_main_student

            }
        } else 0
    }

    override fun getLoadingTargetView(): View? {
        return null;
    }

    override fun initView() {
        upadateLocation()
        initIm()
        if (UserManager.getInsatance().user != null) {
            if ("0" == UserManager.getInsatance().user.role) {

                //        mIvAdd.setVisibility(View.GONE);
                mMeFragment = MeFragment()
            } else {
                iv_add.setOnClickListener(View.OnClickListener { onAddClicked() })
                iv_add.setVisibility(View.VISIBLE)
                mMeFragment = MeMemberFragment()
            }
        }
        bnve.enableItemShiftingMode(false)
        bnve.enableShiftingMode(false)
        bnve.enableAnimation(false)

        mNavigator.showFragment(mExerciseFragment)
        bnve.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.js) {

                mNavigator.showFragment(mExerciseFragment)
            } else if (item.itemId == R.id.vip) {

                mNavigator.showFragment(mMainVipFragment)

            } else if (item.itemId == R.id.course) {

                mNavigator.showFragment(mMainCourseFragment)
            } else {
                mNavigator.showFragment(mMeFragment)
            }

            true
        })
    }

    fun onAddClicked() {

        //   start(NewNeedActivity.class);
        start(NewNeedActivity::class.java)
    }

    private fun initIm() {

        RongIM.setOnReceiveMessageListener(RongIMClient.OnReceiveMessageListener { mMessage, mI ->
            if (AppManager.getInstance().currentActivity is MessageActivity || AppManager.getInstance().currentActivity is ChatActivity ||
                    AppManager.getInstance().currentActivity is SystemMessageActivity) {
                return@OnReceiveMessageListener false
            } else {
                EventBus.getDefault().postSticky(EventMessage.NewMessageReceived(0))
            }
            false
        })

    }

    private fun upadateLocation() {

        val mStringStringMap = HashMap<String, String>()
        mStringStringMap.put("id", UserManager.getInsatance().user.id)
        mStringStringMap.put("longitude", LocationManager.getInsatance().longitude)
        mStringStringMap.put("latitude", LocationManager.getInsatance().latitude)

        Http.getDefault().userEdit(mStringStringMap)
                .`as`(RxHelper.handleResult(mContext))
                .subscribe(object : ResponceSubscriber<User>() {
                    override fun onFail(message: String?) {
                        LogUtils.d("上传用户经纬度失败了")

                    }

                    override fun onSucess(t: User?) {
                        LogUtils.d("成功上传用户经纬度")

                    }
                });


    }
}