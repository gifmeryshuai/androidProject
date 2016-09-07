## Android 异常崩溃日志，捕捉并保存到本地；
--------------------------------------------

#####  前几天因为在省公安厅做一个通讯类之类的应用；碰到个问题，就是download人员信息将信息保存到本地数据库完成的时候，菊花转还没有dismission掉程序就崩溃了；当然这种问题是可以排查和猜测的，当时我就猜测是progressBar的问题，

![软件](https://github.com/gifmeryshuai/markDown-ImageCahce/blob/master/imageMarkDown/device-201609061553.png?raw=true)

##### 其实bug很接近，跟progressBar也有关系；就是在Fragment中获取getActivity是为空的问题；也是醉了；就是上面这个图片，当同步到100%时候就崩溃了；

##### 最关键的还不是说bug问题主要问题是他们公安厅的手机是双系统的，总而言之一句话就是不能调式手机（开发者模式）安装只能通过把内存卡拔出来之后在将打包好的apk复制进去，然后插入手机，安装，查看原因；反正就是坑爹，刚开始我以为找到了问题，可是没有效果，就是看不见错误日志，所以我上网查了查资料，对比别人写的异常崩溃日志，我自己又改良了一下；

----
### 我这里通过分点的方式在这里讲解代码主要逻辑代码都是在重写的uncaughtException方法中实现的，只是方法写的比较多，认真看是可以看明白的；
- ##### 首先创建异常类实现系统默认的接口
- ##### 提供抽象的方法供子类操作
- ##### 捕捉到异常手机异常信息和设备信息保存到本地

#### 1,在创建异常类之后要决定使用当前类来处理异常；CrashAppLog.java
```xml
     public void init(Context context) {

       try {
           if (context == null)
               throw new NullPointerException("Application 的Context不能为空");

           this.mContext = context;

           /**
            * 获取系统默认的异常处理类
            */
           mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

           /**
            * 在获取系统异常类之前子类可以先做一些初始化的操作
            */
           initParams(this);
           /**
            * 使用当前的类为异常处理类
            */
           Thread.setDefaultUncaughtExceptionHandler(this);
       }catch (Exception e){
           Log.e(TAG, "init - "+e.getMessage());
       }

    }
```

#### 2,异常类在实现接口Thread.UncaughtExceptionHandler的时候会重写uncaughtException方法，这个方法就是在程序崩溃或异常的时候执行的；

```xml
 /**
     * 此类是当应用出现异常的时候执行该方法
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        try {

            /**
             * hanlderException此方法是是否处理异常日志，如果处理那么就返回tru              * e，如果处理遇到问题那么就返回false，交由系统默认处理
             **/
            if (!hanlderException(throwable) && mUncaughtExceptionHandler != null) {

                /**
                 * 如果此异常不处理则由系统自己处理
                 */
                this.mUncaughtExceptionHandler.uncaughtException(thread, throwable);

            }else{

                /**
                 * 可以延迟一秒钟在退出
                 */
//                Thread.sleep(1000);
                //如果开发者自己处理一场那么就自己处理，这里我处理的是退出进程
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        }catch (Exception e) {
            Log.e(TAG, "uncaughtException - "+e.getMessage());
        }
    }

```

##### 3,收集设备信息和异常日志信息；
```xml

 /**
     * 用户处理异常日志
     * @param throwable
     * @return
     */
    private boolean hanlderException(Throwable throwable) {

        try {

            if (throwable == null)
                return false;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    Looper.prepare();
                    Toast.makeText(mContext, "程序崩溃", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();

            /**
             * 收集应用信息
             */
            collectCrashLogInfo(mContext);
            /**
             * 将日志写入文件
             */
            writerCrashLogToFile(throwable);

            /**
             * 限制日子志文件的数量
             */

            limitAppLogCount(LIMIT_LOG_COUNT);

        } catch (Exception e) {
            Log.e(TAG, "hanlderException - " + e.getMessage());
        }
        return false;
    }
```

##### 4，收集应用信息
```xml
/**
     * 获取应用信息
     * @param mContext
     */
    private void collectCrashLogInfo(Context mContext) {

        try {
            if (mContext == null)
                return ;

            PackageManager packageManager = mContext.getPackageManager();

            if (packageManager != null) {

                PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

                if (packageInfo != null) {

                    String versionName = packageInfo.versionName;
                    String versionCode = ""+packageInfo.versionCode;
                    String packName = packageInfo.packageName;

                    crashAppLog.put("versionName",versionName);
                    crashAppLog.put("versionCode",versionCode);
                    crashAppLog.put("packName",packName);

                }
            }


            /**
             * 获取手机型号，系统版本，以及SDK版本
             */
            crashAppLog.put("手机型号:", android.os.Build.MODEL);
            crashAppLog.put("系统版本", ""+android.os.Build.VERSION.SDK);
            crashAppLog.put("Android版本", android.os.Build.VERSION.RELEASE);

            Field[] fields = Build.class.getFields();

            if (fields != null && fields.length > 0) {

                for (Field field:fields) {

                    if (field != null) {

                        field.setAccessible(true);

                        crashAppLog.put(field.getName(), field.get(null).toString());
                    }
                }
            }

        }catch (Exception e) {
            Log.e(TAG, "collectDeviceInfo - "+e.getMessage());
        }
    }

```
##### 5,将日志写入文件
```xml

  /**
     * 写入文件中
     * @param ex
     */
    private void writerCrashLogToFile(Throwable ex) {

        try {

            StringBuffer buffer = new StringBuffer();

            if (crashAppLog != null && crashAppLog.size() >0) {

                for (Map.Entry<String, String> entry:crashAppLog.entrySet()) {

                    buffer.append(entry.getKey()+":"+entry.getValue()+"\n");
                }
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();

            while(cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }

            printWriter.flush();
            printWriter.close();

            String result = writer.toString();

            buffer.append("Exception:+\n");

            buffer.append(result);

            writerToFile(buffer.toString());

        }catch (Exception e) {
            Log.e(TAG, "writerCrashLogToFile - "+e.getMessage());
        }
    }
    
    
    //将文件写入
    
     private void writerToFile(String s) {

        try {
            /**
             * 创建日志文件名称
             */
            String curtTimer = ""+System.currentTimeMillis();
            if (formate == null) {

                formate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            }
            String timer = formate.format(new Date());

            String fileName = "crash-"+timer+"-"+curtTimer+".log";
            /**
             * 创建文件夹
             */
            File folder = new File(CAHCE_CRASH_LOG);

            if (!folder.exists())
                folder.mkdirs();

            /**
             * 创建日志文件
             */
            File file = new File(folder.getAbsolutePath()+File.separator+fileName);

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(s);
            bufferedWriter.flush();
            bufferedWriter.close();

            sendCrashLogToServer(folder, file);

        }catch (Exception e) {
            Log.e(TAG, "writerToFile - "+e.getMessage());
        }
    }

```

##### 6,限制日子志文件的数量
```xml
/**
     * 最大文件数量
     * @param limitLogCount
     */
    private void limitAppLogCount(int limitLogCount) {

        try {

            File file = new File(CAHCE_CRASH_LOG);

            if (file != null && file.isDirectory()) {

                File[] files = file.listFiles(new CrashLogFliter());

                if(files != null && files.length >0) {

                    Arrays.sort(files, comparator);

                    if (files.length > LIMIT_LOG_COUNT) {

                        for (int i = 0 ; i < files.length - LIMIT_LOG_COUNT ;i++) {

                            files[i].delete();
                        }
                    }

                }
            }

        }catch (Exception e) {
            Log.e(TAG, "limitAppLogCount - "+e.getMessage());
        }
    }

//这里限制文件的数量我使用的文件类型过滤和排序
 /**
     * 日志文件按日志大小排序
     */
    private Comparator<File> comparator = new Comparator<File>() {
        @Override
        public int compare(File l, File r) {

            if (l.lastModified() > r.lastModified())
                return 1;
            if (l.lastModified() < r.lastModified())
                return -1;

            return 0;
        }
    };

    /**
     * 过滤.log的文件
     */
    public class CrashLogFliter implements FileFilter {

        @Override
        public boolean accept(File file) {

            if (file.getName().endsWith(".log"))
                return true;
            return false;
        }
    }

```
-------------------------------

##### 主要的代码就是这么多以上是关键代码，
- ###### 从init方法初始化
- ###### 到捕捉UncatchException方法处理handlerExcception方法
- ###### 再到收集手机应用信息collectCrashLogInfo和writerCrashLogToFile错误异常日志，最后通过writerToFile写入文件


##### 主要的步骤就这么多，其实当我们如果想要设置缓存目录的路径或者是设置日志文件最大存储数量的话，我们可以把这个类写成抽象类，实现以及方法以供子类调用；

```xml
 /**
     * 默认放在内存卡的root路径
     */
    private String CAHCE_CRASH_LOG = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
    
    /**
     * 抽象方法，
     * 在该类初始化的时候使用
     */
    public abstract void initParams(CrashAppLog crashAppLog);

    /**
     * 发送一场日志文件到服务器
     * @param folder 文件路径
     * @param file 文件
     */
    public abstract void sendCrashLogToServer(File folder, File file);

 /**
     * 允许最大日志文件的数量
     */
    private int LIMIT_LOG_COUNT = 10;
    //这两个变量是可以通过父类调用的，比如可以进行对缓存目录更改和文件数量更改
    
     public int getLIMIT_LOG_COUNT() {
        return LIMIT_LOG_COUNT;
    }

    public void setLIMIT_LOG_COUNT(int LIMIT_LOG_COUNT) {
        this.LIMIT_LOG_COUNT = LIMIT_LOG_COUNT;
    }

    public String getCAHCE_CRASH_LOG() {
        return CAHCE_CRASH_LOG;
    }

    public void setCAHCE_CRASH_LOG(String CAHCE_CRASH_LOG) {
        this.CAHCE_CRASH_LOG = CAHCE_CRASH_LOG;
    }
    //initParams这个抽象方法抛出父类进行一些操作；
    //sendCrashLogToServer(File folder, File file)这个是在异常日志写入文件之后进行用开发者进行发送给服务器
```

--------------------------------------

-------------------------------
#### 所以我们可以另外创建一个类继承于CrashAppLog实现CrashApphandler

```xml

package com.base.crash;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by 东帅 on 2016/9/6.
 */
public class CrashApphandler extends CrashAppLog {

    public static CrashApphandler mCrashApphandler = null;

//实现单例

    private CrashApphandler(){};
    public static CrashApphandler getInstance() {

        if (mCrashApphandler == null)
            mCrashApphandler = new CrashApphandler();

        return mCrashApphandler;

    }

    @Override
    public void initParams(CrashAppLog crashAppLog) {

        if (crashAppLog != null){

//动态的改变缓存目录和缓存文件数量
            crashAppLog.setCAHCE_CRASH_LOG(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"crashLog");
            crashAppLog.setLIMIT_LOG_COUNT(5);
        }
    }

    @Override
    public void sendCrashLogToServer(File folder, File file) {
        //发送服务端
        Log.e("*********", "文件夹:"+folder.getAbsolutePath()+" - "+file.getAbsolutePath()+"");
    }
}

```

##### 最后要注意的一点就是要在Application中实例化

```xml
package com.base.crash;

import android.app.Application;

/**
 * Created by 东帅 on 2016/9/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化一下就行了，别忘记了
        CrashApphandler.getInstance().init(this);
    }
}


// *** 最后加上权限

<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
<uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>

```

#### 项目我已经上传到github上了，需要的可以上GitHub下载查看！！如有不足之处请指出，必定虚心学习；谢谢啦！！！！
#### https://github.com/gifmeryshuai/androidProject.git



