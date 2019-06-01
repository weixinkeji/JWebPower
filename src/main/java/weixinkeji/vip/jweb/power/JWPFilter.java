package weixinkeji.vip.jweb.power;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power._init.ReturnResultObject;
import weixinkeji.vip.jweb.power._init._0_LoadJWPConfig;
import weixinkeji.vip.jweb.power._init._1_IniMain;
import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.event.JWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.JWPGlobalEvent;
import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.tools.JWPRequestUrlTool;
import weixinkeji.vip.jweb.power.vo.JWPUserConfigVO;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;
import weixinkeji.vip.jweb.tools.JWPControllePrint;
import weixinkeji.vip.jweb.tools.JWPPathTool;

public class JWPFilter implements Filter {
	
	
	private String static_resources_prefix;
	private boolean console_print;
	private boolean dynamics_controller_url;
	private boolean free_url_open;
	
	/**
	 * 路径 - 与权限模型
	 */
	private Map<String, JWPControllerModel> jwebPowerControllerModel = new HashMap<>();
	private JWPStaticResourcesModel staticResourcesModel;
	
	// 全局事件
	private JWPGlobalEvent jwpGlobalEvent;
	// 事件
	private JWPControllerURLPowerEvent controllerUrlPowerEvent;
	
	// 用户权限 -接口
	private JWPUserInterface userPower;
	
