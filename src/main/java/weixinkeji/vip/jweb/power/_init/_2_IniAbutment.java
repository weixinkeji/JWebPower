package weixinkeji.vip.jweb.power._init;

import java.util.List;

import weixinkeji.vip.jweb.power.config.DefaultJWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;

/**
 * 初始化系统事件接口的实现类
 * 
 * @author wangchunzi
 *
 */
public class _2_IniAbutment extends __InitTool {
	private JWPSystemInterfaceConfig siConfig=super.findObject(JWPSystemInterfaceConfig.class, new DefaultJWPSystemInterfaceConfig());
	
	/**
	 * @param list 扫描到的类
	 */
	_2_IniAbutment(List<Class<?>> list) {
		super(list);
	}
	
	/**
	 * 对接系统url的实现
	 * @return JWPSystemInterfaceConfig
	 */
	JWPSystemInterfaceConfig getJWPSystemInterfaceConfig() {
		return this.siConfig;
	}
	
	/**
	 * 拼接标注在类与方法的url
	 * 
	 * @param headUrl   标注在类的url
	 * @param methodUrl 标注在方法的url
	 * @return String 拼接后的请求路径
	 */
	String getURLByClassUrlAndMethodUrl(String headUrl, String methodUrl) {
		if (null == headUrl || null == methodUrl) {
			return null;
		}
		headUrl = headUrl.trim();
		methodUrl = methodUrl.trim();
		if (headUrl.isEmpty()) {
			return methodUrl;
		}
		if (headUrl.endsWith("/") && methodUrl.startsWith("/")) {
			return headUrl + methodUrl.substring(1);
		} else if ((!headUrl.endsWith("/") && methodUrl.startsWith("/"))
				|| (headUrl.endsWith("/") && !methodUrl.startsWith("/"))) {
			return headUrl + methodUrl;
		} else {
			return headUrl + "/" + methodUrl;
		}
	}
	
	
	
	
}
