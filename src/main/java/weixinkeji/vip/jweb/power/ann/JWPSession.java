package weixinkeji.vip.jweb.power.ann;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 登陆状态
 * @author wangchunzi
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface JWPSession {
}
