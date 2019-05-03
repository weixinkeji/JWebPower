package weixinkeji.vip.jweb.power.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

public interface JWPUserInterface {

	/**
	 * 获取【会员等级】、【权限编号】的用户接口
	 * 
	 * @param req	基于Servlet的HttpServletRequest
	 * @param resp	基于Servlet的HttpServletResponse
	 * @param requestURL 用户请求路径
	 * @return SessionCodeAndIdentifiterCodeVO  存储临时 值。返回多值* 封装
	 */
	JWPCodeVO getUserPowerCode(HttpServletRequest req, HttpServletResponse resp,
			final String requestURL);
	
}
