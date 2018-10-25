package com.example.administrator.js.login

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.appbaselib.base.BaseFragment
import com.appbaselib.base.BaseModel
import com.appbaselib.network.ResponceSubscriber
import com.appbaselib.rx.RxHelper
import com.appbaselib.utils.ToastUtils
import com.example.administrator.js.App
import com.example.administrator.js.BuildConfig
import com.example.administrator.js.Http
import com.example.administrator.js.R
import com.example.administrator.js.constant.Constans
import com.example.administrator.js.me.ForgetPasswordActivity
import com.example.administrator.js.me.model.User
import com.jakewharton.rxbinding2.widget.RxTextView
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragmentKt : BaseFragment() {


    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {

        if (BuildConfig.DEBUG) {
            tv_phone.setText("18380224875")
            password.setText("qqqq1111")
        }
        val mObservablePhone = RxTextView.textChanges(tv_phone)
        val mCharSequenceObservablePassword = RxTextView.textChanges(password)
        Observable.combineLatest(mObservablePhone, mCharSequenceObservablePassword,
                BiFunction<CharSequence, CharSequence, Boolean> { mCharSequence, mCharSequence2 ->
                    !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2)
                })
                .subscribe { mBoolean -> bt_login.setEnabled(mBoolean!!) }

        App.mInstance.api.registerApp(Constans.weixin)
        bt_login.setOnClickListener {
            onViewClicked(bt_login)
        }
        bt_register.setOnClickListener {
            onViewClicked(bt_register)
        }
        iv_weixin.setOnClickListener {
            onViewClicked(iv_weixin)
        }
        tv_wjmm.setOnClickListener {
            onViewClicked(tv_wjmm)
        }
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.bt_login ->

                login()
            R.id.bt_register ->

                if (mOnbackClickListener is OnbackClickListener)
                    mOnbackClickListener.onBackClick(3)
            R.id.iv_weixin -> if (!App.mInstance.api.isWXAppInstalled) {
                showToast("请安装微信")
            } else {
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "wechat_sdk_demo_test"
                App.mInstance.api.sendReq(req)
            }

            R.id.tv_wjmm ->

                start(ForgetPasswordActivity::class.java)
        }
    }

    private fun login() {
        bt_login.setEnabled(false)
        bt_login.setText("登录中")
        Http.getDefault().login(tv_phone.getText().toString(), password.getText().toString())
                .`as`(RxHelper.handleResult(mContext))
                .subscribe(object : ResponceSubscriber<User>() {
                    override fun onSucess(mUser: User) {

                        if (mOnUserGetListener != null) {
                            mOnUserGetListener.onUserGet(mUser)
                        }

                    }

                    override fun onFail(message: String) {
                        ToastUtils.showShort(App.mInstance, message)
                        bt_login.setEnabled(true)
                        bt_login.setText("登录")
                    }


                })

    }

    internal lateinit var mOnbackClickListener: OnbackClickListener
    internal lateinit var mOnUserGetListener: OnUserGetListener
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mOnbackClickListener = activity as OnbackClickListener
        mOnUserGetListener = activity as OnUserGetListener
    }
}