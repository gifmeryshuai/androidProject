

1，缺少字段：4跟 字段（主要是在警务通系统，业务系统，工具应用三类中）
		-- "appTypeId": "swAppType_home",
      --  "appTypeName": "主业类应用",
      --  "downloadNum": 2,
	   -- "previewImgs": "",

2，在警务通系统和业务系统以及工具应用中缺少

	"code": 0,
    "msg": "获取成功",
    "success": true

	这种具有验证数据安全性的判断字段；

3, 缺少 获取全部appids的versionCode

	故不能在启动时检测应用是否需要升级


4，获取全部应用的接口

	故不能过滤警务通应用在重新安装之后，遗留插件的判断和更新；