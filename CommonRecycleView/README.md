#### 通用的RecycleViewAdapter、RecycleViewHolder

##### 以前我们在使用ListView的时候也经常会使用到封装的ListView来提高开发效率，而对于RecycleView也是一样的，可以提高开发效率；

##### 话不多说来说一下我们具体的使用方式；以前我们使用RecycleView的时候，我们都会使用根据官方提供的实例来写，所以每一次都要重新创建适配器和ViewHolder很是麻烦，如果写一个通用的Adapter和ViewHoler那么就方便的很多；
-----------------------------------


##### 下面我们看一下按照最为普遍的RecycleView的使用的代码。

###### 主要就是这两个步骤

-  首先获取RecycleView的实例，并设置展示方式以及各种配置
-  其次创建适配器继承RecycleViewAdapter
-  之后创建ViewHolder继承RecycleView.ViewHolder
------------

###### 一,获取RecycleView的实例并配置各种设置

```xml
 mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //设置列表展示类型
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //滑动的方向
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(layoutManager);
        
        mRecyclerView.setAdapter(myAdapter);

```

###### 二,创建MyHolderView

```xml
public class MyHolderView extends RecyclerView.ViewHolder{

        private View itemViews;
        private TextView title;
        private ImageView imageView;
        public MyHolderView(View itemView) {
            super(itemView);
            itemViews = itemView.findViewById(R.id.cardviews);
            title = (TextView) itemView.findViewById(R.id.titles);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
```

###### 三，创建Adapter

```xml

public class RecycleAdapter extends RecyclerView.Adapter<MyHolderView> implements View.OnClickListener{

        //设置的item的点击事件监听器
        private RecycleItemClickListener mRecycleItemClickListener;

        public void setRecycleItemClickListener(RecycleItemClickListener mRecycleItemClickListener) {

            this.mRecycleItemClickListener = mRecycleItemClickListener;
        }
        //绑定布局，也就是ViewHolder
        @Override
        public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = mLayoutInflater.inflate(R.layout.item_layout, parent, false);
            MyHolderView myHolderView = new MyHolderView(view);
            myHolderView.itemViews.setOnClickListener(this);
            return myHolderView;
        }

        //根据绑定的布局，获取控件，插入数据
        @Override
        public void onBindViewHolder(MyHolderView holder, int position) {
            holder.imageView.setVisibility(View.VISIBLE);
            app.imageLoader.displayImage(mImagePath.get(position), holder.imageView);
            holder.title.setText(mImagePath.get(position));
//            holder.itemView.setTag(mImagePath.get(position));
            holder.itemViews.setTag(position);
        }

        //所有的数据源的数量
        @Override
        public int getItemCount() {
            return mImagePath.size();
        }

        @Override
        public void onClick(View v) {

            try {
                int position = (int) v.getTag();
                Log.e(TAG, ""+position);
                if(mRecycleItemClickListener != null)
                    mRecycleItemClickListener.onRecycleItemClickListener(v,position);
            }catch (Exception e){
                Log.e(TAG ,"click:"+e.getMessage());
            }

        }
    }

```

###### 之后是布局文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:layout_marginLeft="4dp"
    android:layout_marginRight="4dp"
    tools:context="com.base.translucents.MainActivity">


    <android.support.v7.widget.RecyclerView
        android:visibility="gone"
        android:id="@+id/recycleview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>


    <android.support.v4.widget.ContentLoadingProgressBar
        android:visibility="visible"
        android:id="@+id/progress"
        style="?android:attr/progressBarStyleLarge"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:indeterminate="false" />

</LinearLayout>
```
###### 最后是RecycleView上的Item布局

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="@drawable/ripple_bg"
    android:id="@+id/content"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <de.hdodenhof.circleimageview.CircleImageView
        android:layout_margin="8dip"
        android:id="@+id/head"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_gravity="center"
        android:src="@drawable/small"
        app:border_color="@android:color/white"
        app:border_width="2dp" />

    <TextView
        android:textSize="16sp"
        android:layout_margin="8dip"
        android:id="@+id/titles"
        android:layout_width="0dip"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_weight="1"
        android:gravity="center"
        android:text="dizhi" />
</LinearLayout>


```

------------------------

###### 通常我们开始写RecycleView的时候都是这样写的，如果每一次有列表的时候都这样写一次那岂不是很是麻烦吗，就算不麻烦自己像这样写也会吐的！所有要简化....

##### 当我们想要写工具类的时候要首先考虑他的实用性，简单性，和安全性，以及健壮性和性能的提升等等，都要考虑到；不能写一个工具类只考虑实用性和易用性，不考虑性能也是不行的。所以我们从这几个方面考虑。
- 1，创建的Adapter要将数据源传入到创建的Adapter中，这样不利于对数据源的把控和操作。还要将Context、数据源，以及Item布局传入进去； 2，另外，在onBindViewHolder方法中将控件从传入进来的ViewHolder中拿出，再填入数据，而这个方法只能在Adapter中的onBindViewHolder里面实现；
- 3，创建ViewHolder的时候每次都要整理布局里面所有的控件，初始化一遍，也很是麻烦；
- 4，而数据的List实体显示不同的数据实体也是不一样的，当实体不一样的时候你们就要重新创建一个适配器；很显然这是很不可取的；


