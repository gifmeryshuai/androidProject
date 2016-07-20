
		
		*安装时把apk文件复制到此目录

	*	data/data
 	存放应用程序的数据

	*	Data/dalvik-cache
 	将apk中的dex文件安装到dalvik-cache目录下(dex文件是dalvik虚拟机的可执行文件,其大小约为原始apk文件大小的四分之一)
 


       安装过程：复制APK安装包到data/app目录下，解压并扫描安装包，把dex文件(Dalvik字节码)保存到dalvik-cache目录，
       并data/data目录下创建对应的应用数据目录




	我实现的功能就是
	1，根据包名称或者是应用名称来查询已经安装的apk
	2，得到包名称应用名称和文件安装的目录“
	3，最后将文件复制到SDcard 的根目录上面；




	![image](http://ubmcmm.baidustatic.com/media/v1/0f000nKRys8gFl6qjAVu6f.jpg)
	<img src="https://github.com/gifmeryshuai/androidProject/blob/master/pictures/device-2016-07-20-141236.png?raw=true" />


