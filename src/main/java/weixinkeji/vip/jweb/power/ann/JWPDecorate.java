package weixinkeji.vip.jweb.power.ann;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 装饰 权限等级、代码等
 * @author wangchunzi
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface JWPDecorate {
	/**
	 * 会自动在编号前，加入此值
	 * @return  String
	 */
	public String codePrefix() default "";
	
	/**
	 * 会自动在编号后，加入此值
	 * @return
	 */
	public String codeSffix() default "";
	/**
	 * 会自动在等级前，加入此值
	 * @return  String
	 */
	public String gradesPrefix() default "";
	/**
	 * 会自动在等级后，加入此值
	 * @return  String
	 */
	public String gradesSffix() default "";
}
