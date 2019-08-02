package com.zyh.test;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.zyh.test.view.LoadingAndRetryManager;
import com.zyh.test.view.OnLoadingAndRetryListener;
import com.zyh.toolslibrary.base.BaseActivity;

/**
 * @author ZhangYuhang
 * @date 2019/7/30
 * @updatelog
 */
public class LoadingStatusActivity extends BaseActivity {
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected int initContentViewResId() {
        return R.layout.activity_status;
    }

    @Override
    protected void initView() {

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {//创建mLoadingAndRetryManager，也可以new出来
            @Override
            public void setRetryEvent(View retryView) {
                LoadingStatusActivity.this.setRetryEvent(retryView);
            }


        });
        mLoadingAndRetryManager.mLoadingAndRetryLayout.setRetryView(R.layout.view_retry);//设置自定义的重试界面，也可以修改原作者的布局
    }

    @Override
    protected void initData() {
        loadData();
    }


    private void loadData()
    {
        mLoadingAndRetryManager.showLoading();

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
//                    mLoadingAndRetryManager.showContent();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                double v = Math.random();
                if (v > 0.8)
                {
                    mLoadingAndRetryManager.showContent();
                } else if (v > 0.4)
                {
                    mLoadingAndRetryManager.showRetry();
                } else
                {
                    mLoadingAndRetryManager.showEmpty();
                }
            }
        }.start();


    }

    /**
     * 设置点击事件
     * */
    public void setRetryEvent(View retryView)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(LoadingStatusActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
    }
}
