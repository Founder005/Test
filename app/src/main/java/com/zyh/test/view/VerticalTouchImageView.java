package com.zyh.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalTouchImageView extends android.support.v7.widget.AppCompatImageView {

    public VerticalTouchImageView(Context context) {
        this(context, null);
    }

    public VerticalTouchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTouchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float moveY;

    /**
    *由于项目需求只能上下滑动，所以我们只关心Y轴。
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录下当前按下的Y坐标
                moveY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //在Move事件里面，拿到现在的Y坐标减去按下时候的坐标，就能计算出当前View应该移动的距离。
                float y = event.getRawY();
                float diffY = y - moveY;
                //如果当前的Y坐标已经移到屏幕外，我们需要修正坐标，最多移动到顶部0坐标。
                if (getY() <= 0) {
                    setY(0);
                } else {//如果没有超出边界，我们就让View移动起来。
                    setY(getTop() + diffY);
                }
                //这里返回true将此次事件消耗，自己处理。
                 return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
    *重置方法
    */
    public void reset() {
        if (getY() <= 0) {
            setY(0);
        }
    }

}