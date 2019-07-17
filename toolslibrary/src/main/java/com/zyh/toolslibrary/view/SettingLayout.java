package com.zyh.toolslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyh.toolslibrary.R;


/**
 * Created by ZYH on 2018/7/11.
 * 类描述:
 */

public class SettingLayout extends RelativeLayout {

    private TextView leftTv, rightTv, rightTv2;
    private ImageView intoIv, leftIv;

    private String rightStr, rightStr2, leftStr;

    public SettingLayout(Context context) {
        super(context);
        initView(context);
    }

    public SettingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingLayout);
            String leftStr = a.getString(R.styleable.SettingLayout_leftTxt);
            String rightStr = a.getString(R.styleable.SettingLayout_rightTxt);
            String rightStr2 = a.getString(R.styleable.SettingLayout_rightTxt2);
            Drawable leftDrawable = a.getDrawable(R.styleable.SettingLayout_leftIv);
            int rightStr2Color = a.getColor(R.styleable.SettingLayout_rightTxt2Color, context.getResources().getColor(R.color.text_color_black));
            rightTv2.setTextColor(rightStr2Color);
            int rightStrColor = a.getColor(R.styleable.SettingLayout_rightTxtColor, context.getResources().getColor(R.color.text_color_black));
            rightTv.setTextColor(rightStrColor);
            int leftStrColor = a.getColor(R.styleable.SettingLayout_leftTxtColor, context.getResources().getColor(R.color.text_color_black));
            leftTv.setTextColor(leftStrColor);
            if (null != leftDrawable) {
                leftIv.setImageDrawable(leftDrawable);
            } else {
                leftIv.setVisibility(GONE);
            }
            if (TextUtils.isEmpty(leftStr)) {
                leftTv.setText("");
            } else {
                leftTv.setText(leftStr);
            }
            if (TextUtils.isEmpty(rightStr2)) {
                rightTv2.setText("");
            } else {
                rightTv2.setText(rightStr2);
            }
            if (TextUtils.isEmpty(rightStr)) {
                rightTv.setVisibility(GONE);
            } else {
                rightTv.setText(rightStr);
                intoIv.setVisibility(GONE);
            }
            a.recycle();//销毁
        }
    }

    public SettingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_settinglayout, this);
        leftTv = (TextView) view.findViewById(R.id.left_txt);
        rightTv = (TextView) view.findViewById(R.id.right_txt);
        intoIv = (ImageView) view.findViewById(R.id.into_icon);
        rightTv2 = (TextView) view.findViewById(R.id.right_txt2);
        leftIv = (ImageView) view.findViewById(R.id.left_img);

    }

    public void setRightStr(String rightStr) {
        rightTv.setText(rightStr);
        intoIv.setVisibility(GONE);
    }

    public void setRightStr2(String rightStr2) {
        rightTv2.setText(rightStr2);
    }

    public void setLeftStr(String leftStr) {
        leftTv.setText(leftStr);
    }

    public String getRightStr() {
        return rightTv.getText().toString();
    }
}
