package weixinkeji.vip.jweb.power.ann;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 给监听器，注册监听目标（路径）
 * @author wangchunzi
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface JWPRegListenUrl {
	/**
	 * 控制区请求路径
	 * @return
	 */
	public String[] controllerUrl() default {};
	
	/**
	 * 静态资源 前缀路径（注意 前缀。使用前缀比较与匹配）
	 * @return
	 */
	public String[] staticUrl() default {};
	
}