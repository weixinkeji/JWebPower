package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIdentifiter;
import weixinkeji.vip.jweb.power.ann.JWPPublic;
import weixinkeji.vip.jweb.power.ann.JWPSession;
import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.model.DUrlPools;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;
import weixinkeji.vip.jweb.power.tools.JWPPathTool;
import weixinkeji.vip.jweb.power.vo.JWPExpressConfigVO;

/**
 * 检出所有权限编号、权限等级、会话区、放行区
 * 
 * @author wangchunzi
 *
 */
public class _IniJWPPowerCode extends _InitTool {
	private JWPSystemInterfaceConfig siConfig;
	private _IniJWPExpress express;

	private InMethodPowerCode mp = new InMethodPowerCode();
	private InClassPowerCode cp = new InClassPowerCode();
	private InExcpressPowerCode ep = new InExcpressPowerCode();

	// 路径与类的关系
	private Map<String, Class<?>> urlAndClass = new HashMap<>();
	// 路径与方法的关系
	private Map<String, Method> urlAndMethod = new HashMap<>();

	/**
	 * @param list 扫描到的类
	 */
	_IniJWPPowerCode(List<Class<?>> list, JWPSystemInterfaceConfig siConfig, _IniJWPExpress express) {
		super(list);
		this.express = express;
	}

	public void _1_initCMPower() {
		String headUrl;
		String methodUrl;
		String requestUrl;
		char[] requestUrl2;
		for (Class<?> c : list) {
			if (null != (headUrl = siConfig.getURLByClass(c))) {
				cp.main(c);// 注解在类上的权限
				this.urlAndClass.put(headUrl, c);// 记录下注解在类上的 请求路径
				for (Method m : c.getMethods()) {
					if (null != (methodUrl = siConfig.getURLByMethod(m))) {
						requestUrl = JWPPathTool.joinUrl(headUrl, methodUrl);
						if (requestUrl.contains("{")) {// 表示动态路径
							requestUrl2 = DUrlTools.formatURL(requestUrl);// 格式化路径
							requestUrl = new String(requestUrl2);
							DUrlPools.addDUrl(requestUrl2);// 加入请求路径的缓存池。方便框架校对路径
						}
						if (mp.main(m)) {// 注解在方法上的权限
							// 记录下请求路径与方法的关联
							urlAndMethod.put(requestUrl, m);// 拼接请求路径与缓存
						}
					}
				}
			}
		}
	}

	public void _2_initExcpressPower() {
		// Controller表达式
		JWPExpressConfigVO vo = this.express.getJWPControllerURLExpresstion();
		JWPExpressionTool tool=this.express.getJWPExpressionTool();
		
		// 找到所有与url有关的表达式所附加的【权限等级】权限。
		for (String express :vo.getSessionPowerExpresstion()) {
			
		}

	}

}

/**
 * 传入一个方法，自动找所有注解在此方法上的权限 。
 * 
 * @author wangchunzi
 *
 */
class InMethodPowerCode {
	// 在方法上的 权限编号--Controller
	private Map<Method, String[]> inMethod_identifiter = new HashMap<>();
	// 在方法上的 权限等级--Controller
	private Map<Method, String[]> inMethod_grades = new HashMap<>();
	// 在方法上的 会话--Controller
	private Map<Method, Boolean> inMethod_session = new HashMap<>();
	// 在方法上的 放行区--Controller
	private Map<Method, Boolean> inMethod_public = new HashMap<>();

	/**
	 * 分析 注解在方法上的权限
	 * 
	 * @param m 方法
	 * 
	 * @return boolean true:找到方法上的权限；false：没找到方法上的权限
	 */
	public boolean main(Method m) {
		int count = 0;
		JWPIdentifiter id = m.getAnnotation(JWPIdentifiter.class);
		if (null != id) {
			count++;
			inMethod_identifiter.put(m, id.value());
		}
		JWPGrades grades = m.getAnnotation(JWPGrades.class);
		if (null != grades) {
			count++;
			inMethod_identifiter.put(m, grades.value());
		}
		JWPSession session = m.getAnnotation(JWPSession.class);
		if (null != session) {
			count++;
			inMethod_session.put(m, true);
		}
		JWPPublic ppublic = m.getAnnotation(JWPPublic.class);
		if (null != ppublic) {
			count++;
			inMethod_public.put(m, true);
		}
		return count > 0;
	}

