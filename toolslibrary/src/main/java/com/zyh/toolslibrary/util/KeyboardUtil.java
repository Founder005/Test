package com.zyh.toolslibrary.util;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 *  作者：liut_2016
    链接：https://www.jianshu.com/p/5aecf79025a4
 */
public class KeyboardUtil {

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
