package weixinkeji.vip.jweb.power;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power._init._JWPConfigFactory;
import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.event.JWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.JWPGlobalEvent;
import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;
import weixinkeji.vip.jweb.power.vo.JWPRequestUrlVO;
import weixinkeji.vip.jweb.scan.JWPScanClassFactory;
import weixinkeji.vip.jweb.tools.JWPControllePrint;
import weixinkeji.vip.jweb.tools.JWPPathTool;
import weixinkeji.vip.jweb.tools.JWPPropertiesTool;

public class JWPFilter implements Filter {

	/**
	 * 路径 - 与权限模型
	 */
	private Map<String, JWPControllerModel> jwebPowerControllerModel = new HashMap<>();
	private String static_resources_prefix;
//---------方法区---------
	//全局事件
	private JWPGlobalEvent jwpGlobalEvent;
	// 事件
	private JWPControllerURLPowerEvent controllerUrlPowerEvent;
	// 用户权限 -接口
	private JWPUserInterface userPower;

	private JWPStaticResourcesModel staticResourcesModel;
	private JWPRequestUrlVO requestUrlTool;
	
	private boolean console_print;
	private boolean dynamics_controller_url;
	
	//对静态资源前缀路径进行格式化
	private void initStaticUrl(String ContextPath,final String url) {
		if(null==url||url.isEmpty()){
			static_resources_prefix=ContextPath+"/static/";
			JWPControllePrint.addErrorMessage("没找找到配置文件中的键值对[static_resources_prefix],或键的值为空。系统自动采用 /static/ 作为静态url的前缀！",1);
		}else if(!url.startsWith("/")) {
			static_resources_prefix=ContextPath+"/"+url;
		}else {
			static_resources_prefix=ContextPath+url;
		}
		
	}
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		JWPControllePrint.addMessage("[目录]加载框架配置文件");
		Map<String, String> configMap = new JWPPropertiesTool()
				.loadPropertiesToMap(new File(JWPPathTool.getMyProjectPath("JWP.properties")));
		this.dynamics_controller_url=null==configMap.get("dynamics_controller_url")?true:Boolean.parseBoolean(configMap.get("dynamics_controller_url"));
		this.console_print=null==configMap.get("console_print")?false:Boolean.parseBoolean(configMap.get("console_print"));
		String path = configMap.get("scan_package");
		JWPControllePrint.addMessage("[目录]设置配-扫描的路径："+path+" 完毕");
		
		this.initStaticUrl(fConfig.getServletContext().getContextPath(),configMap.get("static_resources_prefix"));
		JWPControllePrint.addMessage("[目录]加载框架配置文件");
		
		if (null==configMap||null == path || path.isEmpty()) {
			JWPControllePrint.addErrorMessage("JWebPower启动失败，没有配置扫描路径。请在类路径下，创建属性文件【jwebPower.properties】，并设置一个键值对【scan_package=扫描的路径】", 1);
			return;
		}
		// 扫描路径
		List<Class<?>> classList = JWPScanClassFactory.getClassByFilePath(path.split("[,，]{1}"));
		JWPControllePrint.addMessage("[目录]扫描类路径并加载类 完毕");
		JWPControllePrint.addMessage("[目录]环境初始完毕，开始业务处理");
		JWPControllePrint.addMessage("");
		JWPControllePrint.addMessage("[目录]启动配置工厂");
		// 启动配置工厂
		_JWPConfigFactory tempConfigFactory = new _JWPConfigFactory(classList);
		requestUrlTool = tempConfigFactory.getRequestURLVO(fConfig.getServletContext().getContextPath());
		
//从集合中，找到我们的配置实例
		// 事件触发-实现对象
		jwpGlobalEvent=tempConfigFactory.getJWPGlobalEvent();
		controllerUrlPowerEvent = tempConfigFactory.getJWPControllerURLPowerEvent();
		JWPControllePrint.addMessage("[目录]事件触发-实现对象 处理完毕");
		// 设置用户类与方法上注解的权限的模型
		tempConfigFactory.setControllerPowerModel(jwebPowerControllerModel);
		JWPControllePrint.addMessage("[目录]设置用户类与方法上注解的权限的模型 处理完毕");
		// 用户的静态资源 检验模型
		staticResourcesModel =tempConfigFactory.getJWebPowerStaticResourcesModel();
		JWPControllePrint.addMessage("[目录]设置用户的静态资源 检验模型 处理完毕");
		
		// 获取【等级等级】、【权限编号】的用户接口
		userPower = tempConfigFactory.getJWPUserInterface();
		JWPControllePrint.addMessage("[目录]设置  获取【权限等级】、【权限编号】的用户接口的实现类  处理完毕"+userPower.getClass().getName());
		
