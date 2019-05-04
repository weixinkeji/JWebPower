package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIdentifiter;
import weixinkeji.vip.jweb.power.ann.JWPListen;
import weixinkeji.vip.jweb.power.ann.JWPPublic;
import weixinkeji.vip.jweb.power.config.DefaultJWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.DefaultJWPUserInterface;
import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.event.DefaultJWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.DefaultJWPGlobalEvent;
import weixinkeji.vip.jweb.power.event.JWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.JWPGlobalEvent;
import weixinkeji.vip.jweb.power.expresstion.DefaultJWPControllerURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.DefaultJWPStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.JWPControllerURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.JWPStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.JWPListenPool;
import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;
import weixinkeji.vip.jweb.power.vo.JWPExpressConfigVO;
import weixinkeji.vip.jweb.power.vo.JWPExpressVO;
import weixinkeji.vip.jweb.power.vo.JWPRequestUrlVO;
import weixinkeji.vip.jweb.tools.JWPControllePrint;

/**
 * IPublicSystemInterfaceConfig 接口的驱动类
 * <p>
 * 它会找到IPublicSystemInterfaceConfig的实现类，然后实例一个对象返回
 * 
 * @author wangchunzi
 *
 */
final public class _JWPConfigFactory {
	private List<Class<?>> list;
	private JWPSystemInterfaceConfig siConfig;
	
	public _JWPConfigFactory(List<Class<?>> list) {
		this.list = list;
		this.siConfig = loadISystemInterfaceConfig();
	}
	
	/**
	 * 外部系统接口
	 * 
	 * @return ISystemInterfaceConfig
	 */
	private JWPSystemInterfaceConfig loadISystemInterfaceConfig() {
		return findObject(JWPSystemInterfaceConfig.class, new DefaultJWPSystemInterfaceConfig());
	}
	
	/**
	 * 系统路径处理工具
	 * 
	 * @param contextPath 项目上下文路径
	 * @return RequestURLVO
	 */
	public JWPRequestUrlVO getRequestURLVO(String contextPath) {
		return new JWPRequestUrlVO(contextPath, siConfig.getRequestUrlSuffix());
	}

	/**
	 * 用户权限接口
	 * 
	 * @return IJWebPowerUserInterface
	 */
	public JWPUserInterface getJWPUserInterface() {
		return findObject(JWPUserInterface.class, new DefaultJWPUserInterface());
	}
	/**
	 * url全局事件
	 * 
	 * @return IControllerURLPowerEvent
	 */
	public JWPGlobalEvent getJWPGlobalEvent() {
		return findObject(JWPGlobalEvent.class, new DefaultJWPGlobalEvent());
	}
	
	/**
	 * web入口 url事件
	 * 
	 * @return IControllerURLPowerEvent
	 */
	public JWPControllerURLPowerEvent getJWPControllerURLPowerEvent() {
		return findObject(JWPControllerURLPowerEvent.class, new DefaultJWPControllerURLPowerEvent());
	}

	
	/**
	 * 工具：找到T的实现类，并返回一个实例。
	 * <p>
	 * 找不到，则返回用户指定的实例。
	 * 
	 * @param yourClass 你要找的类型
	 * @param obj       用户指定的实例
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	private <T> T findObject(Class<T> yourClass, T obj) {
		for (Class<?> c : list) {
			// 从集合中，找到实现了IPublicSystemInterfaceConfig接口的类。
			if (yourClass.isAssignableFrom(c) && !c.equals(yourClass)) {
				try {
					obj = null;
					return (T) c.getConstructor().newInstance();
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				}
			}
		}
		return obj;
	}

//	/**
//	 * 静态资源URL权限 表达式
//	 * 
//	 * @return ExpressConfigVO
//	 */
//	public ExpressConfigVO find_staticResourcesURLExpresstion() {
//		ExpressConfigVO vo = new ExpressConfigVO();
//		IStaticResourcesURLExpresstion sue = findObject(IStaticResourcesURLExpresstion.class,
//				new DefaultStaticResourcesURLExpresstion());
//		sue.setRequestURL_Public(vo.getPublicPowerExpresstion());
//		sue.setRequestURL_Session(vo.getSessionPowerExpresstion());
//		sue.setRequestURL_Identifiter(vo.getIdentifiterPowerExpression());
//		return vo;
//	}
	public JWPStaticResourcesModel getJWebPowerStaticResourcesModel() {
		JWPStaticResourcesURLExpresstion sre=findObject(JWPStaticResourcesURLExpresstion.class, new DefaultJWPStaticResourcesURLExpresstion());
		Map<String, Class<?extends JWPListenInterface>> map=new HashMap<>();
		sre.setListen(map);
		return new JWPStaticResourcesModel(map);
	}
	/**
	 * Controller权限 表达式
	 * 
	 * @return ExpressConfigVO
	 */
	private JWPExpressConfigVO find_controllerURLExpresstion() {
		//准备一个容器装数据
		JWPExpressConfigVO vo = new JWPExpressConfigVO();
		//找到用户配置的表达式，没有用户配置，则加载默认的配置
		JWPControllerURLExpresstion sue = findObject(JWPControllerURLExpresstion.class,
				new DefaultJWPControllerURLExpresstion());
		sue.setRequestURL_Public(vo.getPublicPowerExpresstion());
		sue.setRequestURL_grades(vo.getSessionPowerExpresstion());
		sue.setRequestURL_Identifiter(vo.getIdentifiterPowerExpression());
		//返回用户配置
		return vo;
	}

