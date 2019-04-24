package weixinkeji.vip.jweb.power.listen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

/**
 * url监听 触发方法
 * 
 * @author wangchunzi
 *
 */
public interface IURLListenMethod {
	
	/**
	 * 执行监听的方法 
	 * 
	 * @param req  HttpServletRequest
	 * @param resp	HttpServletResponse
	 * @param requestURL 请求路径
	 * @param powerCode 用户的权限
	 * 
	 * @return boolean 返回true表示放行，返回 false表示 拦截下来。
	 */
	public boolean doMethod(HttpServletRequest req, HttpServletResponse resp, final String requestURL,SessionCodeAndIdentifiterCodeVO powerCode);
}
