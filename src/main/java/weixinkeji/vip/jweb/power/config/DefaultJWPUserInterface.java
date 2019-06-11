package weixinkeji.vip.jweb.power.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.JWPUserPower;

/**
 * 客户端请求的url 触发权限检验时，权限框架会调用此接口的实例对象的方法（主要是为了拿到用户的权限
 * SessionCodeAndIdentifiterCode）
 * 
 * @author wangchunzi
 *
 */
public class DefaultJWPUserInterface implements JWPUserInterface {

	@Override
	public JWPUserPower getUserPowerCode(HttpServletRequest req, HttpServletResponse resp)  throws IOException, ServletException{
		return null;
	}
}
