package weixinkeji.vip.jweb.power.event;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.event.IControllerURLPowerEvent;

public class DefaultControllerURLPowerEvent implements IControllerURLPowerEvent {

	/**
	 * 进入控制区时，自动调用执行的方法。必定执行。
	 */
	@Override
	public boolean doJWebPower_start(HttpServletRequest req, HttpServletResponse resp, String requestURL) {
		System.out.println(" 进入controller控制区，自动执行此方法。" + requestURL);
		return true;
	}

	/**
	 * 通过【会员等级】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的资源路径
	 * @param grades     String[] 权限模型中的会员等级
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doSessionPower_success(HttpServletRequest req, HttpServletResponse resp, final String requestURL,
			String[] grades) {
		System.out.println(" 通过【会员等级】验证" + requestURL);
		return true;
	}

	/**
	 * 未通【会员等级】过验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的资源路径
	 * @param grades     String[] 权限模型中的会员等级
	 */
	@Override
	public void doSessionPower_fail(HttpServletRequest req, HttpServletResponse resp, final String requestURL,
			String[] grades) {
		System.out.println(" 未通过【会员等级】验证" + requestURL);
	}

	/**
	 * 通过【权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的资源路径
	 * @param identifier String[] 权限模型中的权限编号
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doIdentifiterPower_success(HttpServletRequest req, HttpServletResponse resp, final String requestURL,
			String[] identifier) {
		System.out.println(" 通过【权限编号】验证" + requestURL);
		return true;
	}

	/**
	 * 未通过【权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的资源路径
	 * @param identifier String[] 权限模型中的权限编号
	 */
	@Override
	public void doIdentifiterPower_fail(HttpServletRequest req, HttpServletResponse resp, final String requestURL,
			String[] identifier) {
		System.out.println(" 未通过【权限编号】验证" + requestURL);
	}

	/**
	 * 通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的资源路径
	 * @param grades     String[] 权限模型中的会员等级
	 * @param identifier String[] 权限模型中的权限编号
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doSessionAndIdentifierPower_success(HttpServletRequest req, HttpServletResponse resp,
			final String requestURL, String[] grades, String[] identifier) {
		System.out.println(" 通过【会话、权限编号】验证" + requestURL);
		return true;
	}

	/**
	 * 未通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的资源路径
	 * @param grades     String[] 权限模型中的会员等级
	 * @param identifier String[] 权限模型中的权限编号
	 */

	public void doSessionAndIdentifierPower_fail(HttpServletRequest req, HttpServletResponse resp,
			final String requestURL, String[] grades, String[] identifier) {
		System.out.println(" 未通过【会话、权限编号】验证" + requestURL + "\t" + Arrays.deepToString(grades) + "//"
				+ Arrays.deepToString(identifier));
	}

	/**
	 * 
	 * 不在监控内 的请求地址。执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @return boolean 默认false,不放行！
	 */
	public boolean doOtherURL(HttpServletRequest req, HttpServletResponse resp, final String requestURL) {
		System.out.println(" 其他不在监控的请求地址：" + requestURL);
		return false;
	}

}
