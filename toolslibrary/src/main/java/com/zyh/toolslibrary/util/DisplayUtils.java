package com.zyh.toolslibrary.util;

/**
 * Created by kxn on 2017/4/27.
 */

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * dp、sp转换为px的工具类
 */

public class DisplayUtils {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸不变
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private static DisplayMetrics dm;

    public static int getScreenWidth(Context context) {
        if (dm == null) {
            dm = context.getResources().getDisplayMetrics();
        }
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (dm == null) {
            dm = context.getResources().getDisplayMetrics();
        }
        return dm.heightPixels;
    }
}
