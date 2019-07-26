package com.zyh.toolslibrary.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Locale;

public class StringUtils {

    public static boolean isEmpty(final String s) {
        return null == s || s.trim().length() == 0;
    }

    public static boolean isEqual(final String s1, final String s2) {
        return (s1 == null && s2 == null) || (null != s1 && s1.equals(s2));
    }

    public static String getFileNameFromUrl(final String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return url.replaceAll("[.:/,%?&= ]", "+").replaceAll("[+]+", "+");
    }

    public static String cutLength(final String s, final int len) {
        return (s != null && s.length() >= len) ? s.substring(0, 9) : s;
    }

    public static String replaceBrace(final String s) {
        return (null == s) ? null : s.replace("{", "").replace("}", "");
    }

    public static String replaceBlank(final String s) {
        return (null == s) ? null : s.replaceAll("[\n]", " ");
    }

    public static byte[] getBytes(final String src) {
        if (null == src || src.length() == 0) {
            return null;
        }
        return src.getBytes(StandardCharsets.UTF_8);
    }

    // 将输入流转换成字符串
    public static String parseInStream(final InputStream is) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray());
    }

    /**
     * Convert byte[] to hex string.
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            sb.append(String.format("%02X", Byte.valueOf(src[i])));
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        for (int i = 0; i < ret.length; i++) {
            Integer aInt = Integer.parseInt(src.substring(i * 2, i * 2 + 2), 16);
            ret[i] = aInt.byteValue();
        }
        return ret;
    }


    /**
     * 保留一位小数
     *
     * @param o
     * @return
     */
    public static String strFloatOne(Object o) {
        if (o instanceof Float || o instanceof Double) {
            return String.format(Locale.getDefault(), "%.1f", o);
        }
      else  if (o instanceof String) {
            if (!TextUtils.isEmpty((String)o)) {
                double d = Double.parseDouble((String)o);
                String temp = String.format("%.1f", d);
                return temp;
            } else {
                return "0.0";
            }
        }else{
          return "0.0";
        }
    }

    public static String removeFilter(String str, String filter) {
        return null == str ? null : str.replaceAll(filter, "");

    }

    /**
     * 手机验证格式
     *
     * @param number
     * @return
     */
    public static boolean isMobile(String number) {
        String num = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            return number.matches(num);
        }
    }

    /**
     * 中文姓名
     *
     * @param name
     * @return
     */
    public static boolean isName(String name) {
        return name.matches("[\u4e00-\u9fa5]{2,4}");
    }

    /**
     * 格式化金额*
     *
     * @param money
     * @return
     */
    public static String formMoney(double money) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        return decimalFormat.format(money);
    }

    /**
     * 格式化金额*
     *
     * @param money
     * @return
     */
    public static String formMoney(String money) {
        try {
            double d = Double.parseDouble(money);
            DecimalFormat decimalFormat = new DecimalFormat("###.##");
            return decimalFormat.format(d);
        } catch (Exception e) {
            return "-";
        }

    }

    /**
     * 保留整数
     *
     * @param ratio
     * @return
     */
    public static String str2int(String ratio) {
        if (!TextUtils.isEmpty(ratio)) {
            double d = Double.parseDouble(ratio);
            DecimalFormat decimalFormat = new DecimalFormat("#");
            return decimalFormat.format(d);
        } else {
            return "0";
        }
    }

    /**
     * 从html中提取文本
     * */
    public static String delHTMLTag(String strHtml) {
//        String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
//        txtcontent = txtcontent.replaceAll("&nbsp;", "");
//        txtcontent = txtcontent.replaceAll(" ", "");//去除字符串中的空格,回车,换行符,制表符
//        txtcontent = txtcontent.replace("<p>", "");
//        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        strHtml = strHtml.replaceAll("\\s*|\t|\r|\n", "");
        strHtml = strHtml.replaceAll("<[^>]*>", "");
        strHtml = strHtml.replaceAll("&nbsp;", "");
        return strHtml;
    }

}
