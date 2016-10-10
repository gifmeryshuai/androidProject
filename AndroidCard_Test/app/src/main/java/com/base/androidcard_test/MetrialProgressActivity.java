package com.base.androidcard_test;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MetrialProgressActivity extends AppCompatActivity {


    private MaterialProgressBar materialProgressBar;

    private Timer mTimer;
    private TimerTask mTimerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrial_progress);

        materialProgressBar = (MaterialProgressBar) findViewById(R.id.horizontal_progress_library);

//        Drawable drawable = getResources().getDrawable(R.drawable.progress_color);
//        materialProgressBar.setProgressDrawable(drawable);
//        materialProgressBar.setIndeterminateDrawable(drawable);

//        materialProgressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));

        materialProgressBar.setMax(100);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

                int curProgress = materialProgressBar.getProgress();

                if (curProgress >= 100) {
                    curProgress=0;
                    materialProgressBar.setProgress(0);
                }else{
                    curProgress +=2;
                }
                materialProgressBar.setProgress(curProgress);
            }
        };

        mTimer.schedule(mTimerTask, 10, 30);
    }
}
