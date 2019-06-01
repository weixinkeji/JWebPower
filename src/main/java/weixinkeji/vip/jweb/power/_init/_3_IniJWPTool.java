package weixinkeji.vip.jweb.power._init;

import java.util.List;

import weixinkeji.vip.jweb.power.tools.JWPRequestUrlTool;

public class _3_IniJWPTool extends __InitTool{

	private String contextPath;
	private String[] requestUrlSuffix;
	
	/**
	 * @param list 扫描到的类
	 * @param contextPath 项目上下文路径
	 * @param requestUrlSuffix 请求路径的后缀（可能有多个）
	 */
	public _3_IniJWPTool(List<Class<?>> list,String contextPath,String[] requestUrlSuffix) {
		super(list);
		this.contextPath=contextPath;
		this.requestUrlSuffix=requestUrlSuffix;
	}
	
	/**
	 * Web请求路径 处理工具
	 * 
	 * @return JWPRequestUrlTool
	 */
	public JWPRequestUrlTool getRequestURLTool() {
		return new JWPRequestUrlTool(contextPath, requestUrlSuffix);
	}
}
