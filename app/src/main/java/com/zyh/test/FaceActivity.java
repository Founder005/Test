package com.zyh.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.xuexiang.xui.widget.toast.XToast;
import com.zyh.toolslibrary.base.BaseActivity;
import com.zyh.toolslibrary.util.SPUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author ZhangYuhang
 * @date 2019/8/20
 * @updatelog
 */
public class FaceActivity extends BaseActivity {

    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView surfaceView;
    private boolean isBackCamera = false;
    private int screenWidth, screenHeight;

    @Override
    protected int initContentViewResId() {
        return R.layout.activity_face;
    }

    @Override
    protected void initView() {
        initSurface();
    }

    @Override
    protected void initData() {

    }


    private void initSurface() {

        surfaceView = findViewById(R.id.surface);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.setKeepScreenOn(true);//设置屏幕常亮
//        screenWidth = DisplayUtils.getScreenWidth(this);
//        screenHeight = DisplayUtils.getScreenHeight(this);
        mSurfaceHolder.addCallback(CustomCallBack);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    private SurfaceHolder.Callback CustomCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
//            if (null == mCamera) {
//                try {
//                    mCamera = Camera.open(1);//打开前摄像头
//                    Camera.Parameters mParameters = mCamera.getParameters();
//                    List<Camera.Size> pictureSizes = mParameters.getSupportedPictureSizes();
//                    // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
//                    if (pictureSizes.size() > 1) {
//                        Iterator<Camera.Size> itor = pictureSizes.iterator();
//                        while (itor.hasNext()) {
//                            Camera.Size cur = itor.next();
//                            if (cur.width >= screenWidth
//                                    && cur.height >= screenHeight) {
//                                screenWidth = cur.width;
//                                screenHeight = cur.height;
//                                break;
//                            }
//                        }
//                    }
//                    mParameters.setPictureSize(screenWidth, screenHeight);
//                    mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//持续对焦
//                    mCamera.setParameters(mParameters);
//                    mCamera.setDisplayOrientation(90);
//                    mCamera.setPreviewDisplay(mSurfaceHolder);
//                    mCamera.startPreview();
//                    mCamera.unlock();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
            try {
                initCamera();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            releaseCamera();
        }
    };

    /**
     * 初始化摄像头
     */
    private void initCamera() throws IOException {
        if (mCamera != null) {
            releaseCamera();
        }
        try {
            if (isBackCamera) {
                mCamera = Camera.open(0);//打开后摄像头
            } else {
                mCamera = Camera.open(1);//打开前摄像头

            }

        } catch (Exception e) {
            e.printStackTrace();
            releaseCamera();
        }
        if (mCamera == null)
            return;
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewCallback(new MyCallBack());
        mCamera.setPreviewDisplay(mSurfaceHolder);

        mCamera.startPreview();

        mCamera.unlock();

    }

    class MyCallBack implements Camera.PreviewCallback {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (num < 5) {
//                FaceTask faceTask = new FaceTask()
            }
        }

        private int num = 0;

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

        FaceTask mFaceTask;

        /*自定义的FaceTask类，开启一个线程分析数据*/
        private class FaceTask extends AsyncTask<Void, Void, Void> {

            private byte[] mData;

            //构造函数
            FaceTask(byte[] data) {
                this.mData = data;
            }

            @Override
            protected Void doInBackground(Void... params) {
                // TODO Auto-generated method stub
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
                compare(bmp);   //自己定义的实时分析预览帧视频的算法

                return null;
            }

        }


        private void compare(Bitmap bmp) {
            Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(bmp, 100, 100);
            String s2 = SimilarPicture.binaryString2hexString(SimilarPicture.getBinary(bitmap2, SimilarPicture.getAvg(SimilarPicture.convertGreyImg(bitmap2))));

            String s1 = SPUtils.getInstance().getString("faceData");
            if (!s1.isEmpty() && s1.equals(s2)) {
                XToast.normal(FaceActivity.this, "人脸相同");
            }
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

