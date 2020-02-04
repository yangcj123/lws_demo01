/**
 * 代号:隐无为 2017：厚溥
 * 文件名：Constant.java
 * 创建人：柯栋
 * 日期：2017年2月24日
 * 修改人：
 * 描述：
 */
package cn.com.djin.lws.utils;

/**
 * 用途：常量
 */
public class WebConstant {

	public static final Integer PAGENUM=1;  //页码
	public static final Integer PAGESIZE=10;//页数
	public static final String MD5KEY="hp";   //MD5 密钥
	
	/**
	 * 成功
	 */
	public static final String SUCCESS = "success";
	/**
	 * 失败
	 */
	public static final String ERROR = "error";
	/**
	 * 手机
	 */
	public static final String PHONE = "phone";
	/**
	 * 邮件
	 */
	public static final String EMAIL = "email";
	/**
	 * 实物
	 */
	public static final String ITEMS = "items";
	/**
	 * 日程
	 */
	public static final String  SCHEDULE = "schedule";
	/**
	 * 即时消息
	 */
	public static final String CHAT_MESSAGE = "chatMessage";
	/**
	 * 离线消息
	 */
	public static final String OFF_LINE_MESSAGE = "offLineMsg";
	/**
	 * 响应码
	 */
	public static final Integer SUCCESSCODE=200;   
	public static final Integer PAGESUCCESSCODE=0;   
	public static final Integer ERRORCODE=500;   
	/**
	 * 删除标志
	 */
	public static final Integer DELETECODE=0;   
	/**
	 * 未删除标志
	 */
	public static final Integer NODELETECODE=1;   
	
	/**
	 * 未删除标志
	 */
	public static final Integer OFFDELETECODE=2;  
	
	
	/**
	 * 未发送
	 */
	public static final Integer NOT_SEND=0;
	/**
	 * 已发送
	 */
	public static final Integer SENDED=1;
	public static final String FRIEND = "friend";
	public static final String GROUP = "group";
	
	/**
	 * 发送提示信息
	 */
	public static final String ONLINE_TIPS = "online_tips";
	public static final String OFFLINE_TIPS = "offline_tips";
	
	/**
	 * 添加好友申请
	 */
	public static final String SENDFRIENDAPPLY = "sendFriendApply";
	public static final String SENDGROUPAPPLY = "sendGroupApply";
	
	/**
	 * 同意添加好友
	 */
	public static final String AGREEFRIENDAPPLY = "agreeFriendApply";
	public static final String AGREEGROUPAPPLY = "agreeGroupApply";
	
	/**
	 * 好友状态
	 */
	public static final Integer UFSTATUEAPPLY = 0;
	public static final Integer UFSTATUEAGREE = 1;
	
	/**
	 * 待完成
	 */
	public static final Integer WORKING=0;
	
	/**
	 * 已完成
	 */
	public static final Integer WORKED=1;
	
}
