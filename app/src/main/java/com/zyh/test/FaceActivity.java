package com.zyh.test;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.xuexiang.xui.widget.toast.XToast;
import com.yanzhenjie.nohttp.Logger;
import com.zyh.toolslibrary.base.BaseActivity;
import com.zyh.toolslibrary.util.SPUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author ZhangYuhang
 * @date 2019/8/20
 * @updatelog
 */
public class FaceActivity extends BaseActivity {

    private boolean isBackCamera = false;

    private SurfaceView mSurfaceview = null;  // SurfaceView对象：(视图组件)视频显示
    private SurfaceHolder mSurfaceHolder = null;  // SurfaceHolder对象：(抽象接口)SurfaceView支持类
    private Camera mCamera = null;     // Camera对象，相机预览

    @Override
    protected int initContentViewResId() {
        return R.layout.activity_face;
    }

    @Override
    protected void initView() {
        initSurfaceView();
    }

    @Override
    protected void initData() {

    }


    private void initSurfaceView() {
        mSurfaceview = (SurfaceView) this.findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceview.getHolder(); // 绑定SurfaceView，取得SurfaceHolder对象
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.addCallback(CustomCallBack); // SurfaceHolder加入回调接口
        // mSurfaceHolder.setFixedSize(176, 144); // 预览大小設置
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 設置顯示器類型，setType必须设置
    }

    private SurfaceHolder.Callback CustomCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            // SurfaceView启动时/初次实例化，预览界面被创建时，该方法被调用。
            mCamera = Camera.open(1);// 开启摄像头（2.3版本后支持多摄像头,需传入参数）
            try {
                Logger.i("SurfaceHolder.Callback：surface Created");
                mCamera.setPreviewDisplay(mSurfaceHolder);//set the surface to be used for live preview
                myThread = new Thread(new MyThread());
                myThread.start();
            } catch (Exception ex) {
                if (null != mCamera) {
                    mCamera.release();
                    mCamera = null;
                }
                Logger.i("initCamera");
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
// 当SurfaceView/预览界面的格式和大小发生改变时，该方法被调用
            Logger.i("SurfaceHolder.Callback：Surface Changed");
            //mPreviewHeight = height;
            //mPreviewWidth = width;
            initCamera();

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            isPreview = false;
            myThread.interrupt();
            releaseCamera();
        }
    };
    int mPreviewWidth = 0;
    int mPreviewHeight = 0;
    boolean isPreview;

    /**
     * 初始化摄像头
     */
    private void initCamera() {
        if (mCamera != null) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPictureFormat(PixelFormat.JPEG); //Sets the image format for picture 设定相片格式为JPEG，默认为NV21
                parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP); //Sets the image format for preview picture，默认为NV21

                List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();
                List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                List<Integer> previewFormats = mCamera.getParameters().getSupportedPreviewFormats();
                List<Integer> previewFrameRates = mCamera.getParameters().getSupportedPreviewFrameRates();
                Logger.i("cyy support parameters is ");
                Camera.Size psize = null;
                for (int i = 0; i < pictureSizes.size(); i++) {
                    psize = pictureSizes.get(i);
                    Logger.i("PictrueSize,width: " + psize.width + " height" + psize.height);
                }
                for (int i = 0; i < previewSizes.size(); i++) {
                    psize = previewSizes.get(i);
                    Logger.i("PreviewSize,width: " + psize.width + " height" + psize.height);
                }
                Integer pf = null;
                for (int i = 0; i < previewFormats.size(); i++) {
                    pf = previewFormats.get(i);
                    Logger.i("previewformates:" + pf);
                }
                // 【调试】设置后的图片大小和预览大小以及帧率
                Camera.Size csize = mCamera.getParameters().getPreviewSize();
                mPreviewHeight = csize.height; //
                mPreviewWidth = csize.width;
                // 设置拍照和预览图片大小
                parameters.setPictureSize(640, 480); //指定拍照图片的大小
                parameters.setPreviewSize(mPreviewWidth, mPreviewHeight); // 指定preview的大小
                //这两个属性 如果这两个属性设置的和真实手机的不一样时，就会报错

                // 横竖屏镜头自动调整
                if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait"); //
                    parameters.set("rotation", 90); // 镜头角度转90度（默认摄像头是横拍）
                    mCamera.setDisplayOrientation(90); // 在2.2以上可以使用
                } else// 如果是横屏
                {
                    parameters.set("orientation", "landscape"); //
                    mCamera.setDisplayOrientation(0); // 在2.2以上可以使用
                }

                /* 视频流编码处理 */
                //添加对视频流处理函数


                // 设定配置参数并开启预览
                mCamera.setParameters(parameters); // 将Camera.Parameters设定予Camera
                mCamera.startPreview(); // 打开预览画面


                isPreview = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Thread myThread;

    FaceTask mFaceTask = null;

    class MyCallBack implements Camera.PreviewCallback {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (null != mFaceTask) {
                switch (mFaceTask.getStatus()) {
                    case RUNNING:
                        return;
                    case PENDING:
                        mFaceTask.cancel(false);
                        break;
                }
            }
            mFaceTask = new FaceTask(data);
            mFaceTask.execute((Void) null);
        }
    }

    private int num = 0;

    /*自定义的FaceTask类，开启一个线程分析数据*/
    private class FaceTask extends AsyncTask<Void, Void, Void> {

        private byte[] mData;

        //构造函数
        FaceTask(byte[] data) {
            this.mData = data;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Camera.Size size = mCamera.getParameters().getPreviewSize(); //获取预览大小
            final int w = size.width;  //宽度
            final int h = size.height;
            final YuvImage image = new YuvImage(mData, ImageFormat.NV21, w, h, null);
            ByteArrayOutputStream os = new ByteArrayOutputStream(mData.length);
            if (!image.compressToJpeg(new Rect(0, 0, w, h), 100, os)) {
                return null;
            }
            byte[] tmp = os.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
            num++;
            Logger.d(num);
            compare(bmp);   //自己定义的实时分析预览帧视频的算法

            return null;
        }

    }

    class MyThread implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (null != mCamera && isPreview) {
//myCamera.autoFocus(myAutoFocusCallback);
                        mCamera.setOneShotPreviewCallback(new MyCallBack());
                        Logger.i("setOneShotPreview...");
                    }
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void compare(Bitmap bmp) {
        Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(bmp, 100, 100);
        String s2 = SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap2, SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap2))));

        String s1 = SPUtils.getInstance().getString("faceData");
        if (!s1.isEmpty() && s1.equals(s2)) {
//            XToast.normal(FaceActivity.this, "人脸相同");
            Logger.d("人脸相同");
        }else{
            Logger.d("人脸不同或没有源图");
        }
    }


    /**
     * 释放相机
     */
    protected void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.setOneShotPreviewCallback(null);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.lock();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
        }
    }
}

