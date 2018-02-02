/**
 * Author: S.J.H
 * 
 * Date: 2016/7/1
 */
package com.jdruanjian.jdks;

/**
 * 常数
 */
public class Constants {

	//IP地址
	public static final String API_SERVER = "http://www.jindi163.com:8888/app";
	/**
	 * 注册
	 */
	public final static String REGISTER = API_SERVER+"/user/register";

	/*
	验证码
	* */
	public final static String GER_CODE = API_SERVER+"/user/get_smscode";

	/**
	 * 登录
	 */
	public final static String LOGIN =API_SERVER+ "/user/login";

	//指定彩票最新一期
	public final static String NEW_NUMBER=  "https://jindi163.com:8443/JDLot/type/new/number";

	//所有彩票最新一期
	public final static String NEW_KSLOT = "https://jindi163.com:8443/JDLot/type/new/lot";

	//检查用户名
	public final static String CHECK_PHONE= API_SERVER+"/user/checkname";

	//重置密码
	public final static String RESET_PWD= API_SERVER+"/user/resetPwd";

	//首页banner广告
	public static final String BANNER_PAGE = API_SERVER+"/common/advertising";

	//获取价格
	public static final String GET_PRICE = API_SERVER+"/common/app_info";

	//首页排行榜
	public static final String QUERY_RANK = API_SERVER+"/common/index";

	//所有已开彩票列表
	public final static String ALREADY_LOTT= "https://jindi163.com:8443/JDLot/lot/type/pagelist";

	//获得指定价格卡号
	public final static String ORDER_CARDNUM= API_SERVER+"/common/pay_address";

	//修改分数名次
	public static final String MODIFY_GRADE_RANK = API_SERVER+"/user/update";

	//获取所有计划
	public static final String QUERY_RESULT = API_SERVER+"/plan_info/all";

	//统一充值接口
	public final static String RECHARGE= API_SERVER+"/user/pay";

	//检查版本
	public final static String CHECK_VERSION= API_SERVER+"/os/version";

	//下次开奖时间
	public final static String NEXT_TIME= API_SERVER+"/common/next_time";


	//意见反馈 微信，QQ交流群信息
	public final static String WEICHA_MESS= API_SERVER+"/dict/group/list";

	//意见反馈 微信，QQ交流群信息
	public final static String FEED_BACK= API_SERVER+"/user/advise/add";

	//意见反馈 微信，QQ交流群信息
	public final static String CALCULATOR= API_SERVER+"/lot/count/list";

	//取消订单
	public final static String CANCEL_ORDER= API_SERVER+"/order/cancel";


	//appid 微信分配的公众账号ID
	public static final String APP_ID = "wx8cd6b3025822aceb";

	//商户号 微信分配的公众账号ID
	public static final String MCH_ID = "1494530282";

	//判断
	/**
	 * 日志
	 */
	public static final String TAG = "hhdebug";

	/**
	 * 开发者模式
	 */
	public static final boolean DEVELOPER_MODE = true;
	/**
	 * app cache key
	 */
	public static final String CURRENT_VERSION = "";// 当前版本

	/**
	 * action
	 */
	public static final String NOTIFICATION_REMAIN_CHANGE = "";
	/**
	 * image cache
	 */
	public static final String IMAGE_CACHE_SDCARD_PATH = "";
	/**
	 * error log
	 */
	public static final String LOG_CACHE_SDCARD_PATH = "";
	public static final String CURRENT_USER = "";// 当前登录用户
	public static final String CURRENT_USER_INFO = "";// 当前登录用户详细信息
	public static final String CURRENT_SHOPS = "";
	public static final String CURRENT_USER_LIST = "";// 用户列表
	public static final String CURRENT_CLASSLIST = "";

}
