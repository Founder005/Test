package com.zyh.test.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zyh.test.R;
import com.zyh.toolslibrary.base.CommonRecycleAdapter;
import com.zyh.toolslibrary.base.ViewHolder;

import java.util.ArrayList;

/**
 * @author ZhangYuhang
 * @describe
 * @date 2020/6/8
 * @updatelog
 */
public class BottomListDialog extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private ArrayList<String> list = new ArrayList<>();

    public BottomListDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottomsheet, null);
        initViews(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
//        mBehavior.setPeekHeight(200);这句话可以设置初始弹窗的高度
        return dialog;
    }

    private void initViews(View view) {
        for (int i = 0; i < 100; i++) {
            list.add("条目" + i);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_content_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CommonRecycleAdapter adapter = new CommonRecycleAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                holder.setText(android.R.id.text1, s);
            }
        };
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
//    }
}
