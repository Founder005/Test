package com.zyh.toolslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyh.toolslibrary.R;


/**
 * Created by ZYH on 2018/7/12.
 * 类描述:
 */

public class TopIconLayout extends RelativeLayout {

    private TextView textView;
    private ImageView imageView;

    public TopIconLayout(Context context) {
        super(context);
        initView(context);
    }

    public TopIconLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopIconLayout);
            String textStr = a.getString(R.styleable.TopIconLayout_bottomText);
            int iconResId = a.getResourceId(R.styleable.TopIconLayout_topIcon, 0);
            if (TextUtils.isEmpty(textStr)) {
                textView.setText("");
            } else {
                textView.setText(textStr);
            }
            imageView.setImageResource(iconResId);
            a.recycle();
        }
    }

    public TopIconLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_topicon, this);
        textView = (TextView) findViewById(R.id.bottom_text);
        imageView = (ImageView) findViewById(R.id.top_icon);
    }
}