	private JWPRequestUrlTool requestUrlTool;


	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		//加载用户配置
		JWPUserConfigVO config=this._1_loadConfig(fConfig);
		if(null==config) {
			return;
		}
		//开始执行初始化操作
		this._2_toStartInit(config);
		if (console_print) {
			JWPControllePrint.printMessage();
		}
		JWPControllePrint.clearMessage();
	}
	
	//加载配置文件
	private JWPUserConfigVO _1_loadConfig(FilterConfig fConfig) {
		JWPControllePrint.addMessage("[目录]加载框架配置文件");
		_0_LoadJWPConfig config=new _0_LoadJWPConfig("JWP.properties");
		JWPUserConfigVO configVo=config.getJWPUserConfigVO(fConfig.getServletContext().getContextPath());
		
		this.dynamics_controller_url =configVo.dynamics_controller_url;
		this.console_print =configVo.console_print;
		this.static_resources_prefix=configVo.static_resources_prefix;
		this.free_url_open=configVo.free_url_open;
		
		
		JWPControllePrint.addMessage("[目录]加载框架配置文件");
		if (null == configVo.scan_package || configVo.scan_package.length==0) {
			JWPControllePrint.addErrorMessage(
					"JWebPower启动失败，没有配置扫描路径。请在类路径下，创建属性文件【JWP.properties】，并设置一个键值对【scan_package=扫描的路径】", 1);
			return null;
		}
		// 扫描路径
		JWPControllePrint.addMessage("[目录]扫描类路径并加载类 完毕");
		return configVo;
	}
	
	//开始初始化
	private void _2_toStartInit(JWPUserConfigVO configVo) {
		JWPControllePrint.addMessage("[目录]环境初始完毕，开始业务处理");
		JWPControllePrint.addMessage("");
		JWPControllePrint.addMessage("[目录]启动配置工厂");
		//存在初始化结果
		ReturnResultObject rs=new ReturnResultObject();
		//开始初始化
		new _1_IniMain(configVo,rs);
		
		this.jwebPowerControllerModel=rs.getModel_controller();
		this.staticResourcesModel=rs.getModel_static();
		
		this.jwpGlobalEvent=rs.getEvent_global();// 全局事件
		this.controllerUrlPowerEvent=rs.getEvent_controller();// 事件
		
		this.userPower=rs.getUserPower();// 用户权限 -接口
		
		this.requestUrlTool=rs.getRequestUrlTool();
		
	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestURL = httpRequest.getRequestURI();
		if (!jwpGlobalEvent.jwpGlobal(chain, httpRequest, httpResponse, requestURL)) {
			return;
		}
		JWPUserPower powerCode = userPower.getUserPowerCode(httpRequest, httpResponse);
		// 静态资源处理
		if (requestURL.startsWith(this.static_resources_prefix)) {
			this.doStaticRequestURLLiseten(httpRequest, httpResponse, chain, requestURL, powerCode);
		} else {
			if (console_print) {
				this.doServerRequestURLLiseten_print(httpRequest, httpResponse, chain, requestURL, powerCode);
			} else {
				this.doServerRequestURLLiseten(httpRequest, httpResponse, chain, requestURL, powerCode);
			}
		}
	}

	// 静态资源监听 匹配与执行
	private void doStaticRequestURLLiseten(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final String url, final JWPUserPower powerCode) throws IOException, ServletException {
		String requestURL = this.requestUrlTool.formatStaticRequestURL(url);
		if (staticResourcesModel.doListen(chain, request, response, requestURL, null, powerCode)) {
			chain.doFilter(request, response);
		}
	}

	/**
	 * Controller入口权限监控
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param requestURL
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doServerRequestURLLiseten(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final String url, final JWPUserPower powerCode) throws IOException, ServletException {
		String requestURL = url.equals("/") ? url : this.requestUrlTool.formatRequestURL(url);
		String durl;
		JWPControllerModel powerModel = jwebPowerControllerModel.get(requestURL);
		if (null != requestURL && this.dynamics_controller_url && null == powerModel) {
			durl = DUrlTools.getUserURL(requestURL);
			if (null != durl) {
				requestURL = durl;
				powerModel = jwebPowerControllerModel.get(requestURL);
			}
		}
		if (!controllerUrlPowerEvent.jWebPower_start(chain, request, response, requestURL, powerModel, powerCode)) {
			return;
		}

		if (null == powerModel) {
			if (free_url_open&&controllerUrlPowerEvent.doOtherURL(request, response, requestURL, powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		}
		// 触发监听，并且监听结果不为true时，中断请求。
		if (powerModel.isHasListen
				&& !powerModel.listen.doMethod(chain, request, response, requestURL, powerModel, powerCode)) {
			return;
		}
		switch (powerModel.urlType) {
		case common:
			// 公共路径，得到用户的许可后，执行放行。
			if (controllerUrlPowerEvent.doPublicPower_success(request, response, requestURL, powerModel, powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		case grades:// 会话区
			if (null == powerCode || null == powerCode.grades) {
				return;
			}
			// 假设你的等级等级是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.grades)) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL, powerModel,
						powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel, powerCode);
			}
			return;
		case identifiter:// 编号区
			if (null == powerCode || null == powerCode.identifiter) {
				return;
			}
			// 假设你的权限编号是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInIdentifier(powerCode.identifiter)) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doIdentifiterPower_success(request, response, requestURL, powerModel,
						powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doIdentifiterPower_fail(request, response, requestURL, powerModel, powerCode);
			}
			return;
		case gradesAndIdentifiter:// 等级+编号
			if (null == powerCode || null == powerCode.grades || null == powerCode.identifiter) {
				return;
			}
			// 假设你的等级等级是1、权限编号是2。判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.grades) && powerModel.isInIdentifier(powerCode.identifiter)) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionAndIdentifierPower_success(request, response, requestURL,
						powerModel, powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionAndIdentifierPower_fail(request, response, requestURL, powerModel,
						powerCode);
			}
			return;
		case onlyListen:
			chain.doFilter(request, response);
			return;
//		default: // 不受控制的路径
//			if (controllerUrlPowerEvent.doOtherURL(request, response, requestURL,powerCode)) {
//				chain.doFilter(request, response);
//			}
//			return;
		}
	}

	/**
	 * Controller入口权限监控
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param requestURL
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doServerRequestURLLiseten_print(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final String url, final JWPUserPower powerCode) throws IOException, ServletException {
		String requestURL = url.equals("/") ? url : this.requestUrlTool.formatRequestURL(url);
		JWPControllerModel powerModel = jwebPowerControllerModel.get(requestURL);
		String durl;
		if (null != requestURL && this.dynamics_controller_url && null == powerModel) {
			durl = DUrlTools.getUserURL(requestURL);
			if (null != durl) {
				requestURL = durl;
				powerModel = jwebPowerControllerModel.get(requestURL);
			}
		}
		if (!controllerUrlPowerEvent.jWebPower_start(chain, request, response, requestURL, powerModel, powerCode)) {
			return;
		}
		//不在监听范围的请求路径 
		if (null == powerModel) {
			System.err.println("   不在监控内的权限 ！" + requestURL);
			if (free_url_open&&controllerUrlPowerEvent.doOtherURL(request, response, requestURL, powerCode)) {
				System.out.println("   用户强制执行放行！" + requestURL);
				chain.doFilter(request, response);
				return;
			}
			System.err.println("free_url_open=true,或用户强行中止不在监听范围的请求路径 不允许放行！");
			return;
		}
		// 触发监听，并且监听结果不为true时，中断请求。
		if (powerModel.isHasListen
				&& !powerModel.listen.doMethod(chain, request, response, requestURL, powerModel, powerCode)) {
			System.err.println("   触发监听，并且监听结果不为true，中断请求 ！" + requestURL);
			return;
		}
		switch (powerModel.urlType) {
		case common:
			// 公共路径，得到用户的许可后，执行放行。
			if (controllerUrlPowerEvent.doPublicPower_success(request, response, requestURL, powerModel, powerCode)) {
				System.out.println("   执行放行！" + requestURL);
				chain.doFilter(request, response);
			} else {
				System.err.println("   执行拦截！" + requestURL);
			}
			return;
		case grades:// 等级区
			if (null == powerCode || null == powerCode.grades) {
				return;
			}
			// 假设你的等级等级是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.grades)) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL, powerModel,
						powerCode)) {
					System.out.println("   执行放行！" + requestURL);
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel, powerCode);
				System.err.println("   执行拦截！" + requestURL);
			}
			return;
		case identifiter:// 编号区
			if (null == powerCode || null == powerCode.identifiter) {
				return;
			}
			// 假设你的权限编号是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInIdentifier(powerCode.identifiter)) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doIdentifiterPower_success(request, response, requestURL, powerModel,
						powerCode)) {
					System.out.println("   执行放行！" + requestURL);
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doIdentifiterPower_fail(request, response, requestURL, powerModel, powerCode);
				System.err.println("   执行拦截！" + requestURL);
			}
			return;
		case gradesAndIdentifiter:// 等级+编号
			if (null == powerCode || null == powerCode.grades || null == powerCode.identifiter) {
				return;
			}
			// 假设你的等级等级是1、权限编号是2。判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.grades) && powerModel.isInIdentifier(powerCode.identifiter)) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionAndIdentifierPower_success(request, response, requestURL,
						powerModel, powerCode)) {
					System.out.println("   执行放行！" + requestURL);
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionAndIdentifierPower_fail(request, response, requestURL, powerModel,
						powerCode);
				System.err.println("   执行拦截！" + requestURL);
			}
			return;
		case onlyListen:
			chain.doFilter(request, response);
			return;
//		default: // 不受控制的路径
//			System.err.println("   不受控制的路径！"+requestURL);
//			if (controllerUrlPowerEvent.doOtherURL(request, response, requestURL,powerCode)) {
//				System.out.println("   执行放行！"+requestURL);
//				chain.doFilter(request, response);
//			}
//			System.err.println("   执行拦截！"+requestURL);
//			return;
		}
	}
}
