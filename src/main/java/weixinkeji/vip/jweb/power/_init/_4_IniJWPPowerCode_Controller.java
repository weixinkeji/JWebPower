package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIdentifiter;
import weixinkeji.vip.jweb.power.ann.JWPPublic;
import weixinkeji.vip.jweb.power.ann.JWPSession;
import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.model.DUrlPools;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;
import weixinkeji.vip.jweb.power.tools.JWPPathTool;
import weixinkeji.vip.jweb.power.tools.JWPTool;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;
import weixinkeji.vip.jweb.power.vo.JWPExpressConfigVO;
import weixinkeji.vip.jweb.power.vo.JWPExpressVO;

/**
 * 检出所有注解在方法、类或 表达式上 的权限
 * 
 * @author wangchunzi
 *
 */
public class _4_IniJWPPowerCode_Controller extends __InitTool {
	private JWPSystemInterfaceConfig siConfig;
	private _3_IniJWPExpress express;

	private InMethodPowerCode mp = new InMethodPowerCode();
	private InClassPowerCode cp = new InClassPowerCode();
	private InExcpressPowerCode ep = new InExcpressPowerCode();
	private InExcpressDirectPowerCode edp = new InExcpressDirectPowerCode();

	// 路径与类的关系
	private Map<String, Class<?>> urlAndClass = new HashMap<>();
	// 路径与方法的关系
	private Map<String, MethodAndClass> urlAndMethodAndClass = new HashMap<>();

	private boolean isMethodUrl = false;

	/**
	 * @param list 扫描到的类
	 */
	_4_IniJWPPowerCode_Controller(List<Class<?>> list, JWPSystemInterfaceConfig siConfig, _3_IniJWPExpress express) {
		super(list);
		this.siConfig = siConfig;
		this.express = express;
		this._0_initExcpressPower();// 表达式上，所有 表达直接路径的权限
		this._1_initCMPower();// 注解在方法、类上的所有路径的权限
		this._2_initExcpressPower();// 表达式上的所有权限。
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
						if (null != siConfig.getURLByMethod(m)) {// 注解在方法上路径
							isMethodUrl = true;
							// 记录下请求路径与方法的关联
							mp.main(m);// 标注在方法上的权限
							urlAndMethodAndClass.put(requestUrl, new MethodAndClass(m, c));// 拼接请求路径与缓存
						}
					}
				}
			}
		}
	}

	// 处理表达式权限
	public void _0_initExcpressPower() {
		// Controller表达式
		JWPExpressConfigVO vo = this.express.getJWPControllerURLExpresstion();
		JWPExpressionTool tool = this.express.getJWPExpressionTool();
		edp.main(vo, tool);
	}

	// 处理表达式权限
	public void _2_initExcpressPower() {
		// Controller表达式
		JWPExpressConfigVO vo = this.express.getJWPControllerURLExpresstion();
		JWPExpressionTool tool = this.express.getJWPExpressionTool();

		// 找到所有与url有关的表达式所附加的【权限等级】权限。
		if (this.urlAndMethodAndClass.size() > 0) {// 表示 请求路径= 类（url)+方法（url)
			for (Map.Entry<String, MethodAndClass> kv : urlAndMethodAndClass.entrySet()) {
				ep.main(vo, kv.getKey(), tool);
			}
		} else {// 表示 请求路径= 类（url)
			for (Map.Entry<String, Class<?>> kv : this.urlAndClass.entrySet()) {
				ep.main(vo, kv.getKey(), tool);
			}
		}

	}

	public boolean isMethodUrl() {
		return isMethodUrl;
	}

	public InMethodPowerCode getMp() {
		return mp;
	}

	public InClassPowerCode getCp() {
		return cp;
	}

	public InExcpressPowerCode getEp() {
		return ep;
	}

	public Map<String, Class<?>> getUrlAndClass() {
		return urlAndClass;
	}

	public Map<String, MethodAndClass> getUrlAndMethodAndClass() {
		return urlAndMethodAndClass;
	}

	public InExcpressDirectPowerCode getEdp() {
		return edp;
	}
}

class MethodAndClass {
	public final Method m;
	public final Class<?> c;

