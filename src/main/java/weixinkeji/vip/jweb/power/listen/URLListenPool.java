package weixinkeji.vip.jweb.power.listen;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

public class URLListenPool {

	private static Map<Class<? extends IURLListenMethod>, IURLListenMethod> urlListen = new HashMap<>();
	private static Map<String,IURLListenMethod> url=new HashMap<String,IURLListenMethod>();
	
	public static boolean doUrlListen(HttpServletRequest req, HttpServletResponse resp, final String requestURL,SessionCodeAndIdentifiterCodeVO powerCode ) {
		IURLListenMethod obj=url.get(requestURL);
		if(null==obj) {
			return true;
		}
		return obj.doMethod(req, resp, requestURL,powerCode );
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
