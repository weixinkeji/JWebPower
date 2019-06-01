package weixinkeji.vip.jweb.power.listen;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;

public class JWPListenPool {

	private static Map<Class<? extends JWPListenInterface>, JWPListenInterface> urlListen = new HashMap<>();
	private static Map<String,JWPListenInterface> url=new HashMap<String,JWPListenInterface>();
	
	public static boolean doUrlListen(FilterChain chain, HttpServletRequest req, HttpServletResponse resp, final String requestURL
			,JWPControllerModel powerModel,JWPUserPower powerCode ) throws IOException, ServletException {
		JWPListenInterface obj=url.get(requestURL);
		if(null==obj) {
			return true;
		}
		return obj.doMethod(chain,req, resp, requestURL,powerModel,powerCode );
	}
	
	synchronized public static JWPListenInterface getIURLListenMethod(Class<? extends JWPListenInterface> c) {
		JWPListenInterface obj = urlListen.get(c);
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
