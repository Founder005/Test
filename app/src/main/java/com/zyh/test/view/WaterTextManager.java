package com.zyh.test.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ZhangYuhang
 * @date 2019/8/22
 * @updatelog
 */
public class WaterTextManager {
    public static final int NO_LAYOUT_ID = 0;
    public static int BASE_WATER_LAYOUT_ID = NO_LAYOUT_ID;

    public static WaterTextManager generate(Object activityOrFragment) {
        return new WaterTextManager(activityOrFragment);
    }

    public WaterTextManager(Object activityOrFragmentOrView) {
        ViewGroup contentParent = null;
        Context context;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity: init(context)");
        }

        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        WaterTextLayout waterTextLayout = new WaterTextLayout(context);
        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(waterTextLayout, index, lp);
        waterTextLayout.setContentView(oldContent);

        setupWaterLayout(waterTextLayout);

    }

    private void setupWaterLayout(WaterTextLayout waterTextLayout) {
        if (BASE_WATER_LAYOUT_ID != NO_LAYOUT_ID)
            waterTextLayout.setWaterView(BASE_WATER_LAYOUT_ID);
    }

}
