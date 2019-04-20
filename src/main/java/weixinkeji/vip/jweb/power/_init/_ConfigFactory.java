package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.ann.IdentifiterPower;
import weixinkeji.vip.jweb.power.ann.JWebPowerListen;
import weixinkeji.vip.jweb.power.ann.PublicPower;
import weixinkeji.vip.jweb.power.ann.SessionPower;
import weixinkeji.vip.jweb.power.config.DefaultJWebPowerUserInterface;
import weixinkeji.vip.jweb.power.config.DefaultSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.IJWebPowerUserInterface;
import weixinkeji.vip.jweb.power.config.ISystemInterfaceConfig;
import weixinkeji.vip.jweb.power.event.DefaultControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.IControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.expresstion.DefaultControllerURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.DefaultStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.IControllerURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.IStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.listen.IURLListenMethod;
import weixinkeji.vip.jweb.power.listen.URLListenPool;
import weixinkeji.vip.jweb.power.model.JWebPowerControllerModel;
import weixinkeji.vip.jweb.power.model.JWebPowerType;
import weixinkeji.vip.jweb.power.tools.PowerExpressionTool;
import weixinkeji.vip.jweb.power.vo.ExpressConfigVO;
import weixinkeji.vip.jweb.power.vo.JWebPowerExpressVO;
import weixinkeji.vip.jweb.power.vo.RequestURLVO;
import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

/**
 * IPublicSystemInterfaceConfig 接口的驱动类
 * <p>
 * 它会找到IPublicSystemInterfaceConfig的实现类，然后实例一个对象返回
 * 
 * @author wangchunzi
 *
 */
final public class _ConfigFactory {
	private List<Class<?>> list;
	private ISystemInterfaceConfig siConfig;
	
	public _ConfigFactory(List<Class<?>> list) {
		this.list = list;
		this.siConfig = loadISystemInterfaceConfig();
	}
	
	/**
	 * 外部系统接口
	 * 
	 * @return ISystemInterfaceConfig
	 */
	private ISystemInterfaceConfig loadISystemInterfaceConfig() {
		return findObject(ISystemInterfaceConfig.class, new DefaultSystemInterfaceConfig());
	}
	
	/**
	 * 系统路径处理工具
	 * 
	 * @param contextPath 项目上下文路径
	 * @return RequestURLVO
	 */
	public RequestURLVO getRequestURLVO(String contextPath) {
		return new RequestURLVO(contextPath, siConfig.getRequestUrlSuffix());
	}

	/**
	 * 用户权限接口
	 * 
	 * @return IJWebPowerUserInterface
	 */
	public IJWebPowerUserInterface find_IJWebPowerUserInterface() {
		return findObject(IJWebPowerUserInterface.class, new DefaultJWebPowerUserInterface());
	}

	/**
	 * web入口 url事件
	 * 
	 * @return IControllerURLPowerEvent
	 */
	public IControllerURLPowerEvent find_controllerURLPowerEvent() {
		return findObject(IControllerURLPowerEvent.class, new DefaultControllerURLPowerEvent());
	}

	/**
	 * 找到T的实现类，并返回一个实例。
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

	/**
	 * 静态资源URL权限 表达式
	 * 
	 * @return ExpressConfigVO
	 */
	public ExpressConfigVO find_staticResourcesURLExpresstion() {
		ExpressConfigVO vo = new ExpressConfigVO();
		IStaticResourcesURLExpresstion sue = findObject(IStaticResourcesURLExpresstion.class,
				new DefaultStaticResourcesURLExpresstion());
		sue.setRequestURL_Public(vo.getPublicPowerExpresstion());
		sue.setRequestURL_Session(vo.getSessionPowerExpresstion());
		sue.setRequestURL_Identifiter(vo.getIdentifiterPowerExpression());
		return vo;
	}

