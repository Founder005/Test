package com.zyh.test;

import android.graphics.Bitmap;

/**
 * Created by Wang on 2016/5/31.
 * 第一步，缩小尺寸。Bitmap bitmap8 = ThumbnailUtils.extractThumbnail(bitmapOriginal, 8, 8);
 */
public class SimilarPicture {

//    public static void main(String args[]) {
//
//    }

    /**
     * 将彩色图转换为灰度图
     *  第二步，简化色彩。
     * @param img 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int original = pixels[width * i + j];
//                System.out.println("pixels[" + (width * i + j) + "]原色=" + original);

//                if (original == 0) { // 透明转为白色，不需要，因为求平均值后再转换会变黑
//                    pixels[width * i + j] = -1;
//                } else {
                int red = ((original & 0x00FF0000) >> 16);
                int green = ((original & 0x0000FF00) >> 8);
                int blue = (original & 0x000000FF);

                int grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
//                    System.out.println("pixels[" + (width * i + j) + "]grey=" + grey);
//                }

            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 第三步，计算平均值。
     * @param img
     * @return
     */
    public static int getAvg(Bitmap img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];
        img.getPixels(pixels, 0, width, 0, 0, width, height);

        int avgPixel = 0;
        for (int pixel : pixels) {
            avgPixel += pixel;
        }
        return avgPixel / pixels.length;
    }

    /**
     * 第四步，比较像素的灰度。大于或等于平均值，记为1；小于平均值，记为0。
     * @param img
     * @param average
     * @return
     */
    public static String getBinary(Bitmap img, int average) {//第三步
        StringBuilder sb = new StringBuilder();

        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int original = pixels[width * i + j];
                if (original >= average) {
                    pixels[width * i + j] = 1;
                } else {
                    pixels[width * i + j] = 0;
                }
                sb.append(pixels[width * i + j]);
            }
        }
        return sb.toString();
    }

    /**
     * 第五步，计算哈希值。
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuilder sb = new StringBuilder();
        int iTmp;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            sb.append(Integer.toHexString(iTmp));
        }
        return sb.toString();
    }

    public static Bitmap convertWABImg(Bitmap img, int average) {
        System.out.println("平均值=" + average);
        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int original = pixels[width * i + j];
                if (original >= average) {
                    pixels[width * i + j] = -16777216;
                } else {
                    pixels[width * i + j] = -1;
                }
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }
//    static void diff(String s1, String s2) {
//        char[] s1s = s1.toCharArray();
//        char[] s2s = s2.toCharArray();
//        int diffNum = 0;
//        for (int i = 0; i<s1s.length; i++) {
//            if (s1s[i] != s2s[i]) {
//                diffNum++;
//            }
//        }
//        System.out.println("diffNum="+diffNum);
//    }

    public static boolean similar(String s1, String s2){
        char[] s1s = s1.toCharArray();
        char[] s2s = s2.toCharArray();
        int diffNum = 0;
        for (int i = 0; i<s1s.length; i++) {
            if (s1s[i] != s2s[i]) {
                diffNum++;
            }
        }
        if (diffNum<=5){
            return true;
        }
        return false;
    }
}