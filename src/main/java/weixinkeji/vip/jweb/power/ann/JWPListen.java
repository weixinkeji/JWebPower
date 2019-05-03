package weixinkeji.vip.jweb.power.ann;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface JWPListen {
	public Class<?extends JWPListenInterface> value();
}
