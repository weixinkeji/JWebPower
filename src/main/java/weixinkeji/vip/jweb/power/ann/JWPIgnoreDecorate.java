package weixinkeji.vip.jweb.power.ann;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 无视注解在类上的装饰
 * @author wangchunzi
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface JWPIgnoreDecorate {

}
