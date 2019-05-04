package weixinkeji.vip.jweb.power.event;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JWPGlobalEvent {

	/**
	 * 框架第一次接到数据时，全局事件。必定执行。
	 * 
	 * @param chain      FilterChain
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL 请求路径(注，此地址是未经JWebPower框架处理过的原始请求地址)
	 * @return boolean 默认true放行
	 * 
	 * @throws IOException      IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	default boolean jwpGlobal(final FilterChain chain, final HttpServletRequest req, final HttpServletResponse resp,
			final String requestURL) throws IOException, ServletException {
		return true;
	}
}
