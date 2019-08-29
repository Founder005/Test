package com.zyh.test.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


/**
 * @author ZhangYuhang
 * @date 2019/8/22
 * @updatelog
 */
public class WaterTextLayout extends FrameLayout {

    private View mWaterView;
    private LayoutInflater mInflater;

    private static final String TAG = WaterTextLayout.class.getSimpleName();


    public WaterTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
    }

    public WaterTextLayout(@NonNull Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    public WaterTextLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
    }

    public void setWaterView(int layoutId) {
        setWaterView(mInflater.inflate(layoutId, this, false));
    }

    public void setContentView(View view){
        addView(view);
    }

    public void setWaterView(View waterView) {
        View contentView = mWaterView;
        if (contentView != null)
        {
            Log.w(TAG, "you have already set a water view and would be instead of this new one.");
        }
        removeView(contentView);
        addView(waterView);
        mWaterView = waterView;
    }

}
