package weixinkeji.vip.jweb.power._init;

import java.util.List;

import weixinkeji.vip.jweb.power.event.DefaultJWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.DefaultJWPGlobalEvent;
import weixinkeji.vip.jweb.power.event.JWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.JWPGlobalEvent;

/**
 * 初始化系统事件接口的实现类
 * 
 * @author wangchunzi
 *
 */
public class _IniJWPEvent extends _InitTool {

	/**
	 * @param list 扫描到的类
	 */
	_IniJWPEvent(List<Class<?>> list) {
		super(list);
	}

	/**
	 * url全局事件
	 * @return JWPGlobalEvent
	 */
	public JWPGlobalEvent getJWPGlobalEvent() {
		return super.findObject(JWPGlobalEvent.class, new DefaultJWPGlobalEvent());
	}

	/**
	 * web入口 url事件
	 * @return JWPControllerURLPowerEvent
	 */
	public JWPControllerURLPowerEvent getJWPControllerURLPowerEvent() {
		return super.findObject(JWPControllerURLPowerEvent.class, new DefaultJWPControllerURLPowerEvent());
	}
}
