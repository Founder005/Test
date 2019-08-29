package com.zyh.test.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyh.test.R;

/**
 * @author ZhangYuhang
 * @date 2019/8/22
 * @updatelog
 */
public class WaterTextManager2 {

    public static WaterTextManager2 generate(Object activityOrFragment) {
        return new WaterTextManager2(activityOrFragment);
    }

    public WaterTextManager2(Object activityOrFragmentOrView) {
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
//        contentParent.removeView(oldContent);
//        //setup content layout
//        WaterTextLayout loadingAndRetryLayout = new WaterTextLayout(context);
//        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
//        contentParent.addView(loadingAndRetryLayout, index, lp);
//        loadingAndRetryLayout.setContentView(oldContent);
        View mWaterView =  LayoutInflater.from(context).inflate(R.layout.base_waterview,contentParent,false);
        contentParent.addView(mWaterView);

    }


}


