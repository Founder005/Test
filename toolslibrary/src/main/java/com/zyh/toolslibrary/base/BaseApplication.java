package com.zyh.toolslibrary.base;

import android.app.Application;

import com.xuexiang.xui.BuildConfig;
import com.xuexiang.xui.XUI;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by Administrator on 2019/7/18.
 * 类描述：
 */
public abstract class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this); // NoHttp默认初始化。
        Logger.setDebug(BuildConfig.DEBUG);
        XUI.init(this);
        XUI.debug(BuildConfig.DEBUG);
    }

}
