package weixinkeji.vip.jweb.power.event;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

public interface JWPGlobalEvent {

	/**
	 * 框架第一次接到数据时，全局事件。必定执行。
	 * 
	 * @param chain      FilterChain
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param userPower  用户的权限
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException      IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	default boolean jwpGlobal(final FilterChain chain, final HttpServletRequest req, final HttpServletResponse resp,
			final JWPCodeVO userPower) throws IOException, ServletException {
		return true;
	}
}
