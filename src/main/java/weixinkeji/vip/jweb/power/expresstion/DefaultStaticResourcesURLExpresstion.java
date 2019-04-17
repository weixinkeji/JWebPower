package weixinkeji.vip.jweb.power.expresstion;

import java.util.Set;

public class DefaultStaticResourcesURLExpresstion implements IStaticResourcesURLExpresstion {

	/**
	 * 静态资源的根
	 * <br>
	 * 默认值是 "/static/"
	 * @return String
	 */
	public String setStaticResourcesRoot() {
		return "/static/";
	}

	/**
	 * 直接放行的静态资源
	 * @param set 存放表达式字符的Set集合
	 */
	@Override
	public void setRequestURL_Public(Set<String> set) {

	}

	/**
	 * 需要会员的静态资源
	 * @param set 存放表达式字符的Set集合
	 */
	@Override
	public void setRequestURL_Session(Set<String> set) {

	}

	/**
	 * 需要指定编号的静态资源
	 * @param set 存放表达式字符的Set集合
	 */
	@Override
	public void setRequestURL_Identifiter(Set<String> set) {

	}
}
