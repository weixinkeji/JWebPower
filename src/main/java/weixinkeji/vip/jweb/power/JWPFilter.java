package weixinkeji.vip.jweb.power;

import java.io.IOException;
import java.util.Arrays;
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
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.ListenStatus;
import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.tools.JWPRequestUrlTool;
import weixinkeji.vip.jweb.power.vo.JWPUserConfigVO;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;
import weixinkeji.vip.jweb.tools.JWPControllePrint;

public class JWPFilter implements Filter {
	
	
	private String jwp_static_resources_prefix;
	private boolean jwp_print_console;
	private boolean jwp_controller_dynamics_url;
	private boolean jwp_controller_free_url;
	
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
		JWPControllePrint pr=new JWPControllePrint(true);
		//加载用户配置
		JWPUserConfigVO config=this._1_loadConfig(fConfig,pr);
		if(null==config) {
			return;
		}
		pr.setPrintInController(jwp_print_console);
		
		//开始执行初始化操作
		this._2_toStartInit(config,pr);
		
		pr.printMessage();
		pr.clearMessage();
		pr=null;
	}
	
	//加载配置文件
	private JWPUserConfigVO _1_loadConfig(FilterConfig fConfig,JWPControllePrint pr) {
		pr.addMessage("[目录]加载框架配置文件");
		pr.printMessage();
		pr.clearMessage();
		
		_0_LoadJWPConfig config=new _0_LoadJWPConfig("JWP.properties",pr);
		JWPUserConfigVO configVo=config.getJWPUserConfigVO(fConfig.getServletContext().getContextPath());
		
		this.jwp_controller_dynamics_url =configVo.jwp_controller_dynamics_url;
		this.jwp_print_console =configVo.jwp_print_console;
		this.jwp_static_resources_prefix=configVo.jwp_static_resources_prefix;
		this.jwp_controller_free_url=configVo.jwp_controller_free_url;
		
		if (null == configVo.jwp_scan_package || configVo.jwp_scan_package.length==0) {
			pr.addErrorMessage(
					"JWebPower启动失败，没有配置扫描路径。请在类路径下，创建属性文件【JWP.properties】，并设置一个键值对【scan_package=扫描的路径】", 1);
			return null;
		}
		// 扫描路径
		pr.addMessage("[目录]扫描类路径并加载类 完毕");
		
		pr.printMessage();
		pr.clearMessage();
		
		return configVo;
	}
	
	//开始初始化
	private void _2_toStartInit(JWPUserConfigVO configVo,JWPControllePrint pr) {
		pr.addMessage("[目录]环境初始完毕，开始业务处理");
		pr.addMessage("");
		pr.printMessage();
		pr.clearMessage();
		
		//存在初始化结果
		ReturnResultObject rs=new ReturnResultObject();
		//开始初始化
		new _1_IniMain(configVo,rs,pr);
		
		this.jwebPowerControllerModel=rs.getModel_controller();
		this.staticResourcesModel=rs.getModel_static();
		
		this.jwpGlobalEvent=rs.getEvent_global();// 全局事件
		this.controllerUrlPowerEvent=rs.getEvent_controller();// 事件
		
		this.userPower=rs.getUserPower();// 用户权限 -接口
		JWPFilterCommonInterface.initUserPower(userPower);
		
		this.requestUrlTool=rs.getRequestUrlTool();
		pr.printMessage();
		pr.clearMessage();
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
		if (requestURL.startsWith(this.jwp_static_resources_prefix)) {
			this.doStaticRequestURLLiseten(httpRequest, httpResponse, chain, requestURL, powerCode);
		} else {
			if (jwp_print_console) {
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
		if (staticResourcesModel.doListen(chain, request, response, requestURL, null, powerCode,ListenStatus.onlyListen)) {
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
		//对请求路径进行 标准化（去掉多余的后缀、前缀）
		String requestURL = url.equals("/") ? url : this.requestUrlTool.formatRequestURL(url);
		String durl;
		JWPControllerModel powerModel = jwebPowerControllerModel.get(requestURL);
		//如果请求路径不为null,并且用户开启了动态路径的支持（默认开启），并且请求路径取不到与它关联的权限模型
		//那么，有可能是动态路径。即，需要得出 去除动态参数后的请求路径。再来找一次关联的权限模型。
		if (null != requestURL && this.jwp_controller_dynamics_url && null == powerModel) {
			durl = DUrlTools.getUserURL(requestURL);
			if (null != durl) {
				requestURL = durl;
				powerModel = jwebPowerControllerModel.get(requestURL);
			}
		}
		//先执行一次全局事件。这个事件如果返回false，直接中止程序继续往下走
		if (!controllerUrlPowerEvent.jWebPower_start(chain, request, response, requestURL, powerModel, powerCode)) {
			return;
		}
		//如果没有与它相关联的权限模型，只有一种可能，它不在权限框架监控范围内。执行 其他事件（不在监控范围内事件）
		if (null == powerModel) { // 不受控制的路径
			if (jwp_controller_free_url&&controllerUrlPowerEvent.doOther_noController(request, response, requestURL, powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		}
		
		switch (powerModel.urlType) {
		case common:
			 // 触发监听，并且监听结果不为true时，中断请求。// 公共路径，得到用户的许可后，执行放行。
			if (powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.common_success)
					&&controllerUrlPowerEvent.doCommonPower_success(request, response, requestURL, powerModel, powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		case session:{
			//会话区。
			if(null==powerCode) {//取不到用户权限。执行【会话区】失败事件
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
			}else if (powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_success)
					&&controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL, powerModel, powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		}
		case grades:// 等级区
			if (null == powerCode || null == powerCode.grades) {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
				return;
			}
			//校验权限
			if (powerModel.isInGrades(powerCode.grades)) {
				//执行用户事件+监听
				if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.grades_success)
						&&controllerUrlPowerEvent.doGradesPower_success(request, response, requestURL, powerModel,powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.grades_fail);
				controllerUrlPowerEvent.doGradesPower_fail(request, response, requestURL, powerModel, powerCode);
			}
			return;
		case code:// 编号区
			if (null == powerCode || null == powerCode.code) {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
				return;
			}
			//是否有权限
			if (powerModel.isCode(powerCode.code)) {
				// 用户事件 +监听
					if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.code_success)
							&&controllerUrlPowerEvent.doCodePower_success(request, response, requestURL, powerModel,powerCode)) {
						chain.doFilter(request, response);
					}
			} else {//失败事件
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.code_fail);
				controllerUrlPowerEvent.doCodePower_fail(request, response, requestURL, powerModel, powerCode);
			}
			return;
		case gradesAndCode:// 等级+编号
			if (null == powerCode) {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
				return;
			}
			//是否有权限： 等级不为null,编号不为null, 等级检验成功，编号检验成功
			if (null != powerCode.grades && null!= powerCode.code&&powerModel.isInGrades(powerCode.grades) && powerModel.isCode(powerCode.code)) {
				// 用户事件 +监听
				if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.codeAndGrades_success)
						&&controllerUrlPowerEvent.doGradesAndCodePower_success(request, response, requestURL,powerModel, powerCode)
						) {
					chain.doFilter(request, response);
				}
			} else {//没有权限，执行失败事件。执行监听（无论返回什么，都不起作用）
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.codeAndGrades_fail);
				controllerUrlPowerEvent.doGradesAndCodePower_fail(request, response, requestURL, powerModel,powerCode);
			}
			return;
		case onlyListen:
			if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.onlyListen)) {
				chain.doFilter(request, response);
			}
			return;
		default:
			return;
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
		System.out.println();
		//对请求路径进行 标准化（去掉多余的后缀、前缀）
		String requestURL = url.equals("/") ? url : this.requestUrlTool.formatRequestURL(url);
		String durl;
		JWPControllerModel powerModel = jwebPowerControllerModel.get(requestURL);
		//如果请求路径不为null,并且用户开启了动态路径的支持（默认开启），并且请求路径取不到与它关联的权限模型
		//那么，有可能是动态路径。即，需要得出 去除动态参数后的请求路径。再来找一次关联的权限模型。
		if (null != requestURL && this.jwp_controller_dynamics_url && null == powerModel) {
			durl = DUrlTools.getUserURL(requestURL);
			if (null != durl) {
				requestURL = durl;
				powerModel = jwebPowerControllerModel.get(requestURL);
			}
		}
		this.println(requestURL, powerModel, powerCode);//打印
		//先执行一次全局事件。这个事件如果返回false，直接中止程序继续往下走
		if (!controllerUrlPowerEvent.jWebPower_start(chain, request, response, requestURL, powerModel, powerCode)) {
			System.out.println("【全局事件】未通过，路径="+requestURL);
			return;
		}
		
		//如果没有与它相关联的权限模型，只有一种可能，它不在权限框架监控范围内。执行 其他事件（不在监控范围内事件）
		if (null == powerModel) { // 不受控制的路径
			if (jwp_controller_free_url&&controllerUrlPowerEvent.doOther_noController(request, response, requestURL, powerCode)) {
				System.out.println("【异常】请求路径不在监控范围内，但用户强制放行！，路径="+requestURL);
				chain.doFilter(request, response);
			}else {
				System.out.println("【异常】请求路径不在监控范围内！，路径="+requestURL);
			}
			return;
		}
		
		switch (powerModel.urlType) {
		case common:
			// 触发监听，并且监听结果不为true时，中断请求。// 公共路径，得到用户的许可后，执行放行。
			if (powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.common_success)
					&&controllerUrlPowerEvent.doCommonPower_success(request, response, requestURL, powerModel, powerCode)) {
				chain.doFilter(request, response);
			}else {
				System.out.println("【放行区】未通过用户事件，路径="+requestURL);
			}
			return;
		case session:{
			//会话区。
			if(null==powerCode) {//取不到用户权限。执行【会话区】失败事件
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);				
				System.out.println("【会话事件】未通过，路径="+requestURL);
			}else if (powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_success)
					&&controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL, powerModel, powerCode)) {
				chain.doFilter(request, response);
			}
			return;
		}
		case grades:// 等级区
			if (null == powerCode || null == powerCode.grades) {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
				System.out.println("【会话事件】未通过，路径="+requestURL);
				return;
			}
			//校验权限
			if (powerModel.isInGrades(powerCode.grades)) {
				//执行用户事件+监听
				if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.grades_success)
						&&controllerUrlPowerEvent.doGradesPower_success(request, response, requestURL, powerModel,powerCode)) {
					chain.doFilter(request, response);
				}
			} else {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.grades_fail);
				controllerUrlPowerEvent.doGradesPower_fail(request, response, requestURL, powerModel, powerCode);
				System.out.println("【权限等级】检验或监听结果 未通过，路径="+requestURL);
			}
			return;
		case code:// 编号区
			if (null == powerCode || null == powerCode.code) {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
				System.out.println("【会话事件】未通过，路径="+requestURL);
				return;
			}
			//是否有权限
			if (powerModel.isCode(powerCode.code)) {
				//监听+用户事件 
				if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.code_success)
						&&controllerUrlPowerEvent.doCodePower_success(request, response, requestURL, powerModel,powerCode)) {
					chain.doFilter(request, response);
				}
			} else {//失败事件
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.code_fail);
				controllerUrlPowerEvent.doCodePower_fail(request, response, requestURL, powerModel, powerCode);
				System.out.println("【权限编号】检验或监听结果 未通过，路径="+requestURL);
			}
			return;
		case gradesAndCode:// 等级+编号
			if (null == powerCode) {
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.session_fail);
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel);
				System.out.println("【会话事件】未通过，路径="+requestURL);
				return;
			}
			//是否有权限： 等级不为null,编号不为null, 等级检验成功，编号检验成功
			if (null != powerCode.grades && null!= powerCode.code&&powerModel.isInGrades(powerCode.grades) && powerModel.isCode(powerCode.code)) {
				// 监听+用户事件
				if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.codeAndGrades_success)
						&&controllerUrlPowerEvent.doGradesAndCodePower_success(request, response, requestURL,powerModel, powerCode)
						) {
					chain.doFilter(request, response);
				}
			} else {//没有权限，执行失败事件。执行监听（无论返回什么，都不起作用）
				powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.codeAndGrades_fail);
				controllerUrlPowerEvent.doGradesAndCodePower_fail(request, response, requestURL, powerModel,powerCode);
				System.out.println("【权限等级+编号】检验或监听结果 未通过，路径="+requestURL);
			}
			return;
		case onlyListen:
			if(powerModel.doListen(chain, request, response, requestURL, powerModel, powerCode,ListenStatus.onlyListen)) {
				chain.doFilter(request, response);
			}else {
				System.out.println("【仅监听】未通过，路径="+requestURL);
			}
			return;
		default:
			return;
		}
	}
	
	private void println(String requestURL, JWPControllerModel powerModel, JWPUserPower userPower) {
		String urltype;
		if (null != powerModel) {
			switch (powerModel.urlType) {
			case common:
				urltype = "公共区";
				break;
			case session:
				urltype = "会话区";
				break;
			case grades:
				urltype = "等级区";
				break;
			case code:
				urltype = "编号区";
				break;
			case gradesAndCode:
				urltype = "等级+编号区";
				break;
			case onlyListen:
				urltype = "只监听";
				break;	
			case unknow:
				urltype = "未知区";
				break;
			default:
				urltype = "异常区";
				break;
			}
		}else {
			urltype = "监控外";
		}
		StringBuilder sb=new StringBuilder();
		int messageHead;
		sb.append("[权限模型-").append(urltype).append("]：");
		
		if(null!=powerModel) {
			messageHead=sb.length();
			if(powerModel.urlType==JWPType.grades||powerModel.urlType==JWPType.gradesAndCode) {
				sb.append(messageHead==sb.length()?"":"，").append("权限等级=").append(null != powerModel.grades ? Arrays.deepToString(powerModel.grades) : null);
			}
			if(powerModel.urlType==JWPType.code||powerModel.urlType==JWPType.gradesAndCode) {
				sb.append(messageHead==sb.length()?"":"，").append("权限编号=").append(null != powerModel.code ? Arrays.deepToString(powerModel.code) : null);
			}
			sb.append(messageHead==sb.length()?"":"，").append("路径=").append(requestURL);
			
			
			if (powerModel.listenCount>0) {
				String listenName="";
				for(JWPListenInterface list:powerModel.listens) {
					listenName=listenName+","+list.getClass().getName();
				}
				sb.append(messageHead==sb.length()?"":"，").append("监听类=").append(listenName.substring(1));
			}
		}else {
			sb.append("路径=").append(requestURL);
		}
		System.out.println(sb.toString());
		System.out.print("[系统对接-用户区]: 权限等级="+(null != userPower ? Arrays.deepToString(userPower.grades) : null));
		System.out.println("， 权限编号="+(null != userPower ? Arrays.deepToString(userPower.code) : null));
		
	}
}
