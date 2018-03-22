package com.lawrence.ditrp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * This class JUST determines whether we want to intercept the motion.
 * If we return true, onMotionEvent will be called and we do the actual
 * scrolling there.
 */
public class NonSwappableViewPager extends ViewPager {

    private boolean enabled;

    public NonSwappableViewPager(Context context) {
        super(context);
        this.enabled = true;
        setMyScroller();
    }

    public NonSwappableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
        setMyScroller();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        // Never allow swiping to switch between pages
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //down one is added for smooth scrolling
    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }
}
