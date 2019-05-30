package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weixinkeji.vip.jweb.power.ann.JWPRegListen;
import weixinkeji.vip.jweb.power.config.DefaultJWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

/**
 * 初始化系统事件接口的实现类
 * 
 * @author wangchunzi
 *
 */
public class _IniJWPListen extends _InitTool {
	private JWPSystemInterfaceConfig siConfig=super.findObject(JWPSystemInterfaceConfig.class, new DefaultJWPSystemInterfaceConfig());
	
	//在类上的 监听
	private Map<Class<?>,Class<?extends JWPListenInterface>> inClass=new HashMap<>();
	//在方法上的监听
	private Map<Method,Class<?extends JWPListenInterface>> inMethod=new HashMap<>();
	
	
	/**
	 * @param list 扫描到的类
	 */
	_IniJWPListen(List<Class<?>> list) {
		super(list);
		this.iniCMListen();
		
		
	}
	
	/**
	 * 找到所有注解在方法上的监听类
	 */
	public void iniCMListen() {
		JWPRegListen reg;
		for (Class<?> c : super.list) {
			reg=c.getAnnotation(JWPRegListen.class);
			if(null!=reg&&null!=reg.value()) {
				inClass.put(c, reg.value());
			}
			Method[] ms=c.getMethods();
			
			for(Method m:ms) {
				reg=m.getAnnotation(JWPRegListen.class);
				if(null!=reg&&null!=reg.value()) {
					inMethod.put(m, reg.value());
				}
			}
		}
	}
	
}
