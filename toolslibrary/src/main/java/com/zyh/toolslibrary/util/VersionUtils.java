package com.zyh.toolslibrary.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 系统版本检测
 * Created by tx on 2017/3/30.
 */

public class VersionUtils {

    public static boolean isLargeVersion6() {
        String RELEASE = android.os.Build.VERSION.RELEASE.subSequence(0, 1) + "";
        if (Integer.valueOf(RELEASE) > 6) {
            return true;
        }
        return false;
    }


    public static boolean isLargeVersion5() {
        String RELEASE = android.os.Build.VERSION.RELEASE.subSequence(0, 1) + "";
        if (Integer.valueOf(RELEASE) > 5) {
            return true;
        }
        return false;
    }

    /**
     * 当前版本 code
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        if (mContext != null) {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException ignored) {
                ignored.printStackTrace();
            }
        }
        return 0;
    }
    /**
     * 当前版本 name
     * @param mContext
     * @return
     */
    public static String getVersionName(Context mContext) {
        if (mContext != null) {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException ignored) {
                ignored.printStackTrace();
            }
        }
        return "";
    }

}
