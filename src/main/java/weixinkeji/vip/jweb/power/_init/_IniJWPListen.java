package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weixinkeji.vip.jweb.power.ann.JWPRegListen;
import weixinkeji.vip.jweb.power.ann.JWPRegListenUrl;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.JWPListenPool;
import weixinkeji.vip.jweb.power.vo.JWPStaticUrlAndListenVO;

/**
 * 初始化系统事件接口的实现类
 * 
 * @author wangchunzi
 *
 */
public class _IniJWPListen extends _InitTool {
//	private JWPSystemInterfaceConfig siConfig = super.findObject(JWPSystemInterfaceConfig.class,
//			new DefaultJWPSystemInterfaceConfig());

//注：收集监听的信息中，三个HashMap有可能有重复的收集。所以在使用时，优先使用 方法上的（如果有），次使用类上的（如果有），最后使用扫描的（如果有）
	// 在类上的 监听--Controller
	private Map<Class<?>, Class<? extends JWPListenInterface>> inClass = new HashMap<>();
	// 在方法上的监听--Controller
	private Map<Method, Class<? extends JWPListenInterface>> inMethod = new HashMap<>();
	// 需要扫描的监听--Controller
	private Map<String, Class<? extends JWPListenInterface>> inJWPRegListenUrl_Controller = new HashMap<>();
	// 需要扫描的监听-静态方法
	private Map<String, Class<? extends JWPListenInterface>> inJWPRegListenUrl_static = new HashMap<>();

	/**
	 * @param list 扫描到的类
	 */
	_IniJWPListen(List<Class<?>> list) {
		super(list);
		this.iniCMListen();
	}

	/**
	 * 取得注解在方法、类 或者标有@JWPRegListenUrl 的 JWPListenInterface对象
	 * 
	 * 
	 * @param m   方法 当m为null时，会跳过注解在方法上的监听类的获取
	 * @param c   类   当类为null时，会跳过注解在类上的监听类的获取
	 * @param url 路径 当路径为null或空时，会跳过标有@JWPRegListenUrl的监听类的获取
	 * @return 	   JWPListenInterface 找到 执行监听的实例
	 */
	public JWPListenInterface getJWPListenInterface(Method m, Class<?> c, String url) {
		Class<? extends JWPListenInterface> listen = null != m ? this.inMethod.get(m) : null;
		if (null != listen) {
			return JWPListenPool.getIURLListenMethod(listen);
		}
		listen = null != c ? this.inClass.get(c) : null;
		if (null != listen) {
			return JWPListenPool.getIURLListenMethod(listen);
		}
		listen = null != url && url.length() > 0 ? this.inJWPRegListenUrl_Controller.get(url) : null;
		if (null != listen) {
			return JWPListenPool.getIURLListenMethod(listen);
		}
		return null;
	}
	
	
	/**
	 * 取得标有@JWPRegListenUrl 的 JWPListenInterface类-静态资源
	 * 因为静态资源路径是按前缀比较，成功了，就检出相关联的监听，然后执行。
	 * 所以，返回全部找到的执行监听的对象
	 * 
	 * @return JWPStaticUrlAndListenVO[] 执行监听的对象集合
	 */
	public JWPStaticUrlAndListenVO[] getJWPListenInterface_static() {
		
		JWPStaticUrlAndListenVO[] vos=new JWPStaticUrlAndListenVO[this.inJWPRegListenUrl_static.size()];
		if(this.inJWPRegListenUrl_static.size()>0) {
			int i=0;
			for(Map.Entry<String, Class<? extends JWPListenInterface>> kv:this.inJWPRegListenUrl_static.entrySet()) {
				vos[i++]=new JWPStaticUrlAndListenVO(kv.getKey(),JWPListenPool.getIURLListenMethod(kv.getValue()));
			}
		}
		return vos;
	}
	
	
	/**
	 * 找到所有注解在类、方法上的监听类
	 */
	public void iniCMListen() {
		JWPRegListen reg;
		for (Class<?> c : super.list) {
			reg = c.getAnnotation(JWPRegListen.class);
			if (null != reg && null != reg.value()) {
				inClass.put(c, reg.value());
			}
			Method[] ms = c.getMethods();

			for (Method m : ms) {
				reg = m.getAnnotation(JWPRegListen.class);
				if (null != reg && null != reg.value()) {
					inMethod.put(m, reg.value());
				}
			}
		}
	}

	/**
	 * 找到所有符合需求（@JWPRegListenUrl 打上这个注解的类）的监听类
	 */
	@SuppressWarnings("unchecked")
	public void iniScanListen() {
		JWPRegListenUrl reg;
		for (Class<?> c : super.list) {
			reg = c.getAnnotation(JWPRegListenUrl.class);
			// 首先需要有@JWPRegListenUrl，次需此类实现了JWPListenInterface接口
			if (null != reg && super.isFatherSon(JWPListenInterface.class, c)) {
				// 如果绑定了Controller路径
				if (null != reg.controllerUrl() && reg.controllerUrl().length > 0) {
					for (String url : reg.controllerUrl()) {
						this.inJWPRegListenUrl_Controller.put(url, (Class<? extends JWPListenInterface>) c);
					}
				}
				// 如果绑定了静态资源路径
				if (null != reg.staticUrl() && reg.staticUrl().length > 0) {
					for (String url : reg.controllerUrl()) {
						this.inJWPRegListenUrl_static.put(url, (Class<? extends JWPListenInterface>) c);
					}
				}
			}
		}
	}
}