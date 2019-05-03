          package weixinkeji.vip.jweb.power.expresstion;

import java.util.Map;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

public interface JWPStaticResourcesURLExpresstion {
	/**
	 * 注册监听
	 * @param map 路径(key)-监听类（value)
	 */
	default public void setListen(Map<String, Class<?extends JWPListenInterface>> map) {}
}