	/**
	 * web入口 url权限 表达式
	 * 
	 * @return ExpressConfigVO
	 */
	private ExpressConfigVO find_controllerURLExpresstion() {
		//准备一个容器装数据
		ExpressConfigVO vo = new ExpressConfigVO();
		//找到用户配置的表达式，没有用户配置，则加载默认的配置
		IControllerURLExpresstion sue = findObject(IControllerURLExpresstion.class,
				new DefaultControllerURLExpresstion());
		sue.setRequestURL_Public(vo.getPublicPowerExpresstion());
		sue.setRequestURL_Session(vo.getSessionPowerExpresstion());
		sue.setRequestURL_Identifiter(vo.getIdentifiterPowerExpression());
		//返回用户配置
		return vo;
	}

	/**
	 * 直接建立 URL-权限模型
	 * 
	 * @param jwebPowerControllerModel  权限模型
	 */
	public void setControllerPowerModel(Map<String, JWebPowerControllerModel> jwebPowerControllerModel) {
		//实例一个专门处理Controller 权限的对象
		ClassPowerHandleTools_Temp temp = new ClassPowerHandleTools_Temp(siConfig, jwebPowerControllerModel);
		ExpressConfigVO controllerExpress = this.find_controllerURLExpresstion();
		for (Class<?> c : this.list) {
			temp.createPowerModel(controllerExpress, c);
		}
	}
}

//临时工具类。专用处理标注在类、方法里的请求路径、权限
class ClassPowerHandleTools_Temp {
	//系统接口
	private ISystemInterfaceConfig siConfig;
	//建立的权限模型：路径-权限模型
	private Map<String, JWebPowerControllerModel> modelMap;
	//实例一个表达式处理工具
	PowerExpressionTool expressTool = new PowerExpressionTool();

	public ClassPowerHandleTools_Temp(ISystemInterfaceConfig siConfig,
			Map<String, JWebPowerControllerModel> jwebPowerControllerModel) {
		this.siConfig = siConfig;
		this.modelMap = jwebPowerControllerModel;
	}
	
