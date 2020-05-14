package com.example.carepet.Community;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class NoSrollViewPager extends ViewPager {
    private boolean isCanScroll = false;

    public NoSrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoSrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.isCanScroll = noScroll;

    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
            return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }


}