	/**
	 * 直接建立 URL-权限模型
	 * 
	 * @param jwebPowerControllerModel  权限模型
	 */
	public void setControllerPowerModel(Map<String, JWPControllerModel> jwebPowerControllerModel) {
		//实例一个专门处理Controller 权限的对象
		ClassPowerHandleTools_Temp temp = new ClassPowerHandleTools_Temp(siConfig, jwebPowerControllerModel);
		JWPExpressConfigVO controllerExpress = this.find_controllerURLExpresstion();
		temp.createPowerModel_init(controllerExpress);
		for (Class<?> c : this.list) {
			temp.createPowerModel(controllerExpress, c);
		}
	}
}

//临时工具类。专用处理标注在类、方法里的请求路径、权限
class ClassPowerHandleTools_Temp {
	//系统接口
	private JWPSystemInterfaceConfig siConfig;
	//建立的权限模型：路径-权限模型
	private Map<String, JWPControllerModel> modelMap;
	//实例一个表达式处理工具
	JWPExpressionTool expressTool = new JWPExpressionTool();

	public ClassPowerHandleTools_Temp(JWPSystemInterfaceConfig siConfig,
			Map<String, JWPControllerModel> jwebPowerControllerModel) {
		this.siConfig = siConfig;
		this.modelMap = jwebPowerControllerModel;
	}
	
