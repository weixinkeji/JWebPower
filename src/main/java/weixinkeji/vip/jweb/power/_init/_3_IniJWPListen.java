package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.ann.JWPRegListen;
import weixinkeji.vip.jweb.power.ann.JWPRegListenUrl;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.JWPListenPool;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;
import weixinkeji.vip.jweb.power.tools.JWPTool;
import weixinkeji.vip.jweb.power.vo.JWPListenClassVO;
import weixinkeji.vip.jweb.power.vo.JWPStaticUrlAndListenVO;

/**
 * 初始化系统事件接口的实现类
 * 
 * @author wangchunzi
 *
 */
public class _3_IniJWPListen extends __InitTool {
	private JWPExpressionTool expressTool=new JWPExpressionTool();

//注：收集监听的信息中，三个HashMap有可能有重复的收集。所以在使用时，优先使用 方法上的（如果有），次使用类上的（如果有），最后使用扫描的（如果有）
	// 在类上的 监听--Controller
	private Map<Class<?>, Class<? extends JWPListenInterface>[]> inClass = new HashMap<>();
	// 在方法上的监听--Controller
	private Map<Method, Class<? extends JWPListenInterface>[]> inMethod = new HashMap<>();
	// 需要扫描的监听--Controller
	private List<JWPListenClassVO> inJWPRegListenUrl_Controller = new ArrayList<>();
	
	// 需要扫描的监听-静态方法
//	private Map<String, Class<? extends JWPListenInterface>> inJWPRegListenUrl_static = new HashMap<>();
	private List<JWPListenClassVO> inJWPRegListenUrl_static = new ArrayList<>();
	
	/**
	 * @param list 扫描到的类
	 */
	_3_IniJWPListen(List<Class<?>> list) {
		super(list);
		this.iniCMListen();
		this.iniScanListen();
		
		//排序
		this.sortListen_express(inJWPRegListenUrl_Controller,1);
		this.sortListen_express(inJWPRegListenUrl_static,0);
	}

	/**
	 * 取得注解在方法、类 或者标有@JWPRegListenUrl 的 JWPListenInterface对象
	 * 
	 * 
	 * @param m   方法 当m为null时，会跳过注解在方法上的监听类的获取
	 * @param c   类 当类为null时，会跳过注解在类上的监听类的获取
	 * @param url 路径 当路径为null或空时，会跳过标有@JWPRegListenUrl的监听类的获取
	 * @return JWPListenInterface 找到 执行监听的实例
	 */
	public JWPListenInterface[] getJWPListenInterfaces(Method m, Class<?> c, String url) {
		Set<JWPListenInterface> list = new LinkedHashSet<JWPListenInterface>();
		Class<? extends JWPListenInterface> listen[];

// 类与方法上的监听，是相融合的。与表达式上的是排斥的
		
		// 如果方法不为null，检查方法上有没有监听器。如果有，则注册
		if (null != (listen = null != m ? this.inMethod.get(m) : null)) {
			for(Class<? extends JWPListenInterface> listenClass:listen) {
				list.add(JWPListenPool.getIURLListenMethod(listenClass));
			}
			
		}
		// 如果类不为null，检查类上有不有监听器。
		if (null != (listen = null != c ? this.inClass.get(c) : null)) {
			for(Class<? extends JWPListenInterface> listenClass:listen) {
				list.add(JWPListenPool.getIURLListenMethod(listenClass));
			}
		}
		if(list.size()>0) {
			return list.toArray(new JWPListenInterface[list.size()]);
		}
		
// 类与方法上没有监听是时，到表达式里找。
		//没有表达式时，直接返回null
		if(null==url||url.isEmpty()) {
			return null;
		}
		for(JWPListenClassVO vo:inJWPRegListenUrl_Controller) {
			if(expressTool.isUrlListen(url, vo.url)) {
				list.add(JWPListenPool.getIURLListenMethod(vo.jwpListenClass));
			}
		}
		return list.size()>0?list.toArray(new JWPListenInterface[list.size()]):null;
	}
	