	/**
	 * 设置权限编号
	 * 
	 * @param m    方法
	 * @param code 权限编号
	 */
	public void setIdentifiter(Method m, String[] code) {
		this.inMethod_identifiter.put(m, code);
	}

	/**
	 * 取得权限编号
	 * 
	 * @param m 方法
	 * @return String[]
	 */
	public String[] getIdentifiter(Method m) {
		return this.inMethod_identifiter.get(m);
	}

	/**
	 * 设置权限等级
	 * 
	 * @param m 方法
	 * @param   String[]
	 */
	public void setGrades(Method m, String[] code) {
		this.inMethod_grades.put(m, code);
	}

	/**
	 * 取得权限等级
	 * 
	 * @param m 方法
	 * @return String[]
	 */
	public String[] getGrades(Method m) {
		return this.inMethod_grades.get(m);
	}

	/**
	 * 设置会话
	 * 
	 * @param m 方法
	 * @return code 是否会话区
	 */
	public void setSession(Method m, Boolean code) {
		this.inMethod_session.put(m, code);
	}

	/**
	 * 取得会话
	 * 
	 * @param m
	 * @return
	 */
	public Boolean getSession(Method m) {
		return this.inMethod_session.get(m);
	}

	/**
	 * 设置公共区
	 * 
	 * @param m
	 * @return
	 */
	public void setPublic(Method m, Boolean code) {
		this.inMethod_public.put(m, code);
	}

	/**
	 * 取得公共区
	 * 
	 * @param m
	 * @return
	 */
	public Boolean getPublic(Method m) {
		return this.inMethod_public.get(m);
	}

}

class InClassPowerCode {
	// 在类上的 权限编号--Controller
	private Map<Class<?>, String[]> inClass_identifiter = new HashMap<>();
	// 在类上的 权限等级--Controller
	private Map<Class<?>, String[]> inClass_grades = new HashMap<>();
	// 在类上的 会话--Controller
	private Map<Class<?>, Boolean> inClass_session = new HashMap<>();
	// 在类上的 放行区--Controller
	private Map<Class<?>, Boolean> inClass_public = new HashMap<>();

	public void main(Class<?> c) {
		JWPIdentifiter id = c.getAnnotation(JWPIdentifiter.class);
		if (null != id) {
			inClass_identifiter.put(c, id.value());
		}
		JWPGrades grades = c.getAnnotation(JWPGrades.class);
		if (null != grades) {
			inClass_grades.put(c, grades.value());
		}
		JWPSession session = c.getAnnotation(JWPSession.class);
		inClass_session.put(c, null != session);

		JWPPublic ppublic = c.getAnnotation(JWPPublic.class);
		inClass_public.put(c, null != ppublic);
	}

	/**
	 * 设置权限编号
	 * 
	 * @param c    类
	 * @param code 权限编号
	 */
	public void setIdentifiter(Class<?> c, String[] code) {
		this.inClass_identifiter.put(c, code);
	}

	/**
	 * 取得权限编号
	 * 
	 * @param c 类
	 * @return String[]
	 */
	public String[] getIdentifiter(Class<?> c) {
		return this.inClass_identifiter.get(c);
	}

	/**
	 * 设置权限等级
	 * 
	 * @param c 类
	 * @param   String[]
	 */
	public void setGrades(Class<?> c, String[] code) {
		this.inClass_grades.put(c, code);
	}

