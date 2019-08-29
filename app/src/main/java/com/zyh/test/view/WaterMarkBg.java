package com.zyh.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zyh.test.R;
import com.zyh.toolslibrary.util.DisplayUtils;

public class WaterMarkBg extends Drawable {
    private Paint mPaint;
    private String mWaterStr;
    private Context mContext;
    private boolean isPadFlavors;

    public WaterMarkBg(@NonNull Context context, @NonNull String waterStr, @NonNull boolean isPadFlavors) {
        this.mContext = context;
        this.mWaterStr = waterStr;
        this.mPaint = new Paint();
        this.isPadFlavors = isPadFlavors;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (TextUtils.isEmpty(mWaterStr)) {
            return;
        }

        int width = getBounds().right;
        int height = getBounds().bottom;

        mPaint.setColor(mContext.getResources().getColor(R.color.paic_watercolor));
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DisplayUtils.dip2px(mContext, 15));
        canvas.save();

        float textWidth = mPaint.measureText(mWaterStr);
        int index = 0;
        int waterGap = height / 7;
        if (isPadFlavors) {
            waterGap = height / 4;
        }
        float hor_gap = textWidth * 2 < 300 ? 300 : textWidth * 2;
        for (int positionY = waterGap; positionY <= height; positionY += waterGap) {
            float fromX = -width + (index++ % 2) * textWidth;
            for (float positionX = fromX; positionX < width; positionX += hor_gap) {//间距固定避免出现，名字过短导致视觉上过于密集
                Path path = new Path();
                path.moveTo(positionX, positionY);
                path.lineTo(positionX + textWidth, positionY - textWidth / 2);//斜角30度

                canvas.drawPath(path, mPaint);
                canvas.drawTextOnPath(mWaterStr, path, 0, 0, mPaint);
            }
        }
        canvas.restore();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    /**
     * 设置水印
     *
     * @param context  context
     * @param rootView rootView
     * @param waterTxt waterTxt
     */
    public static void setWaterBg(@NonNull Context context, @NonNull View rootView, @NonNull String waterTxt, @NonNull boolean isPadFlavors) {
        rootView.setBackground(new WaterMarkBg(context, waterTxt, isPadFlavors));
    }
}