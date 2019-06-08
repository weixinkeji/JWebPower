package weixinkeji.vip.jweb.power;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;

final class JWPFilterCommonInterface {
	// 用户权限 -接口
	private static JWPUserInterface userPower = null;
	private static boolean isInit = false;

	synchronized public static void initUserPower(JWPUserInterface mypower) {
		if (!isInit) {
			isInit = true;
			userPower = mypower;
		}
	}

	/**
	 * 用户权限
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	static JWPUserPower getPowergetUserPowerCode(HttpServletRequest req, HttpServletResponse resp) {
		return userPower.getUserPowerCode(req, resp);
	}
}