	/**
	 * 取得权限等级
	 * 
	 * @param c 类
	 * @return String[]
	 */
	public String[] getGrades(Class<?> c) {
		return this.inClass_grades.get(c);
	}

	/**
	 * 设置会话
	 * 
	 * @param c    类
	 * @param code 是否会话区
	 */
	public void setSession(Class<?> m, Boolean code) {
		this.inClass_session.put(m, code);
	}

	/**
	 * 取得会话
	 * 
	 * @param c 类
	 * @return Boolean
	 */
	public Boolean getSession(Class<?> m) {
		return this.inClass_session.get(m);
	}

	/**
	 * 设置公共区
	 * 
	 * @param c    类
	 * @param code 是否公共区
	 */
	public void setPublic(Class<?> m, Boolean code) {
		this.inClass_public.put(m, code);
	}

	/**
	 * 取得公共区
	 * 
	 * @param c 类
	 * @return Boolean
	 */
	public Boolean getPublic(Class<?> m) {
		return this.inClass_public.get(m);
	}
}

class InExcpressPowerCode {
	// 在类上的 权限编号--Controller
	private Map<String, String[]> inExcpress_identifiter = new HashMap<>();
	// 在类上的 权限等级--Controller
	private Map<String, String[]> inExcpress_grades = new HashMap<>();
	// 在类上的 会话--Controller
	private Map<String, Boolean> inExcpress_session = new HashMap<>();
	// 在类上的 放行区--Controller
	private Map<String, Boolean> inExcpress_public = new HashMap<>();

	/**
	 * 取出请求路径 在 检查表达式中的权限
	 * 
	 * @param vo
	 * @param requestUrl
	 */
	public void main(JWPExpressConfigVO vo, String requestUrl,JWPExpressionTool tool) {
		String[] powerCode;
		for (String expUrl : vo.getIdentifiterPowerExpression()) {
			//如果不为null,表示有权限编号
			if(null!=(powerCode=tool.isIdentifiterPower(expUrl,requestUrl))) {
				inExcpress_identifiter.put(requestUrl, powerCode);
			}
			
			//如果不为null,表示有权限等级
			if(null!=(powerCode=tool.isGradePower(expUrl,requestUrl))) {
				inExcpress_identifiter.put(requestUrl, powerCode);
			}
			
			
			
			
			
		}
	}

	/**
	 * 设置权限编号
	 * 
	 * @param c    类
	 * @param code 权限编号
	 */
	public void setIdentifiter(String m, String[] code) {
		this.inExcpress_identifiter.put(m, code);
	}

	/**
	 * 取得权限编号
	 * 
	 * @param c 类
	 * @return String[]
	 */
	public String[] getIdentifiter(String m) {
		return this.inExcpress_identifiter.get(m);
	}

	/**
	 * 设置权限等级
	 * 
	 * @param c 类
	 * @param   String[]
	 */
	public void setGrades(String m, String[] code) {
		this.inExcpress_grades.put(m, code);
	}

	/**
	 * 取得权限等级
	 * 
	 * @param c 类
	 * @return String[]
	 */
	public String[] getGrades(String m) {
		return this.inExcpress_grades.get(m);
	}

	/**
	 * 设置会话
	 * 
	 * @param c    类
	 * @param code 是否会话区
	 */
	public void setSession(String m, Boolean code) {
		this.inExcpress_session.put(m, code);
	}

	/**
	 * 取得会话
	 * 
	 * @param c 类
	 * @return Boolean
	 */
	public Boolean getSession(String m) {
		return this.inExcpress_session.get(m);
	}

	/**
	 * 设置公共区
	 * 
	 * @param c    类
	 * @param code 是否公共区
	 */
	public void setPublic(String m, Boolean code) {
		this.inExcpress_public.put(m, code);
	}

	/**
	 * 取得公共区
	 * 
	 * @param c 类
	 * @return Boolean
	 */
	public Boolean getPublic(String m) {
		return this.inExcpress_public.get(m);
	}
}
