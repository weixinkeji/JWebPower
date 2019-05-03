package weixinkeji.vip.jweb.power.model;

/**
 * 控制路径 的权限类型
 */
public enum JWPType {
	/**
	 * 放行区
	 */
	common
	/**
	 * 等级区
	 */
	,grades
	/**
	 * 编号区
	 */
	,identifiter
	/**
	 * 等级区+编号区
	 */
	,gradesAndIdentifiter
	
	/**
	 * 仅仅有监听
	 */
	,onlyListen;
}