##### 如果我们能在创建Adapter的时候将这个方法抛出到Activity中或是Fragment使用是不是方便的多了，不用考虑Adapter和，ViewHolder内部是怎么实现的，直接封装好，只考虑onBindViewHolder控件数据的添入，并显示出来，那么工作量就小了很多；

##### 接下来我们就按照上面提到的，麻烦逐一解决：

*************************

##### 一，分析：怎么样才能一次性适配器需要的参数数据传入到适配器中；

##### 解决：通过适配器的构造方法：将Context，数据源，以及item的id传入；

##### 所以，代码可以写成：
```xml
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

        //基本上适配器所需要的参数就是这么多
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    
    //在适配器中获取Viewholder，并且返回
     @Override
    public CommonRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonRecycleViewHolder(mLayoutInflater.inflate(layoutId, parent, false));
    }
    
```





##### 二，分析：怎么样才能将获取控件的方法对外抛给调用者onBindViewHolder，让调用者在外部使用；

##### 解决：可以将Adapter适配器类变成抽象的abstract，之后在声明一个抽象的方法，重写onBindViewHolder；这样你就可以控制并设置item各个控件的监听事件了！

##### 所以，代码可以写成：
```xml
 /**
     * 具体逻辑事宜交给调用者使用
     * @param holder CommonRecycleViewHolder
     * @param position 当前数据item的位置
     */
    public abstract void onCommonBindViewHolder(CommonRecycleViewHolder holder, int position);
    
     @Override
    public void onBindViewHolder(CommonRecycleViewHolder holder, int position) {

        /**
         * 将Adapter改为抽象类，向外提供抽象的方法，
         * 使onBindViewHolder方法重写让外界扩展使用
         */
        onCommonBindViewHolder(holder, position);
    }
    
    
    //最后当调用者创建Adapter的时候，就可以在外部使用onBindViewHolder这个替代的抽象方法‘onCommonBindViewHolder’
    
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

```

##### 另外还可以在Adapter中创建几个操作数据源的方法，比如清空数据源，插入，替换，等等；；；
```xml
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
```

##### 三，分析：每次创建ViewHolder的时候都要根据item布局文件指定控件，所以造成如果item文件换了，就要重新创建ViewHolder类，所以解决这个问题就不能单纯的根据item文件有什么控件就指定什么控件声明在ViewHolder中；SparseArray它和Map很像，都是key-value形式的，不过Android却提出使用SparseArray这个工具类，他能提供的“折半查找函数”，能够提高查找效率；

##### 解决：在ViewHolder中，使用SparseArray<View>，根据控件的ID为key,保存对应的控件id的对象；

##### 所以，代码可以写成：

```xml
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

```
##### 四，分析：使用数据源，通过都是通过List<Object>来传入Adapter的，当然Object对应的实体，各种各样；然而我们不能每一个实体都要创建一个Adapter，所以可以将Adapter改为泛型；

##### 解决：创建适配器的时候 class MyAdapter<T>这样写，就可以将List<T>传入进来

##### 所以，代码可以写成：

```xml
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
```

##### 此致最后，已经没有了！我个人认为就这四个点可以改进的！！如还有各位同学有不同的建议都可以提出来，大家相互交流嘛！！我也是一个菜鸟，，，互相学习。。。。最后我贴出，完成的代码：
*********************
- MainActivity.class 代码：
```xml
 mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);

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
```
- CommonRecycleAdapter<T>.class 代码：
```xml
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

```
- CommonRecycleViewHolder.class 代码
```xml
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

```

- mian_layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.base.commonrecycleview.MainActivity">


    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycleview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone" />


    <ProgressBar
        android:id="@+id/progress"
        style="?android:attr/progressBarStyleLarge"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:indeterminate="false"
        android:visibility="visible" />

</FrameLayout>

```

- item.layout 
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/items"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <LinearLayout
        android:background="@drawable/ripple_bg"
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <ImageView
            android:id="@+id/icons"
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:layout_margin="8dp"
            android:contentDescription="@string/app_name"
            android:src="@mipmap/ic_launcher" />

        <TextView
            android:id="@+id/titles"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_weight="1"
            android:gravity="center"
            android:text="@string/app_name"
            android:textColor="@android:color/black"
            android:textSize="16sp" />

    </LinearLayout>

    <View
        android:paddingTop="2dp"
        android:paddingBottom="2dp"
        android:background="@color/colorAccent"
        android:layout_width="match_parent"
        android:layout_height="1dp"></View>

</LinearLayout>
```


##### 到这里代码全部贴完，我将此项目实例小demon全部放在GitHub上面了，有需要的同学可以下载一下看一下！https://github.com/gifmeryshuai/androidProject.git
##### 项目名称：CommonRecycleView