package weixinkeji.vip.jweb.power.config;

import java.lang.reflect.Method;

import javax.servlet.annotation.WebServlet;

import weixinkeji.vip.jweb.power.config.ISystemInterfaceConfig;

public class DefaultSystemInterfaceConfig implements ISystemInterfaceConfig {

	@Override
	public String getURLByClass(Class<?> c) {
		WebServlet ws = c.getAnnotation(WebServlet.class);
		if (null == ws) {
			return null;
		}
		return ws.value()[0];
	}

	@Override
	public String getURLByMethod(Method method) {
		return null;
	}

//	@Override
//	public String[] getRequestUrlSuffix() {
//		return new String[] { ".json", ".xml" };
//	}
//	
}
