package com.zyh.test;

import android.content.Intent;

import com.zyh.test.view.LoadingAndRetryManager;
import com.zyh.toolslibrary.base.BaseApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2019/7/18.
 * 类描述：
 */
public class MyApplication extends BaseApplication {

    private static MyApplication instance;

    /**
     * 单一实例
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                Intent intent = new Intent();
                intent.putExtra("info", crashInfo);
                intent.setClass(instance.getApplicationContext(), CrashDetailActivity.class);//错误日志显示
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //设置默认的状态布局
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;

        //第一：默认初始化
        Bmob.initialize(this, "75131b97d7fc4f67b1377af0fdb4e4be");
    }
}
