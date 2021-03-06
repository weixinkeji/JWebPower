package weixinkeji.vip.jweb.power.listen;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;

/**
 * url监听 触发方法
 * 
 * @author wangchunzi
 *
 */
public interface JWPListenInterface {
	/**
	 * 执行监听的方法 
	 * 
	 * @param chain      FilterChain
	 * @param req  HttpServletRequest
	 * @param resp	HttpServletResponse
	 * @param requestURL 请求路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param powerCode 用户的权限
	 * @param status    触发状态
	 * @return boolean 返回true表示放行，返回 false表示 拦截下来。
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	public boolean doMethod(FilterChain chain, HttpServletRequest req, HttpServletResponse resp, final String requestURL
			,JWPControllerModel powerModel
			,JWPUserPower powerCode,ListenStatus status)throws IOException, ServletException ;
}
