package com.example.administrator.js.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;

import com.ldf.calendar.view.MonthPager;

public class NestScrollViewBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {

    private int initOffset = -1;
    private int minOffset = -1;
    private Context context;
    private boolean initiated = false;
    boolean hidingTop = false;
    boolean showingTop = false;

    public NestScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public boolean onLayoutChild(CoordinatorLayout parent, NestedScrollView child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        MonthPager monthPager = this.getMonthPager(parent);
        this.initMinOffsetAndInitOffset(parent, child, monthPager);
        return true;
    }

    private void initMinOffsetAndInitOffset(CoordinatorLayout parent, NestedScrollView child, MonthPager monthPager) {
        if (monthPager.getBottom() > 0 && this.initOffset == -1) {
            this.initOffset = monthPager.getViewHeight();
            this.saveTop(this.initOffset);
        }

        if (!this.initiated) {
            this.initOffset = monthPager.getViewHeight();
            this.saveTop(this.initOffset);
            this.initiated = true;
        }

        child.offsetTopAndBottom(Utils.loadTop());
        this.minOffset = this.getMonthPager(parent).getCellHeight();
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, NestedScrollView child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.e("ldf", "onStartNestedScroll");
        MonthPager monthPager = (MonthPager) coordinatorLayout.getChildAt(0);
        monthPager.setScrollable(false);
        boolean isVertical = (nestedScrollAxes & 2) != 0;
        return isVertical;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, NestedScrollView child, View target, int dx, int dy, int[] consumed) {
        Log.e("ldf", "onNestedPreScroll");
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        child.setVerticalScrollBarEnabled(true);
        MonthPager monthPager = (MonthPager) coordinatorLayout.getChildAt(0);
        if (monthPager.getPageScrollState() != 0) {
            consumed[1] = dy;
            Log.w("ldf", "onNestedPreScroll: MonthPager dragging");
            // Toast.makeText(this.context, "loading month data", 0).show();
        } else {
            this.hidingTop = dy > 0 && child.getTop() <= this.initOffset && child.getTop() > this.getMonthPager(coordinatorLayout).getCellHeight();
            this.showingTop = dy < 0 && !ViewCompat.canScrollVertically(target, -1);
            if (this.hidingTop || this.showingTop) {
                consumed[1] = Utils.scroll(child, dy, this.getMonthPager(coordinatorLayout).getCellHeight(), this.getMonthPager(coordinatorLayout).getViewHeight());
                this.saveTop(child.getTop());
            }

        }
    }

    public void onStopNestedScroll(CoordinatorLayout parent, NestedScrollView child, View target) {
        Log.e("ldf", "onStopNestedScroll");
        super.onStopNestedScroll(parent, child, target);
        MonthPager monthPager = (MonthPager) parent.getChildAt(0);
        monthPager.setScrollable(true);
        if (!Utils.isScrollToBottom()) {
            if (this.initOffset - Utils.loadTop() > Utils.getTouchSlop(this.context) && this.hidingTop) {
                Utils.scrollTo(parent, child, this.getMonthPager(parent).getCellHeight(), 500);
            } else {
                Utils.scrollTo(parent, child, this.getMonthPager(parent).getViewHeight(), 150);
            }
        } else if (Utils.loadTop() - this.minOffset > Utils.getTouchSlop(this.context) && this.showingTop) {
            Utils.scrollTo(parent, child, this.getMonthPager(parent).getViewHeight(), 500);
        } else {
            Utils.scrollTo(parent, child, this.getMonthPager(parent).getCellHeight(), 150);
        }

    }

    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, NestedScrollView child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.d("ldf", "onNestedFling: velocityY: " + velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, NestedScrollView child, View target, float velocityX, float velocityY) {
        return this.hidingTop || this.showingTop;
    }

    private MonthPager getMonthPager(CoordinatorLayout coordinatorLayout) {
        return (MonthPager) coordinatorLayout.getChildAt(0);
    }

    private void saveTop(int top) {
        Utils.saveTop(top);
        if (Utils.loadTop() == this.initOffset) {
            Utils.setScrollToBottom(false);
        } else if (Utils.loadTop() == this.minOffset) {
            Utils.setScrollToBottom(true);
        }

    }

}
