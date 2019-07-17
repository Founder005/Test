package com.zyh.toolslibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zyh.toolslibrary.R;


/**
 * Created by ZYH on 2018/7/11.
 * 类描述:
 */

public class WarmTipDialog extends Dialog {

    private View line;
    private TextView titleTv; //标题
    private TextView messageTv;//消息
    private Button confirmBtn;//确定按钮
    private Button cancelBtn;//取消按钮
    private String titleStr;//设置的标题
    private String messageStr;//设置的内容
    private String confirmStr;//设置的确定文字
    private String cancelStr;//设置的取消按钮文字

    private OnConfirmClickListener onConfirmClickListener;
    private OnCancelClickListener onCancelClickListener;

    public WarmTipDialog(Context context) {
        super(context, R.style.WarmTipDialog);

    }

    public void setOnConfirmClickListener(String yesStr, OnConfirmClickListener onConfirmClickListener) {
        this.confirmStr = yesStr;
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public void setOnCancelClickListener(String noStr, OnCancelClickListener onCancelClickListener) {
        this.cancelStr = noStr;
        this.onCancelClickListener = onCancelClickListener;
    }

    public WarmTipDialog(Context context, int themeResId) {
        super(context, R.style.WarmTipDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warm_tip);
        //点击空白处不消失
        setCancelable(true);
        //初始化控件
        initView();
        //初始化数据
        initData();
        //设置点击事件
        viewEvent();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
        confirmBtn = (Button) findViewById(R.id.confirm);
        cancelBtn = (Button) findViewById(R.id.cancel);
        line = (View) findViewById(R.id.view_line);
    }

    private void initData() {
        //设置标题和内容
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageTv != null) {
            messageTv.setText(messageStr);
        }
        //设置按钮文字
        if (confirmStr != null) {
            confirmBtn.setText(confirmStr);
        }
        if (cancelStr != null) {
            cancelBtn.setText(cancelStr);
        }
    }

    //设置事件
    private void viewEvent() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onConfirmClickListener != null)
                    onConfirmClickListener.confirmClick();
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelClickListener != null)
                    onCancelClickListener.cancelClick();
                dismiss();
            }
        });
    }

    //设置标题文字
    public void setTitle(String titleStr) {
        this.titleStr = titleStr;
    }

    //设置内容文字
    public void setMessage(String messageStr) {
        this.messageStr = messageStr;
    }

    //设置点击按钮接口
    public interface OnConfirmClickListener {
        void confirmClick();
    }

    public interface OnCancelClickListener {
        void cancelClick();
    }

    @Override
    public void show() {
        super.show();
        if (TextUtils.isEmpty(cancelStr)){
            line.setVisibility(View.GONE);
        }
    }
}
