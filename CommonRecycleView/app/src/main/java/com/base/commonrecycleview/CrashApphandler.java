package com.base.commonrecycleview;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by 东帅 on 2016/9/6.
 */
public class CrashApphandler extends CrashAppLog {

    public static CrashApphandler mCrashApphandler = null;


    private CrashApphandler(){};
    public static CrashApphandler getInstance() {

        if (mCrashApphandler == null)
            mCrashApphandler = new CrashApphandler();

        return mCrashApphandler;

    }

    @Override
    public void initParams(CrashAppLog crashAppLog) {

        if (crashAppLog != null){

            crashAppLog.setCAHCE_CRASH_LOG(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"crashLog");
            crashAppLog.setLIMIT_LOG_COUNT(5);
        }
    }

    @Override
    public void sendCrashLogToServer(File folder, File file) {

        Log.e("*********", "文件夹:"+folder.getAbsolutePath()+" - "+file.getAbsolutePath()+"");
    }
}
