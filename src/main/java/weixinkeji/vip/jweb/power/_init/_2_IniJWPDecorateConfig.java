package weixinkeji.vip.jweb.power._init;

import java.util.List;

import weixinkeji.vip.jweb.power.config.JWPDecorateConfig;

public class _2_IniJWPDecorateConfig extends __InitTool{

	/**
	 * @param list 扫描到的类
	 */
	_2_IniJWPDecorateConfig(List<Class<?>> list) {
		super(list);
	}
	
	/**
	 * 用户自定义 装饰类 配置
	 * @return JWPDecorateConfig 
	 */
	JWPDecorateConfig getJWPDecorateConfig() {
		return super.findObject(JWPDecorateConfig.class, null);
	}
	
}
