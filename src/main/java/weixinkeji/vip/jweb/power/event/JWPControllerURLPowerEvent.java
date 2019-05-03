package weixinkeji.vip.jweb.power.event;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

/**
 * 
 * 非静态资源url触发权限检验时，成功或失败，会调用相关的方法执行。
 * 
 * @author wangchunzi
 *
 */
public interface JWPControllerURLPowerEvent {

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
	default boolean jWebPower_start(final FilterChain chain, HttpServletRequest req, HttpServletResponse resp,
			String requestURL, JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default boolean doPublicPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default boolean doSessionPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default void doSessionPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default boolean doIdentifiterPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default void doIdentifiterPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
	}

	/**
	 * 通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpHttpServletRequest
	 * @param resp       HttpServletRespons
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	default boolean doSessionAndIdentifierPower_success(HttpServletRequest req, HttpServletResponse resp,
			String requestURL, JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default void doSessionAndIdentifierPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPControllerModel powerModel, JWPCodeVO userPower)
			throws IOException, ServletException {
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
	default boolean doOtherURL(HttpServletRequest req, HttpServletResponse resp, String requestURL,
			JWPCodeVO userPower) throws IOException, ServletException {
		return false;
	}

}
