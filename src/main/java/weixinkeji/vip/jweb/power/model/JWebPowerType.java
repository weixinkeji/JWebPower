package weixinkeji.vip.jweb.power.model;

/**
 * 控制路径 的权限类型
 */
public enum JWebPowerType {
	/**
	 * 放行区
	 */
	common
	/**
	 * 会话区
	 */
	,session
	/**
	 * 编号区
	 */
	,identifiter
	/**
	 * 会话区+编号区
	 */
	,sessionAndIdentifiter;	
	
}
