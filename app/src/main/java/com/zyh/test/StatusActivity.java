package com.zyh.test;

import android.os.Handler;

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
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statefulLayout.showContent();
            }
        }, 3000);
    }


}
