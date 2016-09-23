package com.base.commonrecycleview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 东帅 on 2016/9/22.
 */
public class CommonRecycleViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "CommonRecycleViewHolder.class";
    //保存View的集合
    private SparseArray<View> mSparseArray = null;
    /**
     * 文件根布局
     */
    private View rootView;
    public CommonRecycleViewHolder(View itemView) {
        super(itemView);
        /**
         * 初始化
         */
        this.mSparseArray = new SparseArray<View>();
        this.rootView = itemView;

    }



    /**
     * 设置TextView
     * @param id TextView对应的ＩＤ
     * @param msgText 添加的数据
     */
    public void setText(int id, String msgText) {

        try {
            View view = getView(id);

            if (view != null){
                if (view instanceof TextView) {
                    //CharSequence
                    ((TextView) view).setText(""+msgText);
                }else
                    throw new ClassCastException("Current View not Convert To The TextView");

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置TextView
     * @param id TextView对应的ＩＤ
     * @param msgText 添加的数据
     */
    public void setText(int id, CharSequence msgText) {

        try {
            View view = getView(id);

            if (view != null){
                if (view instanceof TextView) {
                    ((TextView) view).setText(""+msgText);
                }else
                    throw new ClassCastException("Current View not Convert To The TextView");

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置drawable的图片
     * @param id ImageView的控件
     * @param drawable 图片显示资源
     */
    public void setImageDrawable(int id, Drawable drawable) {

        try {

            View view = getView(id);

            if (view != null ){
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageDrawable(drawable);
                }else
                    throw new ClassCastException("Current View not Convert To The ImageView");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置资源文件的图片
     * @param id ImageView的控件
     * @param resource 图片显示资源
     */
    public void setImageResource(int id, int resource) {

        try {

            View view = getView(id);

            if (view != null ){
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageResource(resource);
                }else
                    throw new ClassCastException("Current View not Convert To The ImageView");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置Bitmap的图片
     * @param id ImageView的控件
     * @param bitmap 图片显示资源
     */
    public void setImageBitmap(int id, Bitmap bitmap) {

        try {

            View view = getView(id);

            if (view != null ){
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageBitmap(bitmap);
                }else
                    throw new ClassCastException("Current View not Convert To The ImageView");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据控件的ID获取对象
     * @param id 控件id
     * @return
     */
    public View getView(int id) {

        View view = null;
        try {

            if (mSparseArray == null)
                throw new NullPointerException("SparseArray is null");

            if (rootView == null)
                throw new NullPointerException("rootView is null");

            if (id < 0)
                throw new IllegalArgumentException("TextView id not invalidate");

            view = mSparseArray.get(id);

            if (view == null) {
                view = rootView.findViewById(id);

                if (view != null){

                    mSparseArray.put(id, view);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
    /**
     * 设置监听事件的接口
     */
    public interface SetOnViewClickListener{
        public void onViewListener(View view);
    }

    /**
     * 设置监听事件
     * @param id
     * @param setOnViewClickListener
     */
    public void setViewListener(int id, final SetOnViewClickListener setOnViewClickListener) {

        try {

            View view = getView(id);

            if (view != null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (setOnViewClickListener != null) {
                            setOnViewClickListener.onViewListener(view);
                        }
                    }
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
