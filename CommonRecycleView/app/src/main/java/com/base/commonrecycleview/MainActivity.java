package com.base.commonrecycleview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * RecycleView
     */
    private RecyclerView mRecyclerView;
    /**
     * ProgressBar
     */
    private ProgressBar mProgressBar;

    /**
     * 适配器
     */
    private CommonRecycleAdapter adapter = null;

    /**
     * 数据源
     */
    private List<AppInfo> datas = new ArrayList<AppInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        DataTask task = new DataTask();
//        task.execute();

        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 获取全部手机安装的应用
                 */
                datas = DataTools.getInstallApp(MainActivity.this);
                Log.e("MainActivity.class", "当前："+datas.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);



                        if (adapter != null){

                            /**
                             * 重置适配器中的数据
                             */
                            adapter.resetDatas(datas);
                            /**
                             * 更新通知
                             */
                            adapter.notifyDataSetChanged();
                        }

                        Log.e("MainActivity.class", ""+adapter.getItemCount());
                    }
                });

            }
        }).start();


        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(layoutManager);

        /**
         * 绑定的数据值抛到外面使用
         */
        adapter = new CommonRecycleAdapter(this, datas, R.layout.item_layout) {
            @Override
            public void onCommonBindViewHolder(CommonRecycleViewHolder holder, final int position) {

                final AppInfo info = datas.get(position);
                holder.setImageDrawable(R.id.icons, info.appIcon);
                holder.setText(R.id.titles, info.appName);

                /**
                 * 设置监听事件
                 */
//                holder.setViewListener(R.id.icons, new CommonRecycleViewHolder.SetOnViewClickListener() {
//                    @Override
//                    public void onViewListener(View view) {
//
//                        Log.e("MainActivity.class", ""+info.appName);
//                    }
//                });
//
//                holder.setViewListener(R.id.titles, new CommonRecycleViewHolder.SetOnViewClickListener() {
//                    @Override
//                    public void onViewListener(View view) {
//
//                        Log.e("MainActivity.class", ""+position);
//                    }
//                });

                holder.setViewListener(R.id.items, new CommonRecycleViewHolder.SetOnViewClickListener() {
                    @Override
                    public void onViewListener(View view) {

                        Log.e("MainActivity.class", "当前"+info.appPackName);
                    }
                });
            }
        };


        mRecyclerView.setAdapter(adapter);
    }

    private class DataTask<T> extends AsyncTask<Void, Void, List<T>> {

        @Override
        protected List<T> doInBackground(Void... voids) {
            return (List<T>) DataTools.getInstallApp(MainActivity.this);
        }

        @Override
        protected void onPostExecute(List<T> ts) {
            super.onPostExecute(ts);
            if(ts != null && ts.size() >0) {
                for(Object obj:ts) {
                    datas.add((AppInfo) obj);
                }
            }
            if (adapter != null) {
                adapter.resetDatas(datas);
                adapter.notifyDataSetChanged();
            }
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
