package com.zyh.test;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.xuexiang.xui.widget.statelayout.MultipleStatusView;
import com.zyh.toolslibrary.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/18.
 * 类描述：
 */
public class StatusActivity extends BaseActivity {


    @BindView(R.id.statefulLayout)
    MultipleStatusView statefulLayout;

    @Override
    public int initContentViewResId() {
        return R.layout.activity_status;
    }

    @Override
    protected void initView() {
        statefulLayout.showLoading();
                ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(StatusActivity.this,
                        R.animator.color_animator);
                animator.setTarget(findView(R.id.text));
                animator.start();

        ValueAnimator animator1 =  ValueAnimator.ofInt(0xffffff00,0xff0000ff);

        animator1.setEvaluator(new ArgbEvaluator());
        animator1.setDuration(3000);
        animator1.setRepeatCount(30);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                findView(R.id.text2).setBackgroundColor(curValue);

            }
        });

        animator1.start();
    }
    ImageView smallIv;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statefulLayout.showContent();
            }
        }, 3000);

        findView(R.id.data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MusicPlayActivity.class);
            }
        });


         smallIv  = findView(R.id.iv_small);
        smallIv.setTransitionName("big");//共享元素动画 5.0以上
        smallIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat compat  = ActivityOptionsCompat.makeSceneTransitionAnimation(StatusActivity.this,smallIv,"big");
                Intent intent  = new Intent(StatusActivity.this,BigImageActivity.class);
                startActivity(intent,compat.toBundle());
            }
        });

    }


}
