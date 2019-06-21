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
	 * 获取方法绑定的URL地址(有些框架，一个方法上 绑定了多个路径。如果想要此方法生效，，必须getURLByMethod方法返回null;)
	 * 
	 * <p>
	 * 此方法不是控制方法，请返回null
	 * 
	 * @param method 绑定了相关url的方法
	 * @return String[]
	 */
	default public String[] getURLByMethod2(Method method) {return null;}
	
	/**
	 * 获取方法绑定的视力url路径 <br>
	 * 如果你希望，入口权限，与返回的视图路径的管理 权限是一样的，那么，可以在此传入你的视力路径。<br>
	 * 注：视力路径，是不会自动追加注解在类上的任何东西<br>
	 * 
	 * <p>
	 * 此方法不是控制方法，请返回null
	 * 
	 * @param method 绑定了相关url的方法
	 * @return String[]
	 */
	default public String[] getViewByMethod(Class<?> c,Method method) {return null;}
	
	
	
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
	
	/**
	 * 这是一个开关，返回true时，默认方法名即是编号（当此方法没有注解任何权限时）<br>
	 * 默认是false<br>
	 * 为什么不把此关开放到 JWP.properties里呢？主要是属性文件，容易修改，因为权限编号非常敏感，并且我们的权限开发时，就定死的。所以放到接口类来。<br>
	 * 编译成class后，可一定程度上，防止sb，误操作修改了属性文件
	 * 
	 * @return boolean
	 */
	default boolean methodIsCode() {
		return false;
	}
}
