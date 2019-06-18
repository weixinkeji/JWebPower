package weixinkeji.vip.jweb.power.config;

import java.lang.reflect.Method;

public interface JWPSystemInterfaceConfig {

	/**
	 * 获取类绑定的URL地址
	 * <p>
	 * 此类不是控制类，请返回null
	 * <p>
	 * 如果此类没有绑定URL，但是方法绑定了，请返回空
	 * 
	 * @param c 绑定了相关注解的类
	 * @return String
	 */
	public String getURLByClass(Class<?> c);

	/**
	 * 获取方法绑定的URL地址
	 * 
	 * <p>
	 * 此方法不是控制方法，请返回null
	 * 
	 * @param method 绑定了相关url的方法
	 * @return String
	 */
	public String getURLByMethod(Method method);
	/**
	 * 获取方法绑定的URL地址(有些框架，一个方法上 绑定了多个路径。如果想要此方法生效，，必须getURLByMethod方法返回null;
	 * 
	 * <p>
	 * 此方法不是控制方法，请返回null
	 * 
	 * @param method 绑定了相关url的方法
	 * @return String[]
	 */
	default public String[] getURLByMethod2(Method method) {return null;}
	/**
	 * 请求路径 默认后缀
	 * <p>
	 * 有些特别的框架，比如SpringMVC,他配置拦截的后缀没有。
	 * <p>
	 * 但针对指定数据格式请求的url却有后缀。比如.json后缀，.xml后缀（即，不是静态资源，却加了静态资源的后缀）
	 * 
	 * @return String[]
	 */
	default String[] getRequestUrlSuffix() {
		return null;
	}
}