	/**
	 * 先对模型中，没有使用表达的权限，直接为其建立权限模型。
	 * 
	 * @param  controllerExpress 权限表达式集合
	 */
	void createPowerModel_init(JWPExpressConfigVO controllerExpress) {
		Set<String> expression=controllerExpress.getPublicPowerExpresstion();
		JWPExpressVO vo;
		//先对Controller【公共】区的路径进行检验
		for(String urlOrExpression:expression) {
			//如果用户写的不是表达式，而是完整的url,进行建模型
			if(null!=(vo=this.expressTool.getPowerUrl(urlOrExpression))) {
				JWPControllePrint.addMessage("直接放行的路径："+vo.getExpress(), 1);
				modelMap.put(vo.getExpress(), new JWPControllerModel(JWPType.common,null, null,null));
			}
		}
		
		//对Controller【权限等级】区的路径进行检验
		expression=controllerExpress.getSessionPowerExpresstion();
		for(String urlOrExpression:expression) {
			//如果用户写的不是表达式，而是完整的url,进行建模型
			if(null!=(vo=this.expressTool.getPowerUrl(urlOrExpression))) {
//				System.out.println(vo+"直接加入权限模型的【权限等级】路径："+vo.getExpress());
				JWPControllePrint.addMessage(vo+"直接加入权限模型的【权限等级】路径："+vo.getExpress(), 1);
				modelMap.put(vo.getExpress(), new JWPControllerModel(JWPType.grades,null==vo.getValues()?JWPExpressVO.EMPTY_POWER:vo.getValues(), null,null));
			}
		}
		
		//对Controller【权限编号】区的路径进行检验
		//如果发现已经存在，执行合并，或覆盖（如果是公共区）
		expression=controllerExpress.getIdentifiterPowerExpression();
		JWPControllerModel model;
		for(String urlOrExpression:expression) {
			//如果用户写的不是表达式，而是完整的url,进行建模型
			if(null!=(vo=this.expressTool.getPowerUrl(urlOrExpression))) {
				if(null==vo.getValues()) {
					JWPControllePrint.addMessage("直接加入权限模型的【权限编号】路径："+vo.getExpress()+" \t没有指定权限编号 ", 1);
					continue;
				}
				JWPControllePrint.addMessage("直接加入权限模型的【权限编号】路径："+vo.getExpress(), 1);
				model=modelMap.get(vo.getExpress());
				//表示还没有此路径的权限模型——直接建立权限模型；或权限权限为null,执行覆盖
				if(null==model||null==model.grades) {
					modelMap.put(vo.getExpress(), new JWPControllerModel(JWPType.identifiter,null,vo.getValues(),null));
				}else {//执行合并
					modelMap.put(vo.getExpress(), new JWPControllerModel(JWPType.gradesAndIdentifiter,model.grades,vo.getValues(),null));
				}
			}
		}
	}
	/**
	 * 为类+方法 相关的url、权限 建立 权限模型
	 * 
	 * @param controllerExpress 权限表达式集合
	 * @param c        扫描到的类
	 */
	public void createPowerModel(JWPExpressConfigVO controllerExpress, Class<?> c) {
		String headURL = siConfig.getURLByClass(c);
		if (null == headURL) {// 不是控制类
			return;
		}
//		System.out.println("找到控制类绑定的url:" + headURL);
		// 可能标注在类上的权限标识符
		JWPPublic cp = c.getAnnotation(JWPPublic.class);
		JWPGrades cs = c.getAnnotation(JWPGrades.class);
		JWPIdentifiter ci = c.getAnnotation(JWPIdentifiter.class);
		// 标注在类的监听注册
		JWPListen listen = c.getAnnotation(JWPListen.class);

		// 标注在类的权限类型归属
		JWPType classPowerType = getURLPowerType(cp, cs, ci);
		// 标注在类的权限代码（权限等级、权限编号）
		JWPCodeVO classPowerCode = getPowerCode(classPowerType, cs, ci);
		// 标注在类上的监听的实现
		JWPListenInterface head_listen = null == listen ? null : JWPListenPool.getIURLListenMethod(listen.value());

		Method[] methods = c.getMethods();
		// 可能标注在方法上的权限标识符
		JWPPublic mp;
		JWPGrades ms;
		JWPIdentifiter mi;
		// 标注在方法的权限类型归属
		JWPType methodPowerType;
		// 标注在方法的权限代码（权限等级、权限编号）
		JWPCodeVO methodPowerCode;
		
		String methodURL = null;

		// 存放计算结果
		String final_requestURL = null;
		JWPType final_powerType = null;
		JWPCodeVO final_powerCode = null;
		//监听的实现
		JWPListenInterface final_listen = null;
		
		int i = 0;
		for (Method m : methods) {
			// 表示请求路径由 类相关url+方法相关url 组成！
			if (null != (methodURL = this.siConfig.getURLByMethod(m))) {
				i++;
			 	mp = m.getAnnotation(JWPPublic.class);
				ms = m.getAnnotation(JWPGrades.class);
				mi = m.getAnnotation(JWPIdentifiter.class);
				methodPowerType = getURLPowerType(mp, ms, mi);// 标注在方法的权限类型归属
				methodPowerCode = getPowerCode(methodPowerType, ms, mi);// 标注在方法的权限代码（权限等级、权限编号）
				final_requestURL = getURLByClassUrlAndMethodUrl(headURL, methodURL);// 完整的请求路径
				listen=m.getAnnotation(JWPListen.class);//监听标识符
				//最终的权限监听：如果方法上没有标识，则采用标识在类上的监听。
				final_listen=null==listen?head_listen:JWPListenPool.getIURLListenMethod(listen.value());
				
				// 方法优先级最高。如果方法的注解权限不为null,以方法的注解权限为准
				if (null != methodPowerType) {
					final_powerType = methodPowerType;
					final_powerCode = methodPowerCode;
				} else if (null != classPowerType) {// 如果方法没有注解权限，以类的注解权限为准
					final_powerType = classPowerType;
					final_powerCode = classPowerCode;
				} else {// 否则执行表达式的权限处理
					// 检验表达式，取得url相关权限
					final_powerCode = this.getExpressPowerCode(final_requestURL, controllerExpress);
					final_powerType = this.getURLPowerType(final_powerCode);
				}
				//只有监听的情况
				if(null!=final_listen&&null==final_powerType) {
					JWPControllePrint.addErrorMessage(final_requestURL + " 注意，此路径只有监听！", 1);
					modelMap.put(final_requestURL, new JWPControllerModel(JWPType.onlyListen,
							null, null,final_listen));
					continue;
				}
				
				// 如果权限类型为null,表示此路径不在权限管理范围内
				if (null == final_powerType) {
				JWPControllePrint.addErrorMessage(final_requestURL + " 不在监控范围内！", 1);	
					continue;
				}
				JWPControllePrint.addMessage(final_requestURL + ":权限检验，权限等级" + Arrays.deepToString(final_powerCode.getGrades())
				+ "    权限编号：" + Arrays.deepToString(final_powerCode.getIdentifiter()), 1);
				
				modelMap.put(final_requestURL, new JWPControllerModel(final_powerType,
						final_powerCode.getGrades(), final_powerCode.getIdentifiter(),final_listen));
			}
		}
		if (i == 0) {// 可能是Servlet的权限模型
			final_requestURL = headURL;
			// 如果方法没有注解权限，以类的注解权限为准
			if (null != classPowerType) {
				final_powerType = classPowerType;
				final_powerCode = classPowerCode;
			} else {// 否则执行表达式的权限处理
				// 检验表达式，取得url相关权限
				final_powerCode = this.getExpressPowerCode(final_requestURL, controllerExpress);
				final_powerType = this.getURLPowerType(final_powerCode);
			}
			//只有监听的情况
			if(null!=final_listen&&null==final_powerType) {
				JWPControllePrint.addErrorMessage(final_requestURL + " 注意，此路径只有监听！", 1);
				modelMap.put(final_requestURL, new JWPControllerModel(JWPType.onlyListen,
						null, null,final_listen));
			}
			// 如果权限类型为null,表示此路径不在权限管理范围内
			if (null == final_powerType) {
				JWPControllePrint.addErrorMessage(final_requestURL + " 不在监控范围内！", 1);	
				return;
			}
			JWPControllePrint.addMessage(final_requestURL + ":权限检验，权限等级" + Arrays.deepToString(final_powerCode.getGrades())
			+ "    权限编号：" + Arrays.deepToString(final_powerCode.getIdentifiter()), 1);
			modelMap.put(final_requestURL, new JWPControllerModel(final_powerType, final_powerCode.getGrades(),
					final_powerCode.getIdentifiter(),head_listen));
		}
	}

