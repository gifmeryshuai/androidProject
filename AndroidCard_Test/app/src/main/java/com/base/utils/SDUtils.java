package com.base.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by 东帅 on 2016/9/29.
 */
public class SDUtils {

    private static final String TAG = SDUtils.class.getSimpleName();

    /**
     * 应用私有存储（内置存储）
     * 获取内置存储下的文件目录，可以用来保存不能公开给其他应用的一些敏感的信息，如用户数据信息等等。。
     * 在应用私有文件加File文件夹里面创建文件夹；这些文件夹会随着应用的卸载而被删除
     * 你也可以在手机的应用管理里面，手动删除这写缓存的信息
     * @param context 上下文参数
     * @param folder 创建文件夹名称
     * @return
     */
    public static boolean createPrivateFileDir(Context context, String folder) {

        try {

            if (context == null)
                new NullPointerException("Context don't null");

            //  /data/data/应用包名/files/
            String fileDir = context.getFilesDir().getAbsolutePath();

            ///data/user/0/com.base.androidcard_test/files/test
            File file = null;

            if (!TextUtils.isEmpty(folder))

                file = new File(fileDir+File.separator+folder);
            else
                file = new File(fileDir);

            if (!file.exists()) {

                if (file.mkdirs())
                    return true;

            }else
                Log.e(TAG, "已经存在："+file.getAbsolutePath());

        }catch (Exception e) {
            Log.e(TAG, "createPrivateFileDir - "+e.getMessage());
        }
        
        return false;
    }


    /**
     * 应用私有存储（内置存储）
     * 获取内置应用包下的缓存文件夹，比如保存一些缓存的图片一些什么的，当内置存储空间不足的时候那么就
     * 会清除一部分这里面的数据；这些文件夹会随着应用的卸载而被删除
     * 在应用私有文件加cahce文件夹里面创建文件夹
     * 你也可以在手机的应用管理里面，手动删除这写缓存的信息
     * @param context 上下文参数
     * @param folder 创建文件夹名称
     * @return
     */
    public static boolean createPrivateCacheDir(Context context, String folder) {

        try {

            if (context == null)
                new NullPointerException("Context don't null");

            //  /data/data/应用包名/cache/
            String fileDir = context.getCacheDir().getAbsolutePath();
            ///data/user/0/com.base.androidcard_test/cache/test
            File file = null;

            if (!TextUtils.isEmpty(folder))

                file = new File(fileDir+File.separator+folder);
            else
                file = new File(fileDir);

            if (!file.exists()) {

                if (file.mkdirs())

                    return true;

            }else
                Log.e(TAG, "已经存在："+file.getAbsolutePath());


        }catch (Exception e) {
            Log.e(TAG, "createPrivateFileDir - "+e.getMessage());
        }
        return false;
    }

    /**
     * 应用私有存储（外置存储）
     * 获取内置存储下的文件目录，可以用来保存不能公开给其他应用的一些敏感的信息，如用户数据信息等等。。
     * 在应用私有文件加File文件夹里面创建文件夹；这些文件夹会随着应用的卸载而被删除
     * 你也可以在手机的应用管理里面，手动删除这写缓存的信息
     * @param context 上下文参数
     * @param folder 创建文件夹名称
     * @param dir 可填可不填，添上就创建，补填默认就是根路径
     * @return
     */
    public static boolean createExternalPrivateFileDir(Context context, String folder, String dir) {

        try {

            if (context == null)
              throw new NullPointerException("Context don't null");

            //  SDCard/Android/data/应用包名/files/
            String fileDir = context.getExternalFilesDir("").getAbsolutePath();
            Log.e(TAG, "fileDir："+fileDir);
            File file = null;

            if (!TextUtils.isEmpty(folder))

                file = new File(fileDir+File.separator+folder);
            else
                file = new File(fileDir);

            if (!file.exists()) {

                if (file.mkdirs())
                    return true;

            }else
                Log.e(TAG, "已经存在："+file.getAbsolutePath());

        }catch (Exception e) {
            Log.e(TAG, "createExternalPrivateFileDir - "+e.getMessage());
        }

        return false;
    }


    /**
     * 应用私有存储（外置存储）
     * 获取内置应用包下的缓存文件夹，比如保存一些缓存的图片一些什么的，当内置存储空间不足的时候那么就
     * 会清除一部分这里面的数据；这些文件夹会随着应用的卸载而被删除
     * 在应用私有文件加cahce文件夹里面创建文件夹
     * 你也可以在手机的应用管理里面，手动删除这写缓存的信息
     * @param context 上下文参数
     * @param folder 创建文件夹名称
     * @return
     */
    public static boolean createExternalPrivateCacheDir(Context context, String folder) {

        try {

            if (context == null)
                new NullPointerException("Context don't null");

            //  /SDCard/Android/data/应用包名/cache/
            String fileDir = context.getExternalCacheDir().getAbsolutePath();
            Log.e(TAG, "fileDir："+fileDir);
            File file = null;

            if (!TextUtils.isEmpty(folder))

                file = new File(fileDir+File.separator+folder);
            else
                file = new File(fileDir);

            if (!file.exists()) {

                if (file.mkdirs())

                    return true;

            }else
                Log.e(TAG, "已经存在："+file.getAbsolutePath());


        }catch (Exception e) {
            Log.e(TAG, "createExternalPrivateCacheDir - "+e.getMessage());
        }
        return false;
    }



    public static boolean createExternalSDCard(Context context, String dir) {

        try {

            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

            Log.e(TAG,"rootPath："+rootPath);
            File file = new File(rootPath+File.separator+dir);
            if (!file.exists()) {

                if (file.mkdirs())

                    return true;
                else
                    return false;

            }else
                Log.e(TAG, "已经存在："+file.getAbsolutePath());
        }catch (Exception e) {
            Log.e(TAG, "createExternalSDCard - "+e.getMessage());
        }

        return false;
    }


}
