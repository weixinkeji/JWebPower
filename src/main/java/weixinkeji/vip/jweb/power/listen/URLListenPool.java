package weixinkeji.vip.jweb.power.listen;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.model.JWebPowerControllerModel;
import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

public class URLListenPool {

	private static Map<Class<? extends IURLListenMethod>, IURLListenMethod> urlListen = new HashMap<>();
	private static Map<String,IURLListenMethod> url=new HashMap<String,IURLListenMethod>();
	
	public static boolean doUrlListen(FilterChain chain, HttpServletRequest req, HttpServletResponse resp, final String requestURL
			,JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO powerCode ) throws IOException, ServletException {
		IURLListenMethod obj=url.get(requestURL);
		if(null==obj) {
			return true;
		}
		return obj.doMethod(chain,req, resp, requestURL,powerModel,powerCode );
	}
	
	synchronized public static IURLListenMethod getIURLListenMethod(Class<? extends IURLListenMethod> c) {
		IURLListenMethod obj = urlListen.get(c);
		if (null == obj) {
			try {
				obj = c.getConstructor().newInstance();
				urlListen.put(c, obj);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				return null;
			}
		}
		return obj;
	}
}