	public MethodAndClass(Method m, Class<?> c) {
		this.m = m;
		this.c = c;
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
	private Map<Method, String[]> inIdentifiter = new HashMap<>();
	// 在方法上的 权限等级--Controller
	private Map<Method, String[]> inGrades = new HashMap<>();
	// 在方法上的 会话--Controller
	private Map<Method, Boolean> inSession = new HashMap<>();
	// 在方法上的 放行区--Controller
	private Map<Method, Boolean> inPublic = new HashMap<>();

	/**
	 * 分析 注解在方法上的权限
	 * 
	 * @param m 方法
	 * 
	 * @return boolean true:找到方法上的权限；false：没找到方法上的权限
	 */
	public boolean main(Method m) {
		int count = 0;
		JWPIdentifiter identifiter = m.getAnnotation(JWPIdentifiter.class);
		if (null != identifiter) {
			count++;
			inIdentifiter.put(m, JWPTool.formatMyArray(identifiter.value()));
		}
		JWPGrades grades = m.getAnnotation(JWPGrades.class);
		if (null != grades) {
			count++;
			inGrades.put(m, JWPTool.formatMyArray(grades.value()));
		}
		JWPSession session = m.getAnnotation(JWPSession.class);
		if (null != session) {
			count++;
			inSession.put(m, true);
		}
		JWPPublic ppublic = m.getAnnotation(JWPPublic.class);
		if (null != ppublic) {
			count++;
			inPublic.put(m, true);
		}
		return count > 0;
	}

	/**
	 * 取得方法关联的权限
	 * 
	 * @param m 控制类
	 * @return JWPCodeVO
	 */
	public JWPCodeVO getPowerCode(Method m) {
		return new JWPCodeVO(null != this.inPublic.get(m), null != this.inSession.get(m), this.inGrades.get(m),
				this.inIdentifiter.get(m));
	}
}

class InClassPowerCode {
	// 在类上的 权限编号--Controller
	private Map<Class<?>, String[]> inIdentifiter = new HashMap<>();
	// 在类上的 权限等级--Controller
	private Map<Class<?>, String[]> inGrades = new HashMap<>();
	// 在类上的 会话--Controller
	private Map<Class<?>, Boolean> inSession = new HashMap<>();
	// 在类上的 放行区--Controller
	private Map<Class<?>, Boolean> inPublic = new HashMap<>();

	public void main(Class<?> c) {
		JWPIdentifiter identifiter = c.getAnnotation(JWPIdentifiter.class);
		if (null != identifiter) {
			inIdentifiter.put(c, JWPTool.formatMyArray(identifiter.value()));
		}
		JWPGrades grades = c.getAnnotation(JWPGrades.class);
		if (null != grades) {
			inGrades.put(c, JWPTool.formatMyArray(grades.value()));
		}
		JWPSession session = c.getAnnotation(JWPSession.class);
		inSession.put(c, null != session);

		JWPPublic ppublic = c.getAnnotation(JWPPublic.class);
		inPublic.put(c, null != ppublic);
	}

	/**
	 * 取得类关联的权限
	 * 
	 * @param c 控制类
	 * @return JWPCodeVO
	 */
	public JWPCodeVO getPowerCode(Class<?> c) {
		return new JWPCodeVO(true== this.inPublic.get(c),true== this.inSession.get(c), this.inGrades.get(c),
				this.inIdentifiter.get(c));
	}
}

/**
 * 直接权限。非表达式
 * 
 * @author wangchunzi
 *
 */
class InExcpressDirectPowerCode {
	// 在类上的 权限编号--Controller
	private Map<String, String[]> inIdentifiter = new HashMap<>();
	// 在类上的 权限等级--Controller
	private Map<String, String[]> inGrades = new HashMap<>();
	// 在类上的 会话--Controller
	private Map<String, Boolean> inSession = new HashMap<>();
	// 在类上的 放行区--Controller
	private Map<String, Boolean> inPublic = new HashMap<>();
	private Set<String> expressUrl = new HashSet<>();

