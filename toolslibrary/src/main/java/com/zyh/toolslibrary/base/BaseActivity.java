package com.zyh.toolslibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.zyh.toolslibrary.nohttp.CallServer;
import com.zyh.toolslibrary.util.AppManager;
import com.zyh.toolslibrary.util.KeyboardUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/7/16.
 * 类描述：
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(initContentViewResId());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    protected abstract int initContentViewResId();

    protected abstract void initView();

    protected abstract void initData();

    /*ConstantUtil.TEXTVIEWSIZE是设值的一个静态常量，当TEXTVIEWSIZE=1的时候，会显示系统标准字体大小，这个时候即使系统修改了字体大小，也不会影响到应用程序的字体大小。如果想要字体放大，设值其值>1即可。如果想要字体缩小，设值其值<1即可。*/
    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }


    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
//            assert imm != null;//ASSERT就是断言， 如果为false在debug模式下会弹个框，在release下ASSERT里面的内容不会执行
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyboardUtil.isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }
    /**
     * 用来标记取消。
     */
    private Object cancelObject = new Object();

    /**
     * 发起请求。
     *
     * @param what     what.
     * @param request  请求对象。
     * @param callback 回调函数。
     * @param <T>      想请求到的数据类型。
     */
    public <T> void doRequest(int what, Request<T> request, OnResponseListener<T> callback) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);
        CallServer.getInstance().request(what, request, callback);
    }

    @Override
    protected void onDestroy() {
        CallServer.getInstance().cancelBySign(cancelObject);
        super.onDestroy();
        mUnbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }
    /**
     * 记录上次点击按钮的时间
     **/
    private long lastClickTime = 0;
    /**
     * 按钮连续点击最低间隔时间 单位：毫秒
     **/
    public final static int CLICK_TIME = 1200;

    /**
     * 防止快速多次点击
     */
    public boolean fastDoubleClick() {
        if (System.currentTimeMillis() - lastClickTime <= CLICK_TIME) {
            return true;
        }
        lastClickTime = System.currentTimeMillis();
        return false;
    }

}
