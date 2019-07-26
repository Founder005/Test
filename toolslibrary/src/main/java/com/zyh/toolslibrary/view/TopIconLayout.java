package com.zyh.toolslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyh.toolslibrary.R;
import com.zyh.toolslibrary.util.DisplayUtils;


/**
 * Created by ZYH on 2018/7/12.
 * 类描述: 上部图标下部文字
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
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        Log.d("TopIconLayout", "widthSpecSize:" + widthSpecSize);//得到的是xml文件中设置的视图的宽度，输出结果为：widthSpecSize:1080
//        Log.d("TopIconLayout", "widthSpecMode:" + widthSpecMode);//输出结果为widthSpecMode:1073741824，这个view的宽为match_parent
//        Log.d("TopIconLayout", "heightSpecSize:" + heightSpecSize);//得到的是xml文件中设置的视图的高度，输出结果为：heightSpecSize:1776
//        Log.d("TopIconLayout", "heightSpecMode:" + heightSpecMode);//输出结果为heightSpecMode:-2147483648，这个view的高为wrap_content
//        Log.d("TopIconLayout", "MeasureSpec.AT_MOST:" + MeasureSpec.AT_MOST);//MeasureSpec.AT_MOST对应的值为:-2147483648，也就是说wrap_content对应的是MeasureSpec.AT_MOST,最高位是2表示AT_MOST
//        Log.d("TopIconLayout", "MeasureSpec.EXACTLY:" + MeasureSpec.EXACTLY);//MeasureSpec.EXACTLY对应的值为:1073741824也就是说match_parent对应的是MeasureSpec.EXACTLY，最高位是1表示EXACTLY
//        Log.d("TopIconLayout", "MeasureSpec.UNSPECIFIED:" + MeasureSpec.UNSPECIFIED);//MeasureSpec.UNSPECIFIED对应的值为:0，最高位是0表示UNSPECIFIED
//        Log.d("TopIconLayout", "***************************************");
//
//        /*某个博客中的总结：
//        MeasureSpec.EXACTLY：使用measureSpec中size的值作为宽高的精确值
//        当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
//        MeasureSpec.AT_MOST：使用measureSpec中size的值作为最大值，采用不超过这个值的最大允许值
//        当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此
//        ，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
//        MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多*/
//
//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(DisplayUtils.dip2px(getContext(),50), DisplayUtils.dip2px(getContext(),50));//setMeasuredDimension方法是设置测量的范围，也就是视图能在哪个范围内绘制
//        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(DisplayUtils.dip2px(getContext(),50), heightSpecSize);
//        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSpecSize, DisplayUtils.dip2px(getContext(),50));
//        }
//    }
}
