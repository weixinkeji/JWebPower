package weixinkeji.vip.jweb.power.event;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;

public class DefaultJWPControllerURLPowerEvent implements JWPControllerURLPowerEvent {
	private void println(String requestURL, JWPControllerModel powerModel,
			JWPUserPower userPower) {
		String urltype = "无";
		if (null != powerModel) {
			switch (powerModel.urlType) {
			case common:
				urltype = "放行区";
				break;
			case grades:
				urltype = "会员等级区";
				break;
			case identifiter:
				urltype = "编号区";
				break;
			case gradesAndIdentifiter:
				urltype = "会员等级区+编号区";
				break;
			default:
				break;
			}
		}
		System.out.println("用户请求地址：" + requestURL + "  ,路径归属：[" + urltype + "]" + "  ,路径绑定的权限等级："
				+ (null != powerModel ? Arrays.deepToString(powerModel.grades) : null) + "  ,路径绑定的权限编号："
				+ (null != powerModel ? Arrays.deepToString(powerModel.identifier) : null) + "\n   用户权限等级："
				+ (null != userPower ? Arrays.deepToString(userPower.grades) : null) + "\n   用户权限编号："
				+ (null != userPower ? Arrays.deepToString(userPower.identifiter) : null));
	}

	/**
	 * 进入控制区时，自动调用执行的方法。必定执行。
	 * 
	 * @param chain      FilterChain
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public boolean jWebPower_start(FilterChain chain, HttpServletRequest req, HttpServletResponse resp,
			String requestURL, JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		// this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 服务请求，通过【放行区】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public boolean doPublicPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 通过【会员等级】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       ServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public boolean doSessionPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 未通【会员等级】过验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       ServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public void doSessionPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		this.println(requestURL, powerModel, userPower);
	}

	/**
	 * 通过【权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public boolean doIdentifiterPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {

		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 未通过【权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public void doIdentifiterPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		this.println(requestURL, powerModel, userPower);
	}

	/**
	 * 通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpHttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public boolean doSessionAndIdentifierPower_success(HttpServletRequest req, HttpServletResponse resp,
			String requestURL, JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 未通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpHttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public void doSessionAndIdentifierPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPUserPower userPower)
			throws IOException, ServletException {
		this.println(requestURL, powerModel, userPower);
	}

	/**
	 * 
	 * 不在监控内 的请求地址。执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认false,不放行！
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	@Override
	public boolean doOtherURL(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPUserPower userPower) throws IOException, ServletException {
		this.println(requestURL, null, userPower);
		return false;
	}

}
