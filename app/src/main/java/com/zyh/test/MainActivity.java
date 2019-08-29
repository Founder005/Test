package com.zyh.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuexiang.xui.widget.toast.XToast;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.zyh.test.view.WaterMarkBg;
import com.zyh.test.view.WaterTextManager;
import com.zyh.toolslibrary.nohttp.LoadingDialog;
import com.zyh.toolslibrary.util.SPUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ImageView faceIv;

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
        faceIv = findViewById(R.id.iv_face);
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
                Bitmap bitmap1 = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.app_launcher), 8, 8);
                Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.note), 8, 8);
                String s1 = SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap1, SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap1))));
                String s2 = SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap2, SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap2))));
                XToast.normal(MainActivity.this, SimilarPicture.similar(s1, s2) + "").show();

            }
        });

        findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FaceActivity.class));
            }
        });
        findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album.album(MainActivity.this).singleChoice().onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        Glide.with(MainActivity.this).load(result.get(0).getPath()).into(faceIv);
                        saveFaceData(result.get(0).getPath());
                    }
                }).start();
            }
        });
//        WaterTextManager.generate(this);//水印第一种
    }

    private void saveFaceData(String path){
        Bitmap bitmap1 = ThumbnailUtils.extractThumbnail( BitmapFactory.decodeFile(path), 100, 100);
        String s1 = SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap1, SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap1))));
        SPUtils.getInstance().put("faceData",s1);
    }
    /*水印第二种*/
    private boolean isAdd ;
    @Override
    protected void onStart() {
        //判断是否已经添加过
        if(isAdd){
        }else {
            ViewGroup rootView = getRootView(this);
            View framView = LayoutInflater.from(this).inflate(R.layout.view_waterview, null);
            WaterMarkBg markBg = new WaterMarkBg(this,"HELLO",false);
            framView.setBackground(markBg);
            rootView.addView(framView);
            isAdd=true;
        }
        super.onStart();

    }
    //查找布局的底层
    protected static ViewGroup getRootView(Activity context)
    {
        return (ViewGroup)context.findViewById(android.R.id.content);
    }
}
