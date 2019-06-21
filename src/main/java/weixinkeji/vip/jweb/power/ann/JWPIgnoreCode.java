package weixinkeji.vip.jweb.power.ann;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 无视权限编号
 * @author wangchunzi
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface JWPIgnoreCode {
}