	/**
	 * 取出请求路径 在 检查表达式中的权限
	 * 
	 * @param vo
	 * @param requestUrl
	 */
	public void main(JWPExpressConfigVO expressVo, JWPExpressionTool tool) {
		JWPExpressVO vo;
		// 权限编号的 直接权限
		for (String expUrl : expressVo.getIdentifiterPowerExpression()) {
			expUrl = DUrlTools.formatURLAndCacheUrl(expUrl);// 处理路径。如果是动态路径，格式成框架处理用的路径。
			if (null != (vo = tool.getPowerUrl(expUrl))) {
				inIdentifiter.put(vo.getExpress(), vo.getValues());
				expressUrl.add(expUrl);
			}
		}
		// 权限等级的 直接权限
		for (String expUrl : expressVo.getGradesExcpresstion()) {
			expUrl = DUrlTools.formatURLAndCacheUrl(expUrl);// 处理路径。如果是动态路径，格式成框架处理用的路径。
			if (null != (vo = tool.getPowerUrl(expUrl))) {
				inGrades.put(vo.getExpress(), vo.getValues());
				expressUrl.add(expUrl);
			}
		}
		// 会话区的 直接权限
		for (String expUrl : expressVo.getSessionPowerExpresstion()) {
			expUrl = DUrlTools.formatURLAndCacheUrl(expUrl);// 处理路径。如果是动态路径，格式成框架处理用的路径。
			if (null != (vo = tool.getPowerUrl(expUrl))) {
				this.inSession.put(vo.getExpress(), true);
				expressUrl.add(expUrl);
			}
		}
		// 公共区的 直接权限
		for (String expUrl : expressVo.getPublicPowerExpresstion()) {
			expUrl = DUrlTools.formatURLAndCacheUrl(expUrl);// 处理路径。如果是动态路径，格式成框架处理用的路径。
			if (null != (vo = tool.getPowerUrl(expUrl))) {
				this.inPublic.put(vo.getExpress(), true);
				expressUrl.add(expUrl);
			}
		}

	}

	/**
	 * 取得所有直接路径的权限
	 * 
	 * @return Map
	 */
	public Map<String, JWPCodeVO> getMethodPowerCode() {
		Map<String, JWPCodeVO> map = new HashMap<>();
		for (String requestUrl : this.expressUrl) {
			map.put(requestUrl,
					new JWPCodeVO(null != this.inPublic.get(requestUrl), null != this.inSession.get(requestUrl),
							this.inGrades.get(requestUrl), this.inIdentifiter.get(requestUrl)));
		}
		return map;
	}
}

class InExcpressPowerCode {
	// 在表达式上的 权限编号--Controller
	private Map<String, String[]> inIdentifiter = new HashMap<>();
	// 在表达式上的 权限等级--Controller
	private Map<String, String[]> inGrades = new HashMap<>();
	// 在表达式上的 会话--Controller
	private Map<String, Boolean> inSession = new HashMap<>();
	// 在表达式上的 放行区--Controller
	private Map<String, Boolean> inPublic = new HashMap<>();

	/**
	 * 取出请求路径 在 检查表达式中的权限
	 * 
	 * @param vo
	 * @param requestUrl
	 */
	public void main(JWPExpressConfigVO vo, String requestUrl, JWPExpressionTool tool) {
		String[] powerCode;
		String[] hasPowerCode;
		for (String expUrl : vo.getIdentifiterPowerExpression()) {//编号
			// 如果不为null,表示有权限编号
			if (null != (powerCode = tool.isIdentifiterPower(expUrl, requestUrl))) {
				hasPowerCode=inIdentifiter.get(requestUrl);
				if(null!=hasPowerCode) {//如果多个表达都与xx请求路径匹配成功，则进行权限融合
					powerCode=JWPTool.mergeStringArray(powerCode,hasPowerCode);
				}
				inIdentifiter.put(requestUrl, powerCode);
			}
		}
		for (String expUrl : vo.getGradesExcpresstion()) {//等级
			// 如果不为null,表示有权限等级
			if (null != (powerCode = tool.isGradePower(expUrl, requestUrl))) {
				hasPowerCode=inGrades.get(requestUrl);
				if(null!=hasPowerCode) {//如果多个表达都与xx请求路径匹配成功，则进行权限融合
					powerCode=JWPTool.mergeStringArray(powerCode,hasPowerCode);
				}
				inGrades.put(requestUrl, powerCode);
			}
		}
		for (String expUrl : vo.getSessionPowerExpresstion()) {//会话
			// 如果不为null,表示有会话权限
			if (tool.isSessionPower(expUrl, requestUrl)) {
				inSession.put(requestUrl, true);
			}
		}
		
		for (String expUrl : vo.getPublicPowerExpresstion()) {//放行区
			// 如果不为null,表示有公共权限
			if (tool.isPublicPower(expUrl, requestUrl)) {
				inPublic.put(requestUrl, true);
			}
		}
	}

	/**
	 * 取得请求路径关联的权限
	 * 
	 * @param requestUrl 请求路径
	 * @return JWPCodeVO
	 */
	public JWPCodeVO getPowerCode(String requestUrl) {
		return new JWPCodeVO(null != this.inPublic.get(requestUrl), null != this.inSession.get(requestUrl),
				this.inGrades.get(requestUrl), this.inIdentifiter.get(requestUrl));
	}
}