	//表达式中的 集合的元素，进行排序-升
	//sytle=1:controller  sytle=0:static
	private void sortListen_express(List<JWPListenClassVO> list,int sytle) {
		Collections.sort(list, new Comparator<JWPListenClassVO>() {
            @Override
            public int compare(JWPListenClassVO o1, JWPListenClassVO o2) {
            	if(sytle==1) {
            		return this.getSort_controller(o1.jwpListenClass)-this.getSort_controller(o2.jwpListenClass);
            	}
            	return this.getSort_static(o1.jwpListenClass)-this.getSort_static(o2.jwpListenClass);
            }
            
        	private int getSort_static(Class<?extends JWPListenInterface> c) {
        		JWPRegListenUrl reg=c.getAnnotation(JWPRegListenUrl.class);
        		if(null==reg) {return 0;}
        		return reg.staticSort();
        	}
        	private int getSort_controller(Class<?extends JWPListenInterface> c) {
        		JWPRegListenUrl reg=c.getAnnotation(JWPRegListenUrl.class);
        		if(null==reg) {return 0;}
        		return reg.controllerSort();
        	}
        });
	}

	/**
	 * 取得标有@JWPRegListenUrl 的 JWPListenInterface类-静态资源
	 * 因为静态资源路径是按前缀比较，成功了，就检出相关联的监听，然后执行。 所以，返回全部找到的执行监听的对象
	 * 
	 * @return JWPStaticUrlAndListenVO[] 执行监听的对象集合
	 */
	public JWPStaticUrlAndListenVO[] getJWPListenInterface_static() {

		JWPStaticUrlAndListenVO[] vos = new JWPStaticUrlAndListenVO[this.inJWPRegListenUrl_static.size()];
		if (this.inJWPRegListenUrl_static.size() > 0) {
			int i = 0;
			for (JWPListenClassVO kv : this.inJWPRegListenUrl_static) {
				vos[i++] = new JWPStaticUrlAndListenVO(kv.url, JWPListenPool.getIURLListenMethod(kv.jwpListenClass));
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
		boolean hasRegStaticListen=false;
		for (Class<?> c : super.list) {
			reg = c.getAnnotation(JWPRegListenUrl.class);
			// 首先需要有@JWPRegListenUrl，次需此类实现了JWPListenInterface接口
			if (null != reg && JWPTool.isFatherSon(JWPListenInterface.class, c)) {
				// 如果绑定了Controller路径
				if (null != reg.controllerUrl() && reg.controllerUrl().length > 0) {
					for (String url : reg.controllerUrl()) {
						//如果是正则表达式
						if(expressTool.isRegexExpression(url)) {
							this.inJWPRegListenUrl_Controller.add(new JWPListenClassVO(url, (Class<? extends JWPListenInterface>) c));
						}
						//如果是简化表达式
						else if(expressTool.isSimpleRegexExpression(url)) {
							url = JWPExpressionTool.formatSimpleRegexExpression(url);
							this.inJWPRegListenUrl_Controller.add(new JWPListenClassVO(url, (Class<? extends JWPListenInterface>) c));
						}
						//其他情况，当作完整的路径
						else {
							url=DUrlTools.formatURLAndCacheUrl(url);//有可能是动态路径。先对路径进行转化
							this.inJWPRegListenUrl_Controller.add(new JWPListenClassVO(url, (Class<? extends JWPListenInterface>) c));
						}
					}
				}
				// 如果绑定了静态资源路径
				if (null != reg.staticUrl() && reg.staticUrl().length > 0) {
					hasRegStaticListen=false;
					for (String url : reg.staticUrl()) {
						for(JWPListenClassVO staticVo:inJWPRegListenUrl_static) {
							if(staticVo.url.equals(url)&&staticVo.jwpListenClass==c) {
								hasRegStaticListen=true;
								break;//结束此循环,表示同样的路径，已经注册同样的类
							}
						}
						if(!hasRegStaticListen) {
							this.inJWPRegListenUrl_static.add(new JWPListenClassVO(url, (Class<? extends JWPListenInterface>) c));	
						}
					}
				}
			}
		}
	}
}
