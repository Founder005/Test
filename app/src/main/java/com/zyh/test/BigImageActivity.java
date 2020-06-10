package com.zyh.test;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.zyh.toolslibrary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ZhangYuhang
 * @date 2019/8/8
 * @updatelog 共享元素动画
 */
public class BigImageActivity extends BaseActivity {
    @BindView(R.id.iv_big)
    ImageView ivBig;

    @Override
    protected int initContentViewResId() {
        return R.layout.activity_bigimage;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        ivBig.setTransitionName("big");
    }

    @Override
    protected void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
