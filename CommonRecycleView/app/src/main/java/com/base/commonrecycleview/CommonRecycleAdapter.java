package com.base.commonrecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 东帅 on 2016/9/22.
 */
public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonRecycleViewHolder> {

    /**
     * 具体逻辑事宜交给调用者使用
     * @param holder CommonRecycleViewHolder
     * @param position 当前数据item的位置
     */
    public abstract void onCommonBindViewHolder(CommonRecycleViewHolder holder, int position);
    private Context context;
    private List<T> datas;
    private LayoutInflater mLayoutInflater;
    private int layoutId;
    public  CommonRecycleAdapter(Context context, List<T> datas, int layoutId) {

        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public CommonRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonRecycleViewHolder(mLayoutInflater.inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonRecycleViewHolder holder, int position) {

        /**
         * 将Adapter改为抽象类，向外提供抽象的方法，
         * 使onBindViewHolder方法重写让外界扩展使用
         */
        onCommonBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    /**
     * 移除所有数据
     */
    public void clearAllDatas() {

        if (datas != null) {
            datas.clear();
        }
    }

    /**
     * 新增数据
     * @param newDatas
     */
    public void addDatas(List<T> newDatas) {

        if (datas != null && newDatas != null){
            datas.addAll(newDatas);
        }
    }

    /**
     * 重置数据
     * @param datas
     */
    public void resetDatas(List<T> datas) {

        try {

            if (this.datas != null){
                this.datas.clear();

                if (datas != null) {
                    this.datas.addAll(datas);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