	/**
	 * 先对模型中，没有使用表达的权限，直接为其建立权限模型。
	 * 
	 * @param  controllerExpress 权限表达式集合
	 */
	private void createPowerModel_init(ExpressConfigVO controllerExpress) {
		Set<String> expression=controllerExpress.getPublicPowerExpresstion();
		JWebPowerExpressVO vo;
		//先对Controller【公共】区的路径进行检验
		for(String urlOrExpression:expression) {
			//如果用户写的不是表达式，而是完整的url,进行建模型
			if(null!=(vo=this.expressTool.getPowerUrl(urlOrExpression))) {
				System.out.println("直接放行的路径："+vo.getExpress());
				modelMap.put(vo.getExpress(), new JWebPowerControllerModel(JWebPowerType.common,null, null));
			}
		}
		
		//对Controller【会员等级】区的路径进行检验
		expression=controllerExpress.getSessionPowerExpresstion();
		for(String urlOrExpression:expression) {
			//如果用户写的不是表达式，而是完整的url,进行建模型
			if(null!=(vo=this.expressTool.getPowerUrl(urlOrExpression))) {
				System.out.println("直接放行的【会员等级】路径："+vo.getExpress());
				modelMap.put(vo.getExpress(), new JWebPowerControllerModel(JWebPowerType.grades,null, null));
			}
		}
		
		//对Controller【权限编号】区的路径进行检验
		//如果发现已经存在，执行合并，或覆盖（如果是公共区）
		expression=controllerExpress.getIdentifiterPowerExpression();
		JWebPowerControllerModel model;
		for(String urlOrExpression:expression) {
			//如果用户写的不是表达式，而是完整的url,进行建模型
			if(null!=(vo=this.expressTool.getPowerUrl(urlOrExpression))) {
				if(null==vo.getValues()) {
					System.err.println("直接加入权限模型的【权限编号】路径："+vo.getExpress()+" \t没有指定权限编号 ");
					continue;
				}
				System.out.println("直接加入权限模型的【权限编号】路径："+vo.getExpress());
				model=modelMap.get(vo.getExpress());
				//表示还没有此路径的权限模型——直接建立权限模型；或会员权限为null,执行覆盖
				if(null==model||null==model.grades) {
					modelMap.put(vo.getExpress(), new JWebPowerControllerModel(JWebPowerType.identifiter,null,vo.getValues()));
				}else {//执行合并
					modelMap.put(vo.getExpress(), new JWebPowerControllerModel(JWebPowerType.gradesAndIdentifiter,model.grades,vo.getValues()));
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
	public void createPowerModel(ExpressConfigVO controllerExpress, Class<?> c) {
		createPowerModel_init(controllerExpress);
		String headURL = siConfig.getURLByClass(c);
		if (null == headURL) {// 不是控制类
			return;
		}
//		System.out.println("找到控制类绑定的url:" + headURL);
		// 可能标注在类上的权限标识符
		PublicPower cp = c.getAnnotation(PublicPower.class);
		SessionPower cs = c.getAnnotation(SessionPower.class);
		IdentifiterPower ci = c.getAnnotation(IdentifiterPower.class);
		// 标注在类的监听注册
		JWebPowerListen listen = c.getAnnotation(JWebPowerListen.class);

		// 标注在类的权限类型归属
		JWebPowerType classPowerType = getURLPowerType(cp, cs, ci);
		// 标注在类的权限代码（会员等级、权限编号）
		SessionCodeAndIdentifiterCodeVO classPowerCode = getPowerCode(classPowerType, cs, ci);
		// 标注在类上的监听的实现
		IURLListenMethod head_listen = null == listen ? null : URLListenPool.getIURLListenMethod(listen.value());

		Method[] methods = c.getMethods();
		// 可能标注在方法上的权限标识符
		PublicPower mp;
		SessionPower ms;
		IdentifiterPower mi;
		// 标注在类的权限类型归属
		JWebPowerType methodPowerType;
		// 标注在类的权限代码（会员等级、权限编号）
		SessionCodeAndIdentifiterCodeVO methodPowerCode;
		String methodURL = null;

		// 存放计算结果
		String final_requestURL = null;
		JWebPowerType final_powerType = null;
		SessionCodeAndIdentifiterCodeVO final_powerCode = null;
		//监听的实现
		IURLListenMethod final_listen = null;
		
		int i = 0;
		for (Method m : methods) {
			// 表示请求路径由 类相关url+方法相关url 组成！
			if (null != (methodURL = this.siConfig.getURLByMethod(m))) {
				i++;
			 	mp = m.getAnnotation(PublicPower.class);
				ms = m.getAnnotation(SessionPower.class);
				mi = m.getAnnotation(IdentifiterPower.class);
				methodPowerType = getURLPowerType(mp, ms, mi);// 标注在方法的权限类型归属
				methodPowerCode = getPowerCode(methodPowerType, ms, mi);// 标注在方法的权限代码（会员等级、权限编号）
				final_requestURL = getURLByClassUrlAndMethodUrl(headURL, methodURL);// 完整的请求路径

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
				// 如果权限类型为null,表示此路径不在权限管理范围内
				if (null == final_powerType) {
					System.err.println(final_requestURL + " 不在监控范围内！");
					continue;
				}
				System.out.println(final_requestURL + ":权限检验，会员等级" + Arrays.deepToString(final_powerCode.getGrades())
						+ "    权限编号：" + Arrays.deepToString(final_powerCode.getIdentifiter()));
				modelMap.put(final_requestURL, new JWebPowerControllerModel(final_powerType,
						final_powerCode.getGrades(), final_powerCode.getIdentifiter()));
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
			// 如果权限类型为null,表示此路径不在权限管理范围内
			if (null == final_powerType) {
				System.err.println(final_requestURL + " 不在监控范围内！");
				return;
			}
			System.out.println(final_requestURL + ":权限检验，会员等级" + Arrays.deepToString(final_powerCode.getGrades())
					+ "    权限编号：" + Arrays.deepToString(final_powerCode.getIdentifiter()));
			modelMap.put(final_requestURL, new JWebPowerControllerModel(final_powerType, final_powerCode.getGrades(),
					final_powerCode.getIdentifiter()));
		}
	}

	private SessionCodeAndIdentifiterCodeVO getExpressPowerCode(String url, ExpressConfigVO controllerExpress) {
		List<String[]> sessionCode = new ArrayList<>();
		List<String[]> identifiter = new ArrayList<>();
		String[] powercode;
		boolean ishasSessionPowerCode = false, isHasIdentifiterPowerCode = false;

		SessionCodeAndIdentifiterCodeVO vo = new SessionCodeAndIdentifiterCodeVO();
		// 找到所有与url有关的表达式所附加的【会员等级】权限。
		for (String express : controllerExpress.getSessionPowerExpresstion()) {
			if (null != (powercode = this.expressTool.isSessionPower(express, url))) {
				ishasSessionPowerCode = true;
				sessionCode.add(powercode);// 加入会员等级集合
			}
		}

		// 找到所有与url有关的表达式所附加的【权限编号 】权限。
		for (String express : controllerExpress.getIdentifiterPowerExpression()) {
			if (null != (powercode = this.expressTool.isIdentifiterPower(express, url))) {
				isHasIdentifiterPowerCode = true;
				identifiter.add(powercode);// 加入权限编号集合
			}
		}
		// 不是会员等级与权限编号 绑定的路径
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
			// 合并会员等级 权限
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
	 * @param sp   SessionPower 注解：会员等级
	 * @param ip   IdentifiterPower 注解：权限编号
	 * @return SessionCodeAndIdentifiterCodeVO
	 */
	private SessionCodeAndIdentifiterCodeVO getPowerCode(JWebPowerType type, SessionPower sp, IdentifiterPower ip) {
		if (null == type) {
			return null;
		}
		SessionCodeAndIdentifiterCodeVO vo = new SessionCodeAndIdentifiterCodeVO();
		switch (type) {
		case common:
			return vo;
		case grades:
			vo.setGrades(sp.value());
			return vo;
		case identifiter:
			vo.setIdentifiter(ip.value());
			return vo;
		case gradesAndIdentifiter:
			vo.setGrades(sp.value());
			vo.setIdentifiter(ip.value());
			return vo;
		default:
			return null;
		}
	}

	/**
	 * 取得权限类型
	 * 
	 * @param pp PublicPower 注解：公共权限
	 * @param sp SessionPower 注解：会员等级
	 * @param ip IdentifiterPower 注解：权限编号
	 * @return JWebPowerType
	 */
	private JWebPowerType getURLPowerType(PublicPower pp, SessionPower sp, IdentifiterPower ip) {
		if (null != sp && null != ip) {// 会员等级+权限编号
			return JWebPowerType.gradesAndIdentifiter;
		}
		if (null != sp) {// 仅会员等级
			return JWebPowerType.grades;
		}
		if (null != ip) {// 仅权限编号
			return JWebPowerType.identifiter;
		}
		if (null != pp) {// 默认公共路径
			return JWebPowerType.common;
		}
		return null;
	}

	private JWebPowerType getURLPowerType(SessionCodeAndIdentifiterCodeVO powerCode) {
		if (null != powerCode.getGrades() && null != powerCode.getIdentifiter()) {// 会员等级+权限编号
			return JWebPowerType.gradesAndIdentifiter;
		}
		if (null != powerCode.getGrades()) {// 仅会员等级
			return JWebPowerType.grades;
		}
		if (null != powerCode.getIdentifiter()) {// 仅权限编号
			return JWebPowerType.identifiter;
		}
		return powerCode.isPublic() ? JWebPowerType.common : null;
	}

}
