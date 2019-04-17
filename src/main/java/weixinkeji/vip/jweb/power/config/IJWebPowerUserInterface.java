package weixinkeji.vip.jweb.power.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

public interface IJWebPowerUserInterface {

	/**
	 * 获取【会员等级】、【权限编号】的用户接口
	 * 
	 * @param req
	 * @param resp
	 * @param requestURL
	 * @return
	 */
	SessionCodeAndIdentifiterCodeVO getUserPowerCode(HttpServletRequest req, HttpServletResponse resp,
			final String requestURL);
	
}
