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
	 * 会话区
	 */
	,session
	/**
	 * 等级区（提交锁定是会话区之后）
	 */
	,grades
	/**
	 * 编号区（提交锁定是会话区之后）
	 */
	,identifiter
	/**
	 * 等级区+编号区（提交锁定是会话区之后）
	 */
	,gradesAndIdentifiter
	
	/**
	 * 仅仅有监听（提交锁定是会话区之后）
	 */
	,onlyListen
	
	/**
	 * 未知
	 */
	,unknow
	;
}
