package com.base.crashlog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


/**
 * 此类是为了在App在雨挡异常崩溃的时候，及时的捕捉到异常日志信息；
 * 保存到文件中去，之后发发送到服务器；
 * @author Huang
 */
@SuppressLint("SimpleDateFormat")
public abstract class AppCrashLog implements UncaughtExceptionHandler {

	
	
	/**
	 * 日志数量最大数量
	 */
	public static int LIMIT_FILE_COUNT = 10;
	private static final String TAG = "AppCrashLog.class";
	/**
	 * 日志缓存目录
	 */
	public static String CACHE_LOG = Environment.getExternalStorageDirectory()+File.separator;
	/**
	 * 日期格式类型
	 */
	private SimpleDateFormat format = null;
	/**
	 * 设备日志信息
	 */
	private LinkedHashMap<String, String> crashLogInfos = new LinkedHashMap<String ,String>();
	
	/**
	 * 系统的异常默认类
	 */
	private UncaughtExceptionHandler exceptionHandler;
	
	/**
	 * 上下文参数
	 */
	private Context context;
	/**
	 * 在此类初始化的时候在子类中做一些操作
	 * 此刻UncaughtExceptionHandler不为空
	 */
	public abstract void initParams();
	
	/**
	 * 在捕捉到日志之后发送日志给服务器
	 */
	public abstract void sendCrashLogToServer(File file);
	
	/***
	 * 这个回调接口是在程序在异常崩溃的时候及时执行的
	 */
	
