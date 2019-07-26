package com.zyh.test;

import android.content.Intent;

import com.zyh.toolslibrary.base.BaseApplication;

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
    }
}
