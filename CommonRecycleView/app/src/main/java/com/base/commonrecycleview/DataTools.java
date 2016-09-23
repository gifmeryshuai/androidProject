package com.base.commonrecycleview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 东帅 on 2016/9/23.
 */
public class DataTools {

    private static final String TAG = "DataTools.class";

    public static List<AppInfo> getInstallApp(Context context) {

        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {

            PackageManager manager = context.getPackageManager();

            if (manager != null) {
                List<PackageInfo> packageInfo = manager.getInstalledPackages(0);
//                Log.e(TAG, "包数量:"+packageInfo.size());
                if (packageInfo != null && packageInfo.size() >0 ){

                    for (PackageInfo infos:packageInfo) {

                        AppInfo info = new AppInfo();
//                        Log.e(TAG, "包名称:"+infos.packageName);
                        info.appPackName = infos.packageName;
                        info.appName = getAppName(infos.packageName, manager);
                        info.appIcon = getAppDrawable(infos.packageName, manager);

                        appInfos.add(info);
                    }
                }
            }

        }catch (Exception e) {
            Log.e(TAG, "getInstallApp - "+e.getMessage());
        }

        return appInfos;
    }

    /**
     * 获取应用图标
     * @param packageName
     * @param manager
     * @return
     */
    private static Drawable getAppDrawable(String packageName, PackageManager manager) {

        Drawable drawable = null;
        try {

            drawable = manager.getApplicationIcon(packageName);

        }catch (Exception e) {
            Log.e(TAG, "getAppDrawable  - "+e.getMessage());
        }
        return drawable;
    }

    /**
     * 获取应用名称
     * @param packageName
     * @param manager
     * @return
     */
    private static String getAppName(String packageName, PackageManager manager) {

        String appName = null;
        try {


            ApplicationInfo applicationInfo = manager.getApplicationInfo(packageName, 0);

            if (applicationInfo != null)

                appName = (String) manager.getApplicationLabel(applicationInfo);

            return appName;
        }catch (Exception e) {
            Log.e(TAG, "getAppName - "+e.getMessage());
        }

        return appName;
    }
}