	private JWPCodeVO getExpressPowerCode(String url, JWPExpressConfigVO controllerExpress) {
		List<String[]> sessionCode = new ArrayList<>();
		List<String[]> identifiter = new ArrayList<>();
		String[] powercode;
		boolean ishasSessionPowerCode = false, isHasIdentifiterPowerCode = false;

		JWPCodeVO vo = new JWPCodeVO();
		// 找到所有与url有关的表达式所附加的【权限等级】权限。
		for (String express : controllerExpress.getSessionPowerExpresstion()) {
			if (null != (powercode = this.expressTool.isSessionPower(express, url))) {
				ishasSessionPowerCode = true;
				sessionCode.add(powercode);// 加入权限等级集合
			}
		}

		// 找到所有与url有关的表达式所附加的【权限编号 】权限。
		for (String express : controllerExpress.getIdentifiterPowerExpression()) {
			if (null != (powercode = this.expressTool.isIdentifiterPower(express, url))) {
				isHasIdentifiterPowerCode = true;
				identifiter.add(powercode);// 加入权限编号集合
			}
		}
		// 不是权限等级与权限编号 绑定的路径
		if (sessionCode.isEmpty() && identifiter.isEmpty()) {
			// 检测是否绑定了放行权限
			for (String express : controllerExpress.getPublicPowerExpresstion()) {
				if (this.expressTool.isPublicPower(express, url)) {
					vo.setPublic(true);
				}
			}
			return vo;
		}
		if (ishasSessionPowerCode) {
			// 合并权限等级 权限
			vo.setGrades(this.expressTool.mergeArrayList(sessionCode));
		}
		if (isHasIdentifiterPowerCode) {
			// 合并权限编号 权限
			vo.setIdentifiter(this.expressTool.mergeArrayList(identifiter));
		}
		return vo;
	}