	/**
	 * 初始化
	 * @param context
	 */
	public void init(Context context) {
		
		try {

			this.context = context;

			/**
			 * 获取系统异常默认的类
			 */
			exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
			
			/**
			 * 外抛抽象方法给子类，让其做自己的操作；
			 */
			initParams();
			
			/**
			 * 绑定当前类，使当前类为异常处理类
			 */
			Thread.setDefaultUncaughtExceptionHandler(this);
			
			Log.e(TAG, "init - ");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "init - "+e.getMessage());
		}
	}
	
	/**
	 * 当程序出现崩溃的异常的时候调用此回调方法
	 * @param thread 当前线程
	 * @param ex 异常类
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
		try {

			
			if(!handlerException(ex) && exceptionHandler != null) {
				
				this.exceptionHandler.uncaughtException(thread, ex);
			}else{
				
				/**
				 * 让程序延迟1秒
				 */
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/**
				 * 退出程序
				 */
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "uncaughtException - "+e.getMessage());
		}
	}

	/**
	 * @param ex
	 * @return 如果返回false表示此异常不让开发者处理，或者开发者处理失败最后交由系统处理；
	 * 返回true则表明由开发者自己处理
	 */
	private boolean handlerException(Throwable ex) {
		
		try {
			//如果异常类为空，那么返回false
			if(ex == null) 
				return false;
			/**
			 * 做一个Toast提示，这个可以不要
			 */
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Looper.prepare();
					Toast.makeText(context, "程序异常", Toast.LENGTH_SHORT).show();
					Looper.loop();
				}
			}).start();
			
			/**
			 * 之后是手机手机设备信息
			 */
			collectDeviceInfo(context);
			//把崩溃日志保存到文件中
			crashExceptionWriterFile(ex);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "handlerException - "+e.getMessage());
		}
		return false;
	}

	/**
	 * 保存崩溃日志到sd中
	 * @param ex
	 */
	private void crashExceptionWriterFile(Throwable ex) {
		// TODO Auto-generated method stub
		try {
			
			if(ex == null) 
				
				return ;
			
			StringBuffer buffer = new StringBuffer();
			if(crashLogInfos != null && crashLogInfos.size()>0) {
				
				for(Map.Entry<String, String> entry:crashLogInfos.entrySet()) {
					
					buffer.append(entry.getKey()+":"+entry.getValue()+"\n");
				}
			}
			
			
			/**
			 * 获取异常日志信息
			 */
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			Throwable cause = ex.getCause();
			
			while(cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			
			printWriter.close();
			
			buffer.append("Exception:\n");
			buffer.append(""+writer.toString());
		
			/**
			 * 设置日志的路径与名称
			 */
			
			String timer = ""+System.currentTimeMillis();
			
			if(format == null) {
				
				format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			}
			
			String tm = format.format(new Date());
			/**
			 * 日志文件名称
			 */
			String fileName = "crash-"+tm+"-"+timer+".log";
			
			exceptionWriterSd(CACHE_LOG, fileName,  buffer.toString());
			
			/**
			 * 日志数量的管理
			 * 默认值存在10
			 */
			logLimitCount(LIMIT_FILE_COUNT);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "crashExceptionWriterFile - "+e.getMessage());
		}
		
	}

	/**
	 * 处理日志数量
	 * @param count
	 */
	private void logLimitCount(int count) {
		
		try {
			
			File file = new File(CACHE_LOG);
			
			if(file != null && file.isDirectory()) {
				
				//过滤文件类型文件
				File[] files = file.listFiles(new FileLogFilter());
				
				
				if(files != null && files.length > 10) {
					//排序
					Arrays.sort(files, comparator);
					
					for(int i = 0 ; i < files.length - LIMIT_FILE_COUNT;i++) {
						
						files[i].delete();
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "logLimitCount - "+e.getMessage());
		}
	}
	
	/**
	 * 过滤文件
	 */
	private class FileLogFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			// TODO Auto-generated method stub
			
			if(pathname !=null && pathname.getName().endsWith(".log")) {
				
				return true;
			}
			return false;
		}
		
	}

	/**
	 * 排序
	 * 由小到大
	 */
	private Comparator<File> comparator = new Comparator<File>() {

		@Override
		public int compare(File lhs, File rhs) {
			// TODO Auto-generated method stub

			if (lhs.lastModified() > rhs.lastModified())

				return 1;

			if (lhs.lastModified() < rhs.lastModified())
				return -1;

			return 0;
		}
	};
	
	/**
	 * 将异常信息和设备信息写入文件中
	 * @param folder
	 * @param fileName
	 * @param result
	 */
	private void exceptionWriterSd(String folder, String fileName, String result) {
		
		try {
			
			File file = new File(folder);
			if(!file.exists()) {
				
				file.mkdirs();
			}
			
			File fileLog = new File(file.getAbsolutePath()+File.separator+fileName);
			
			if(!fileLog.exists()) {
				
				fileLog.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileLog));
			writer.append(result);
			//
//			fileWriter.flush();
//			fileWriter.close();
			writer.flush();
			writer.close();
			
			
			sendCrashLogToServer(fileLog);
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "exceptionWriterSd - "+e.getMessage());
		}
	}

	/**
	 * 手机设备信息
	 * @param context 上下文参数
	 */
	private void collectDeviceInfo(Context context) {
		
		try {

			if(context == null) 
				return;
			
				/**
				 * 1，获取应用的包名称及版本code，name
				 */
				PackageManager packageManager = context.getPackageManager();

				if (packageManager != null) {
					PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
					
					if(info !=  null) {
						
						String versionName = ""+info.versionName;
						String versionCode = ""+info.versionCode;
						String packName = ""+info.packageName;
						
						crashLogInfos.put("versionName", versionName);
						crashLogInfos.put("versionCode", versionCode);
						crashLogInfos.put("packName", packName);
					}
				}
				
				/**
				 * 2，收集应用版本信息 
				 */
				
				/**
				 * 获取手机型号，系统版本，以及SDK版本
				 */
				crashLogInfos.put("手机型号:", Build.MODEL);
				crashLogInfos.put("系统版本", ""+ Build.VERSION.SDK);
				crashLogInfos.put("Android版本", Build.VERSION.RELEASE);
				
				/**
				 * 3，手机设备信息 
				 */
				
				Field[] fields = Build.class.getDeclaredFields();
				
				if(fields != null && fields.length>0) {
					
					for(Field field:fields) {
						
						if(field != null) {
							
							field.setAccessible(true);
							
							crashLogInfos.put(field.getName(), field.get(null).toString());
						}
					}
				}
		
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "collectDeviceInfo - "+e.getMessage());
		}
	}
}
