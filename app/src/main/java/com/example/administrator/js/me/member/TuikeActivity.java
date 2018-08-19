package com.example.administrator.js.me.member;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.BottomDialogUtils;
import com.appbaselib.utils.DateUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.exercise.model.Skill;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

@Route(path = "/member/TuikeActivity")
public class TuikeActivity extends BaseActivity {

    @Autowired
    MyOrder mMyOrder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.ll_reason)
    LinearLayout mLlReason;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.et_reason)
    EditText mEtReason;
    @BindView(R.id.btn_sure)
    Button mBtnSure;
    private List<String> mItems;
    private String refundtype;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tuike;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("申请退款");
        setData(mMyOrder);
        requestData();
        mLlReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                showType();
            }
        });

        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mTvReason);
        Observable<CharSequence> mCharSequenceObservablePassword = RxTextView.textChanges(mEtReason);
        Observable.combineLatest(mObservablePhone, mCharSequenceObservablePassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2) throws Exception {
                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mBtnSure.setEnabled(mBoolean);
            }
        });
    }

    @Override
    protected void requestData() {
        super.requestData();
        Http.getDefault().gettuikereasons(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<List<String>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<List<String>>() {
                    @Override
                    protected void onSucess(List<String> mStrings) {
                        mItems = mStrings;
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });
    }

    private void setData(MyOrder mOrder) {
        ImageLoader.load(mContext, mOrder.img, mIvHead);
        mTvName.setText(mOrder.nickname);
        mTvId.setText("ID" + mOrder.no + "");
        //年龄
        if (mOrder.age != null && mOrder.sex != null) {
            mTvAge.setText(" "+mOrder.age);
            if (mOrder.sex.equals("1")) {
                //男性
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));

            }
        } else {
            mTvAge.setVisibility(View.GONE);

        }

        BigDecimal mBigDecimal=new BigDecimal(mOrder.refundmoney);
        mTvPrice.setText(mBigDecimal.divide(new BigDecimal(100)).doubleValue()+"元");

    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {


        cancel("b55");
    }

    private void cancel(final String status) {

        final Map<String, Object> mMap = new HashMap<>();
        mMap.put("uid", UserManager.getInsatance().getUser().id);
        mMap.put("id", mMyOrder.id);
        mMap.put("status", status);
        mMap.put("refundtype", refundtype);
        if (!TextUtils.isEmpty(mEtReason.getText().toString()))
            mMap.put("refunddetail", mEtReason.getText().toString());

        Http.getDefault().applyYuyueke(mMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        mMyOrder.tuikeTime = DateUtils.getCurrentTimeInString();
                        mMyOrder.refundtype = refundtype;
                        mMyOrder.refunddetail = mEtReason.getText().toString();

                        ARouter.getInstance().build("/member/TuikeDetailActivity")
                                .withBoolean("isFirst", true)
                                .withObject("mMyOrder", mMyOrder)
                                .navigation(mContext);
                        finish();
                        EventBus.getDefault().post(new EventMessage.ListStatusChange());
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    private void showType() {


        if (mItems == null)
            return;
        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                refundtype = mItems.get(position);
                mTvReason.setText(refundtype);
            }
        }).show();

    }
}
