package weixinkeji.vip.jweb.power.listen;

public enum ListenStatus {

	/**
	 * 公共区
	 */
	common_success
	
	/**
	 * 会话失败
	 */
	,session_fail
	
	/**
	 * 会话成功
	 */
	,session_success
	
	/**
	 * 等级检验失败
	 */
	,grades_fail
	
	/**
	 * 等级检验成功
	 */
	,grades_success
	
	/**
	 * 编号检验失败
	 */
	,code_fail
	/**
	 * 编号检验失败
	 */
	,code_success
	
	/**
	 * 编号+等级 检验失败
	 */
	,codeAndGrades_fail
	
	/**
	 * 编号+等级 检验成功
	 */
	,codeAndGrades_success
	
	/**
	 * 仅监听
	 */
	,onlyListen
	
	/**
	 * 静态资源区
	 */
	,staticListen;
}
