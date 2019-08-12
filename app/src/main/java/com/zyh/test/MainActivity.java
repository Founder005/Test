package com.zyh.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.toast.XToast;
import com.zyh.test.bean.User;
import com.zyh.toolslibrary.nohttp.LoadingDialog;
import com.zyh.toolslibrary.util.StringUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       final LoadingDialog dialog = new LoadingDialog(MainActivity.this);
//        mTextMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//                User p2 = new User();
//                p2.setName("lucky");
//                p2.setAddress("北京海淀");
//                p2.save(new SaveListener<String>() {
//                    @Override
//                    public void done(String objectId, BmobException e) {
//                        dialog.dismiss();
//                        if(e==null){
//                            XToast.normal(MainActivity.this,"添加数据成功，返回objectId为："+objectId).show();
//                        }else{
//                            XToast.normal(MainActivity.this,"创建数据失败：" + e.getMessage()).show();
//                        }
//                    }
//                });
//            }
//        });
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap1 = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(),R.drawable.app_launcher), 8, 8);
                Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(),R.drawable.note), 8, 8);
                String s1= SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap1,SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap1))));
                String s2= SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap2,SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap2))));
                XToast.normal(MainActivity.this, SimilarPicture.similar(s1,s2)+"").show();

            }
        });

    }

}
