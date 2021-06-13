package com.house.framework.commons.conf;

/**
 *整个应用通用的常量 
 *<br><b>类描述:</b>
 *<pre>|</pre>
 */
public final class CommonConstant{

//------------------------------------ SESSION 信息 KEY----------------------------------	
   /**
    * 用户对象放到Session中的键名称
    */
   public static final String USER_CONTEXT_KEY = "USER_CONTEXT_KEY";
   
   /**
    * App用户对象放到Session中的键名称
    */
   public static final String USER_APP_KEY = "USER_APP_KEY";
   public static final String DRIVER_APP_KEY="DRIVER_APP_KEY";
   public static final String CLIENT_APP_KEY="CLIENT_APP_KEY";
   public static final String WORKER_APP_KEY="WORKER_APP_KEY";
   /** 登入验证码在Session 中的键名称*/
   public static final String VERITY_CODE_KEY = "VERITY_CODE";
   /**
    * 短信验证码
    */
   public static final String SMS_CODE_KEY="SMS_CODE";
   /**
    * 操作员编号
    */
   public static final String CZYBH = "CZYBH";
//------------------------------------ 部门人员树 节点类型----------------------------------	
	/** 部门节点标志 */
	public static final String DEPART = "depart";
	/** 人员节点标志 */
	public static final String USER = "user";
	
//-----------------------------------------树根节点-------------------------------------------
	/** 树根节点 */
	public static final String ROOT_ID = "0";
	
//-----------------------------------------3个内置角色编码-------------------------------------------
	/** 超级管理员 */
	public static final String SUPER_ADMIN = "SUPER_ADMIN";
	/** 一级管理员 */
	public static final String ONE_ADMIN = "ONE_ADMIN";
	/** 二级管理员 */
	public static final String TWO_ADMIN = "TWO_ADMIN";
	
	
//------------------------------------------缓存数据编码--缓存数据编码-----------------------------------------
	
	public static final String CACHE_MENUS_KEY = "MENU_CACHE";
	
	public static final String CACHE_AUTHORITY_KEY = "AUTHORITY_CACHE";
	
	public static final String CACHE_DICT_KEY = "DICT_CACHE";
	
	public static final String CACHE_XTDM_KEY = "XTDM_CACHE";
	
	public static final String CACHE_POSITION_KEY = "POSITION_CACHE";
	
	public static final String CACHE_DEPARTMENT_KEY = "DEPARTMENT_CACHE";
	
	public static final String CACHE_BUILDER_KEY = "BUILDER_CACHE";
	
	public static final String CACHE_BUILDERGROUP_KEY = "BUILDERGROUP_CACHE";
	
	public static final String CACHE_ROLE_KEY = "ROLE_CACHE";
	
	public static final String CACHE_MESSAGE_KEY = "MESSAGE_CACHE";
	
	public static final String CACHE_OTHER_KEY = "OTHER_CACHE";
	
	public static final String CACHE_CUSTTYPE_KEY = "CUSTTYPE_CACHE";
	
//--------------------------------------------默认分页大小-----------------------------------------	
	/**默认分页大小*/
	public static final int DEFAULT_PAGE_SIZE = 15;
	/**wap默认分页大小*/
	public static final int WAP_DEFAULT_PAGE_SIZE = 10;
	/**默认第几页*/
	public static final int DEFAULT_PAGE_NO = 1;
	
	public static final String PAGE_NO = "pageNo";
	
	public static final String PAGE_SIZE = "pageSize";
	
	public static final String ORDER_BY = "pageOrderBy";
	
	public static final String ORDER = "pageOrder";
	
//------------------------ 服务端响应标识---------------------------------------------------------	
	
	public static final String DEFAULT_SUCCESS_MSG = "保存成功";
	public static final String DEFAULT_FAIL_MSG = "保存失败";
	
//------------------------ 分页对像在上下文标识---------------------------------------------------------	
	public static final String PAGE_KEY = "page";

//------------------------ 导出Excel标识在上下文中标识---------------------------------------------------------	
	/**导出Excel标识在上下文中标识*/
	public static final String EXCEL_EXPORT_TYPE = "excel_export_type";
	
//------------------------ 默认初始密码 ---------------------------------------------------------
	
	public static final String RESET_PASSWORD = "111111";
	
//------------------------ 导出EXCEL方式 --------------------------
	/** 导出当前页EXCEL */
	public final static String EXCEL_EXPORT_TYPE_PAGE = "0"; 
	
	/** 导出符合检索条件EXCEL */
	public final static String EXCEL_EXPORT_TYPE_ALL = "1";
	
	/**excel标题头数据保存在上下文标识*/
	public final static String EXCEL_HEAD_LIST = "EXCEL_HEAD_LIST"; 
	
	/**excel内容体数据保存在上下文标识*/
	public final static String EXCEL_DATA_LIST = "EXCEL_DATA_LIST";
	
//------------------------ 逻辑状态 --------------------------
	/** 不可用 */
	public final static String STATUS_UNUSABLE = "0";
	
	/** 可用 */
	public final static String STATUS_USABLE = "1";
	
	/** 逻辑删除 */
	public final static String STATUS_DELETE = "2";
	
	/** 逻辑是 */
	public final static String LOGIC_YES = "1";
	
	/** 逻辑否 */
	public final static String LOGIC_NO = "0";
	
	/** 系统级别 */
	public final static String SYSTEM_LEVEL = "SYSTEM_LEVEL";
	
	/** 业务级别 */
	public final static String USER_LEVEL = "USER_LEVEL";
	
//------------------------ FTP上传文件标识 --------------------------
	/**标识FTP上传*/
	public final static String FTP_UPLOAD = "FTP_UPLOAD";
	
	/**标识FTP下载*/
	public final static String FTP_DOWNLOAD = "FTP_DOWNLOAD";
	
//------------------------ 邮件参数 --------------------------
	public final static String MAIL = "mail";
	public final static String MAIL_HOST = "host";
	public final static String MAIL_PORT = "port";
	public final static String MAIL_FROM = "from";
	public final static String MAIL_USER = "user";
	public final static String MAIL_PASSWORD = "password";
	
//--------------------------日志模块常量-----------------------------

	public static final String ADMIN_CHANNEL = "admin";

	public static final String USER_CHANNEL = "user";
	
	public static final String MERCHANT_CHANNEL = "merchant";
	
	public static final String CLIENT_CHANNEL = "client";
	
//--------------------------登录方式--------------------------------------------
	
	public static final String LOGIN_MODEL_WEB = "0";//WEB端
	
	public static final String LOGIN_MODEL_PHONE = "1";//手机端
	
	public static final String PLATFORM_ALL = "0";//所有平台代码 
	
}
