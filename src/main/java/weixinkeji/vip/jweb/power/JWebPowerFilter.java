package weixinkeji.vip.jweb.power;

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

import weixinkeji.vip.jweb.power._init._ConfigFactory;
import weixinkeji.vip.jweb.power.config.IJWebPowerUserInterface;
import weixinkeji.vip.jweb.power.event.IControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.model.JWebPowerControllerModel;
import weixinkeji.vip.jweb.power.model.JWebPowerStaticResourcesValidateModel;
import weixinkeji.vip.jweb.power.vo.ExpressConfigVO;
import weixinkeji.vip.jweb.power.vo.RequestURLVO;
import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;
import weixinkeji.vip.jweb.scan.ScanClassFactory;

public class JWebPowerFilter implements Filter {

	/**
	 * 路径 - 与权限模型
	 */
	private Map<String, JWebPowerControllerModel> jwebPowerControllerModel = new HashMap<>();

//---------方法区---------
	// 事件
	private IControllerURLPowerEvent controllerUrlPowerEvent;
	// 用户权限 -接口
	private IJWebPowerUserInterface userPower;

	private JWebPowerStaticResourcesValidateModel staticResourcesValiDate;
	private RequestURLVO requestUrlTool;

	public void init(FilterConfig fConfig) throws ServletException {
		// 扫描路径
		List<Class<?>> classList = ScanClassFactory.getClassByFilePath("jwebpower", "test.servlet");
		// 启动配置工厂
		_ConfigFactory tempConfigFactory = new _ConfigFactory(classList);
		requestUrlTool = tempConfigFactory.getRequestURLVO(fConfig.getServletContext().getContextPath());

//从集合中，找到我们的配置实例
		// 事件触发-实现对象
		controllerUrlPowerEvent = tempConfigFactory.find_controllerURLPowerEvent();
		// 设置用户类与方法上注解的权限的模型
		tempConfigFactory.setControllerPowerModel(jwebPowerControllerModel);

		// 加载用户的静态资源权限配置
		ExpressConfigVO staticExpresstion = tempConfigFactory.find_staticResourcesURLExpresstion();
		// 设置用户的静态资源 检验模型
		staticResourcesValiDate = new JWebPowerStaticResourcesValidateModel(staticExpresstion);
		// 设置用户权限
		userPower = tempConfigFactory.find_IJWebPowerUserInterface();
	}

	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestURL = httpRequest.getRequestURI();
		// 静态资源处理
		if (requestURL.startsWith(request.getServletContext().getContextPath() + "/static/")) {
			System.out.println("静态资源处理！");
		} else {
			this.doServerRequestURLLiseten(httpRequest, httpResponse, chain, requestURL);
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
			final FilterChain chain, String url) throws IOException, ServletException {
		String requestURL = this.requestUrlTool.formatRequestURL(url);
		System.out.println(requestURL);
		JWebPowerControllerModel powerModel = jwebPowerControllerModel.get(requestURL);

		SessionCodeAndIdentifiterCodeVO powerCode = userPower.getUserPowerCode(request, response, requestURL);
		if (null == powerModel) {
			// System.out.println("不在监控内的权限 ！"+requestURL);
			return;
		}
		if (!controllerUrlPowerEvent.jWebPower_start(request, response, requestURL)) {
			return;
		}
		switch (powerModel.urlType) {
		case common:
			// 公共路径，得到用户的许可后，执行放行。
			if (controllerUrlPowerEvent.doPublicPower_success(request, response, requestURL)) {
				chain.doFilter(request, response);
			}
			return;
		case session:// 会话区
			// 假设你的会员等级是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.getGrades())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionPower_success(request, response, requestURL, powerModel.grades)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionPower_fail(request, response, requestURL, powerModel.grades);
			}
			return;
		case identifiter:// 编号区
			// 假设你的权限编号是1.判断 用户信息中，是否有相关的权限
			if (powerModel.isInIdentifier(powerCode.getIdentifiter())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doIdentifiterPower_success(request, response, requestURL,
						powerModel.identifier)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doIdentifiterPower_fail(request, response, requestURL, powerModel.identifier);
			}
			return;
		case sessionAndIdentifiter:// 会员+编号
			// 假设你的会员等级是1、权限编号是2。判断 用户信息中，是否有相关的权限
			if (powerModel.isInGrades(powerCode.getGrades()) && powerModel.isInIdentifier(powerCode.getIdentifiter())) {
				// 有权限。调用定义的方法。
				if (controllerUrlPowerEvent.doSessionAndIdentifierPower_success(request, response, requestURL,
						powerModel.grades, powerModel.identifier)) {
					chain.doFilter(request, response);
				}
			} else {
				controllerUrlPowerEvent.doSessionAndIdentifierPower_fail(request, response, requestURL,
						powerModel.grades, powerModel.identifier);
			}
			return;
		default: // 不受控制的路径
			if (controllerUrlPowerEvent.doOtherURL(request, response, requestURL)) {
				chain.doFilter(request, response);
			}
			return;
		}
	}
}
