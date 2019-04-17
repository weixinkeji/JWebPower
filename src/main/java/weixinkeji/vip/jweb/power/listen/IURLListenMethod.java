package weixinkeji.vip.jweb.power.listen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url监听 触发方法
 * 
 * @author wangchunzi
 *
 */
public interface IURLListenMethod {
	
	public boolean doMethod(HttpServletRequest req, HttpServletResponse resp, final String requestURL);
}
