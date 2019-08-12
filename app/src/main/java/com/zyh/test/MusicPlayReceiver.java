package com.zyh.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.util.Locale;

/**
 * @author ZhangYuhang
 * @date 2019/8/5
 * @updatelog
 */
public class MusicPlayReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

//            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
//        MediaPlayer   mediaPlayer = MediaPlayer.create(context, R.raw.test);
//        mediaPlayer.start();}
        Toast.makeText(context,"我接收到了开机广播",Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context,MusicPlayActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);

    }
}