		if(console_print) {
			JWPControllePrint.printMessage();
		}
		JWPControllePrint.clearMessage();
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
		if(!jwpGlobalEvent.jwpGlobal(chain, httpRequest, httpResponse, requestURL)) {
			return;
		}
		JWPCodeVO powerCode = userPower.getUserPowerCode(httpRequest, httpResponse);
		// 静态资源处理
		if (requestURL.startsWith(this.static_resources_prefix)) {
			this.doStaticRequestURLLiseten(httpRequest, httpResponse, chain, requestURL, powerCode);
		} else {
			if(console_print) {
				this.doServerRequestURLLiseten_print(httpRequest, httpResponse, chain, requestURL,powerCode);
			}else {
				this.doServerRequestURLLiseten(httpRequest, httpResponse, chain, requestURL,powerCode);
			}
		}
	}
	//静态资源监听 匹配与执行
	private void doStaticRequestURLLiseten(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final String url,final JWPCodeVO powerCode)  throws IOException, ServletException {
		String requestURL = this.requestUrlTool.formatStaticRequestURL(url);
		if(staticResourcesModel.doListen(chain, request, response, requestURL, null, powerCode)) {
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
			final FilterChain chain, final String url,final JWPCodeVO powerCode) throws IOException, ServletException {
		String requestURL = this.requestUrlTool.formatRequestURL(url);
		String durl;
		JWPControllerModel powerModel = jwebPowerControllerModel.get(requestURL);
		if(this.dynamics_controller_url&&null==powerModel) {
			durl=DUrlTools.getUserURL(requestURL);
			if(null!=durl) {
				requestURL=durl;
				powerModel = jwebPowerControllerModel.get(requestURL);
			}
		}
		if (!controllerUrlPowerEvent.jWebPower_start(chain,request, response, requestURL,powerModel,powerCode)) {
			return;
		}
		
		if (null == powerModel) {
			if (controllerUrlPowerEvent.doOtherURL(request, response, requestURL,powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		}
		//触发监听，并且监听结果不为true时，中断请求。
		if(powerModel.isHasListen&&!powerModel.listen.doMethod(chain,request, response, requestURL,powerModel,powerCode)) {
			return;
		}
		switch (powerModel.urlType) {
		case common:
			// 公共路径，得到用户的许可后，执行放行。
			if (controllerUrlPowerEvent.doPublicPower_success(request, response, requestURL,powerModel,powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		case grades:// 会话区
			if(null==powerCode||null==powerCode.getGrades()) {
				return;
			}
			// 假设你的等级等级是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.getGrades())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL,powerModel,powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL,powerModel,powerCode);
			}
			return;
		case identifiter:// 编号区
			if(null==powerCode||null==powerCode.getIdentifiter()) {
				return;
			}
			// 假设你的权限编号是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInIdentifier(powerCode.getIdentifiter())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doIdentifiterPower_success(request, response, requestURL,powerModel,powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doIdentifiterPower_fail(request, response, requestURL,powerModel,powerCode);
			}
			return;
		case gradesAndIdentifiter:// 等级+编号
			if(null==powerCode||null==powerCode.getGrades()||null==powerCode.getIdentifiter()) {
				return;
			}
			// 假设你的等级等级是1、权限编号是2。判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.getGrades()) && powerModel.isInIdentifier(powerCode.getIdentifiter())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionAndIdentifierPower_success(request, response, requestURL,powerModel,powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionAndIdentifierPower_fail(request, response, requestURL,powerModel,powerCode);
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
			final FilterChain chain,final String url,final JWPCodeVO powerCode ) throws IOException, ServletException {
		String requestURL = this.requestUrlTool.formatRequestURL(url);
		JWPControllerModel powerModel = jwebPowerControllerModel.get(requestURL);
		String durl;
		if(this.dynamics_controller_url&&null==powerModel) {
			durl=DUrlTools.getUserURL(requestURL);
			if(null!=durl) {
				requestURL=durl;
				powerModel = jwebPowerControllerModel.get(requestURL);
			}
		}
		if (!controllerUrlPowerEvent.jWebPower_start(chain,request, response, requestURL,powerModel,powerCode)) {
			return;
		}
		if (null == powerModel) {
			System.err.println("   不在监控内的权限 ！"+requestURL);
			if (controllerUrlPowerEvent.doOtherURL(request, response, requestURL,powerCode)) {
				System.out.println("   用户强制执行放行！"+requestURL);
				chain.doFilter(request, response);
			}
			return;
		}
		//触发监听，并且监听结果不为true时，中断请求。
		if(powerModel.isHasListen&&!powerModel.listen.doMethod(chain,request, response, requestURL,powerModel,powerCode)) {
			System.err.println("   触发监听，并且监听结果不为true，中断请求 ！"+requestURL);
			return;
		}
		switch (powerModel.urlType) {
		case common:
			// 公共路径，得到用户的许可后，执行放行。
			if (controllerUrlPowerEvent.doPublicPower_success(request, response, requestURL,powerModel,powerCode)) {
				System.out.println("   执行放行！"+requestURL);
				chain.doFilter(request, response);
			}else {
				System.err.println("   执行拦截！"+requestURL);
			}
			return;
		case grades:// 等级区
			if(null==powerCode||null==powerCode.getGrades()) {
				return;
			}
			// 假设你的等级等级是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.getGrades())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL,powerModel,powerCode)) {
					System.out.println("   执行放行！"+requestURL);
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL,powerModel,powerCode);
				System.err.println("   执行拦截！"+requestURL);
			}
			return;
		case identifiter:// 编号区
			if(null==powerCode||null==powerCode.getIdentifiter()) {
				return;
			}
			// 假设你的权限编号是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInIdentifier(powerCode.getIdentifiter())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doIdentifiterPower_success(request, response, requestURL,powerModel,powerCode)) {
					System.out.println("   执行放行！"+requestURL);
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doIdentifiterPower_fail(request, response, requestURL,powerModel,powerCode);
				System.err.println("   执行拦截！"+requestURL);
			}
			return;
		case gradesAndIdentifiter:// 等级+编号
			if(null==powerCode||null==powerCode.getGrades()||null==powerCode.getIdentifiter()) {
				return;
			}
			// 假设你的等级等级是1、权限编号是2。判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.getGrades()) && powerModel.isInIdentifier(powerCode.getIdentifiter())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionAndIdentifierPower_success(request, response, requestURL,powerModel,powerCode)) {
					System.out.println("   执行放行！"+requestURL);
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionAndIdentifierPower_fail(request, response, requestURL,powerModel,powerCode);
				System.err.println("   执行拦截！"+requestURL);
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
