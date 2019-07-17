//package com.zyh.toolslibrary.base;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.TextView;
//
//import com.gd.terminal.R;
//import com.gd.terminal.bean.ShopDocInfoBean;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2019/4/1.
// * 类描述：
// */
//public class WrapItemAdapter extends AutoWrapAdapter<ShopDocInfoBean.UserdataBean.DgNameBean>{
//
//    private Context mContext;
//
//    public WrapItemAdapter(List<ShopDocInfoBean.UserdataBean.DgNameBean> datas, Context mContext) {
//        super(datas);
//        this.mContext = mContext;
//    }
//
//    @Override
//    public int getItemCount() {
//        return datas.size();
//    }
//
//    @Override
//    public void onBindView(View targetView, final int position) {
//        TextView textView = ((TextView) targetView);
//        textView.setText(datas.get(position).getObject());
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener!=null){
//                    mOnItemClickListener.onClicked(v,position);
//                }
//            }
//        });
//    }
//
//    @Override
//    public View createView(Context context, int position) {
//        TextView textView = new TextView(mContext);
//        textView.setTextColor(mContext.getResources().getColor(R.color.color_777777));
//        textView.setTextSize(14);
//        return textView;
//    }
//
//    private OnItemClickListener mOnItemClickListener;
//
//    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }
//
//    /**
//     * 定制条目点击事件
//     */
//    public interface OnItemClickListener{
//        void onClicked(View view, int position);
//    }
//}
