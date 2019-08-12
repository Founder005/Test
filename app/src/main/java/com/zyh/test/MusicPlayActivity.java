package com.zyh.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zyh.toolslibrary.base.BaseActivity;

import java.io.IOException;
import java.security.Permission;
import java.security.Permissions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ZhangYuhang
 * @date 2019/8/6
 * @updatelog
 */
public class MusicPlayActivity extends BaseActivity {
    @BindView(R.id.play)
    TextView play;
    @BindView(R.id.stop)
    TextView stop;
    int REQUEST_CODE_CONTACT = 101;
    private MediaPlayer mediaPlayer;

    @Override
    protected int initContentViewResId() {
        return R.layout.activity_play_music;
    }

    @Override
    protected void initView() {
        mediaPlayer = new MediaPlayer();
        if (Build.VERSION.SDK_INT >= 23) {

            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {

                }
            }
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(MusicPlayActivity.this, Uri.parse(Environment.getExternalStorageDirectory() + "/test.mp3"));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                mediaPlayer.setDataSource(this, Uri.parse(Environment.getExternalStorageDirectory() + "/test.mp3"));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
