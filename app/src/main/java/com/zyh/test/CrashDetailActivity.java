package com.zyh.test;

import android.widget.TextView;

import com.zyh.toolslibrary.base.BaseActivity;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/28
 * @updatelog
 */
public class CrashDetailActivity extends BaseActivity {

    @BindView(R.id.tv_crash_info)
    TextView tvCrashInfo;
    private String info;

    @Override
    protected int initContentViewResId() {
        return R.layout.activity_crash_detail;
    }

    @Override
    protected void initView() {
        info = getIntent().getStringExtra("info");
        tvCrashInfo.setText(info);
    }

    @Override
    protected void initData() {

    }
}
