package weixinkeji.vip.jweb.power.listen;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class URLListenPool {

	private static Map<Class<? extends IURLListenMethod>, IURLListenMethod> urlListen = new HashMap<>();

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