	/**
	 * 拼接标注在类与方法的url
	 * 
	 * @param headUrl   标注在类的url
	 * @param methodUrl 标注在方法的url
	 * @return String 拼接后的请求路径
	 */
	private String getURLByClassUrlAndMethodUrl(String headUrl, String methodUrl) {
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

	/**
	 * 取得权限代码
	 * 
	 * @param type JWebPowerType 权限类型
	 * @param sp   SessionPower 注解：权限等级
	 * @param ip   IdentifiterPower 注解：权限编号
	 * @return SessionCodeAndIdentifiterCodeVO
	 */
	private JWPCodeVO getPowerCode(JWPType type, JWPGrades sp, JWPIdentifiter ip) {
		if (null == type) {
			return null;
		}
		JWPCodeVO vo = new JWPCodeVO();
		switch (type) {
		case common:
			return vo;
		case grades:
			vo.setGrades(this.getStringArray(sp.value()));
			return vo;
		case identifiter:
			vo.setIdentifiter(this.getStringArray(ip.value()));
			return vo;
		case gradesAndIdentifiter:
			vo.setGrades(this.getStringArray(sp.value()));
			vo.setIdentifiter(this.getStringArray(ip.value()));
			return vo;
		default:
			return null;
		}
	}
	/**
	 * 把 {"a,a1","b"}  变成  {"a","a1","b"}
	 * @param powers String[] 权限数据
	 * @return String[]
	 */
	private String[] getStringArray(String[] powers) {
		Set<String> set=new HashSet<>();
		String[] sonPower;
		for(String p:powers) {
			if(p.contains(",")) {
				sonPower=p.split(",");
				for(String son:sonPower) {
					set.add(son);
				}
			}else {
				set.add(p);
			}
		}
		String[] mypower=new String[set.size()];
		return set.toArray(mypower);
	}
	/**
	 * 取得权限类型
	 * 
	 * @param pp PublicPower 注解：公共权限
	 * @param sp SessionPower 注解：权限等级
	 * @param ip IdentifiterPower 注解：权限编号
	 * @return JWebPowerType
	 */
	private JWPType getURLPowerType(JWPPublic pp, JWPGrades sp, JWPIdentifiter ip) {
		if (null != sp && null != ip) {// 权限等级+权限编号
			return JWPType.gradesAndIdentifiter;
		}
		if (null != sp) {// 仅权限等级
			return JWPType.grades;
		}
		if (null != ip) {// 仅权限编号
			return JWPType.identifiter;
		}
		if (null != pp) {// 默认公共路径
			return JWPType.common;
		}
		return null;
	}

	private JWPType getURLPowerType(JWPCodeVO powerCode) {
		if (null != powerCode.getGrades() && null != powerCode.getIdentifiter()) {// 权限等级+权限编号
			return JWPType.gradesAndIdentifiter;
		}
		if (null != powerCode.getGrades()) {// 仅权限等级
			return JWPType.grades;
		}
		if (null != powerCode.getIdentifiter()) {// 仅权限编号
			return JWPType.identifiter;
		}
		return powerCode.isPublic() ? JWPType.common : null;
	}

}
