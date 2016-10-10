package com.base.androidcard_test;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.base.utils.SDUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 100;
    private Button fileDir,cacheDir,externalFileDir,externalCacheDir,externalDir,metrialBtn,customerHBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileDir = (Button) findViewById(R.id.filedir);
        cacheDir = (Button) findViewById(R.id.cahcedir);

        externalFileDir = (Button) findViewById(R.id.exterfiledir);
        externalCacheDir = (Button) findViewById(R.id.extercahcedir);

        externalDir = (Button) findViewById(R.id.exterdir);

        metrialBtn = (Button) findViewById(R.id.progress);

        customerHBar = (Button) findViewById(R.id.customer_h_progress);

        fileDir.setOnClickListener(this);
        cacheDir.setOnClickListener(this);
        externalFileDir.setOnClickListener(this);
        externalCacheDir.setOnClickListener(this);
        externalDir.setOnClickListener(this);
        metrialBtn.setOnClickListener(this);
        customerHBar.setOnClickListener(this);
        convertToMap();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.filedir:

                if(SDUtils.createPrivateFileDir(this, "test")){
                    Log.e(TAG,"创建成功");
                }

                break;
            case R.id.cahcedir:

                if(SDUtils.createPrivateCacheDir(this, "test")){

                    Log.e(TAG,"创建成功");
                }
                break;

            case R.id.exterfiledir:

                if(SDUtils.createExternalPrivateFileDir(this, "Test", "")){
                    Log.e(TAG,"创建成功");
                }

                break;
            case R.id.extercahcedir:

                if(SDUtils.createExternalPrivateCacheDir(this, "Test")){

                    Log.e(TAG,"创建成功");
                }
                break;

            case R.id.exterdir:


                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
                }

                break;

            case R.id.progress:

                startActivity(new Intent(this, MetrialProgressActivity.class));
                break;

            case R.id.customer_h_progress:

                startActivity(new Intent(this, ProgressBarActivity.class));
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e(TAG, "requestCode:"+requestCode+"- String[] permissions:"+permissions+" - int[] grantResults:"+grantResults);

        switch (requestCode){

            case WRITE_EXTERNAL_STORAGE_CODE:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //得到用户授予的权限

                    Toast.makeText(this, "授权",Toast.LENGTH_SHORT).show();

                    if(SDUtils.createExternalSDCard(this, "SD")){

                        Log.e(TAG,"创建成功");
                    }else
                        Log.e(TAG,"创建失败");
                }else{

                    //没有得到用户授予的权限
                    Toast.makeText(this, "未授权",Toast.LENGTH_SHORT).show();
                }

                break;
        }
        for(String key:permissions) {
            Log.e(TAG, "permissions:"+key);
        }

        for (int key:grantResults){
            Log.e(TAG, "grantResults:"+key);
        }
    }


    public void convertToMap() {

        String value = "{\"zbgsId\":\"LH\",\"zspjJlgsxx\":null,\"isGrade\":\"isGrade1\"}";

        HashMap<String, String> result = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(value);

            if (jsonObject != null) {

                String values = jsonObject.getString("zbgsId");
                result.put("zbgsId",""+values);

                values = jsonObject.getString("zspjJlgsxx");
                result.put("zspjJlgsxx",""+values);

                values = jsonObject.getString("isGrade");
                result.put("isGrade",""+values);


            }

            if (result != null && result.size()>0) {
                for (String key:result.keySet()) {
                    Log.e(TAG, ""+key+":"+result.get(key));
                }
            }

        } catch (Exception e) {
            Log.e(TAG, ""+e.getMessage());
        }
    }

}
