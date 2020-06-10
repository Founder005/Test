package com.zyh.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yanzhenjie.nohttp.Logger;

/**
 * @author ZhangYuhang
 * @describe
 * @date 2019/9/27
 * @updatelog
 */
public class FloatButton extends View {

    private int lastX;
    private int lastY;
    private int startLeft;
    private int startRight;
    private int startTop;
    private int startBottom;

    public FloatButton(Context context) {
        super(context);
    }

    public FloatButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个小球
        Paint paint = new Paint();
        paint.setStrokeWidth(50);
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(50);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 30, paint);

        Paint paint1 = new Paint();
        paint1.setStrokeWidth(30);
        paint1.setColor(Color.parseColor("#FFFFFF"));
        paint1.setAlpha(70);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 30, paint1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //获取手机触摸的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        Logger.d("xxxxxxxxxxxxx="+x);
        Logger.d("getLeft="+getLeft());
        switch (action) {
            case MotionEvent.ACTION_DOWN://按下,获取小球初始的位置
                startLeft = getLeft();
                startRight = getRight();
                startTop = getTop();
                startBottom = getBottom();
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE://移动,小球跟随手指的移动
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                if (x<=0){
                    layout(0, getTop() + offsetY,
                            getRight() + offsetX, getBottom() + offsetY);
                }else {
                    layout(getLeft() + offsetX, getTop() + offsetY,
                            getRight() + offsetX, getBottom() + offsetY);
                }
                break;
            case MotionEvent.ACTION_UP://当手指抬起时,回到小球初始的位置
//                layout(startLeft, startTop, startRight, startBottom);
                break;
        }
        return true;
    }

}
