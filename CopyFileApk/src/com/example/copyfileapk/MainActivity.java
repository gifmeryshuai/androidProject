package com.example.copyfileapk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	
	private static final String TAG = "MainActivity.class";
	private EditText packAppEdt;
	private TextView packAppTxt;
	private Button sure, save;
	private boolean isExists = false;
	private String apkPath;
	private InputMethodManager inputMethodManager; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		packAppEdt = (EditText) findViewById(R.id.packorapp_edt);
		packAppTxt = (TextView) findViewById(R.id.packorapp_txt);
		sure = (Button) findViewById(R.id.sure);
		save = (Button) findViewById(R.id.save);
		
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		sure.setOnClickListener(this);
		save.setOnClickListener(this);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings1) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.sure:

			// TODO Auto-generated method stub
			packAppTxt.setText("正在查询请稍候...");
			save.setVisibility(View.GONE);
			try {
				
				String params = packAppEdt.getText().toString().trim();
				
				if(!TextUtils.isEmpty(params)) {
					
					PackageManager packageManager = getPackageManager();
					
					if(packageManager !=  null) {
						//获取所有的安装应用
						List<PackageInfo> infos = packageManager.getInstalledPackages(0);
						
						if(infos != null && infos.size() > 0) {
							
							for(PackageInfo info:infos) {

								if(info !=null &&(info.packageName.equals(params) || info.applicationInfo.loadLabel(packageManager).equals(params))) {
									
									String packName = info.packageName;
									String appName = (String) info.applicationInfo.loadLabel(packageManager);
									String sourcePath = info.applicationInfo.sourceDir;
									
									Log.e(TAG, "packName:"+packName+"\nappName:"+appName+"\nsourcePath:"+sourcePath);
									apkPath = sourcePath;
									packAppTxt.setText("包名称:"+packName+"\n应用名称:"+appName+"\n文件目录:"+sourcePath);
									
									isExists = true;
									save.setEnabled(true);
									save.setVisibility(View.VISIBLE);
									
									break;
								}else{
									isExists = false;
									Log.e(TAG, "不存在");
								}
								
							}
							
							if(!isExists) {
								Log.e(TAG, "未找到,请检查询条件");
								packAppTxt.setText("未找到,请检查询条件");
							}
							
						}else{
							Log.e(TAG, "无安装应用软件");
							packAppTxt.setText("无安装应用软件");
						}
					}
					
				}else{
					Log.e(TAG, "请输入查询条件");
					packAppTxt.setText("请输入查询条件");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				packAppEdt.setText(""+e.getMessage());
			}finally{
			}
		
			break;
			
		case R.id.save:
			
			/**
			 * 如果数据查到就可以进行保存
			 */
			if(v.getVisibility() == View.VISIBLE && isExists && !TextUtils.isEmpty(apkPath)) {
				
				try {
					
					//写入输入流中
					File file = new File(apkPath);
					
					if(file.exists()) {
						
						FileInputStream fis = new FileInputStream(file);
						
						//获取文件名称
						int index = apkPath.lastIndexOf("/");
						String fileName = apkPath.substring(index, apkPath.length());
						
						
						//创建复制到的文件目录
						
						String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+fileName;		

						File file2 = new File(path);
						
						if(!file2.exists()) {
							file2.createNewFile();
						}
						
						//创建复制到的文件目录
						FileOutputStream fos = new FileOutputStream(file2);
						
						int len = 0;
						byte [] buffer = new byte[1024];
						
						while((len = fis.read(buffer))!= -1) {
							
							fos.write(buffer, 0, len);
						}
						
						fos.close();
						fis.close();
						
						if(file.length() == file2.length()) {
							
							Log.e(TAG, ""+file.length()+"  -  "+file2.length());
							
							Toast.makeText(this, "保存成功:"+fileName, Toast.LENGTH_SHORT).show();
							
							save.setEnabled(false);
						}
					}
					isExists = false;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					packAppTxt.setText(""+e.getMessage());
				}
				
			}else{
				
				Toast.makeText(this, "程序异常请重新查找", Toast.LENGTH_SHORT).show();
//				sure.performClick();
			}
			
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		softKeyBoard(this);
	}
	
//	public void softKeyBoard(Context context) {
//		
//		if(inputMethodManager != null) {
//			
//			//如果输入框是有软键盘弹出，那么就关闭
//			if(inputMethodManager.isActive(packAppEdt)) {
//
//				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//				inputMethodManager.restartInput(packAppEdt);
//				Log.e(TAG, "关闭键盘");
//			}else{
//				inputMethodManager.showSoftInput(packAppEdt, InputMethodManager.HIDE_NOT_ALWAYS);
//				Log.e(TAG, "打开键盘");
//			}
//		}
//		
//	}
}
