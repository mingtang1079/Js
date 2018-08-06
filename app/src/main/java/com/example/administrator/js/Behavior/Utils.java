package com.example.administrator.js.Behavior;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.ldf.calendar.component.CalendarAttr.WeekArrayType;
import com.ldf.calendar.model.CalendarDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
@SuppressLint("WrongConstant")
public class Utils {

    private static HashMap<String, String> markData = new HashMap();
    private static int top;
    private static boolean customScrollToBottom = false;

    private Utils() {
    }

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            ++year;
        } else if (month < 1) {
            month = 12;
            --year;
        }

        int[] monthDays = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            monthDays[1] = 29;
        }

        try {
            days = monthDays[month - 1];
        } catch (Exception var5) {
            var5.getStackTrace();
        }

        return days;
    }

    @SuppressLint("WrongConstant")
    public static int getYear() {
        return Calendar.getInstance().get(1);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(5);
    }

    public static int getFirstDayWeekPosition(int year, int month, WeekArrayType type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(7) - 1;
        if (type == WeekArrayType.Sunday) {
            return week_index;
        } else {
            week_index = cal.get(7) + 5;
            if (week_index >= 7) {
                week_index -= 7;
            }

            return week_index;
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : "0" + month) + "-01";
        Date date = new Date();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException var5) {
            System.out.println(var5.getMessage());
        }

        return date;
    }

    public static int calculateMonthOffset(int year, int month, CalendarDate currentDate) {
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonth();
        int offset = (year - currentYear) * 12 + (month - currentMonth);
        return offset;
    }

    public static int dpi2px(Context context, float dpi) {
        return (int) (context.getResources().getDisplayMetrics().density * dpi + 0.5F);
    }

    public static HashMap<String, String> loadMarkData() {
        return markData;
    }

    public static void setMarkData(HashMap<String, String> data) {
        markData = data;
    }

    private static int calcOffset(int offset, int min, int max) {
        if (offset > max) {
            return max;
        } else {
            return offset < min ? min : offset;
        }
    }

    public static int scroll(View child, int dy, int minOffset, int maxOffset) {
        int initOffset = child.getTop();
        int offset = calcOffset(initOffset - dy, minOffset, maxOffset) - initOffset;
        child.offsetTopAndBottom(offset);
        return -offset;
    }

    public static int getTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @SuppressLint("WrongConstant")
    public static CalendarDate getSunday(CalendarDate seedDate) {
        Calendar c = Calendar.getInstance();
        String dateString = seedDate.toString();
        Date date = new Date();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
            date = sdf.parse(dateString);
        } catch (ParseException var5) {
            System.out.println(var5.getMessage());
        }

        c.setTime(date);
        if (c.get(7) != 1) {
            c.add(5, 7 - c.get(7) + 1);
        }

        return new CalendarDate(c.get(1), c.get(2) + 1, c.get(5));
    }

    @SuppressLint("WrongConstant")
    public static CalendarDate getSaturday(CalendarDate seedDate) {
        Calendar c = Calendar.getInstance();
        String dateString = seedDate.toString();
        Date date = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
            date = sdf.parse(dateString);
        } catch (ParseException var5) {
            System.out.println(var5.getMessage());
        }

        c.setTime(date);
        c.add(5, 7 - c.get(7));
        return new CalendarDate(c.get(1), c.get(2) + 1, c.get(5));
    }

    public static boolean isScrollToBottom() {
        return customScrollToBottom;
    }

    public static void setScrollToBottom(boolean customScrollToBottom) {
        customScrollToBottom = customScrollToBottom;
    }

    public static void scrollTo(final CoordinatorLayout parent, final NestedScrollView child, int y, int duration) {
        final Scroller scroller = new Scroller(parent.getContext());
        scroller.startScroll(0, top, 0, y - top, duration);
        ViewCompat.postOnAnimation(child, new Runnable() {
            public void run() {
                if (scroller.computeScrollOffset()) {
                    int delta = scroller.getCurrY() - child.getTop();
                    child.offsetTopAndBottom(delta);
                    Utils.top = child.getTop();
                    parent.dispatchDependentViewsChanged(child);
                    ViewCompat.postOnAnimation(child, this);
                }

            }
        });
    }

    public static void saveTop(int y) {
        top = y;
    }

    public static int loadTop() {
        return top;
    }
}
