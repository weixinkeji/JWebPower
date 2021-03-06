package weixinkeji.vip.jweb.power.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.JWPUserPower;

public interface JWPUserInterface {

	/**
	 * 
	 * 获取【会员等级】、【权限编号】的用户接口
	 * 
	 * @param req	基于Servlet的HttpServletRequest
	 * @param resp	基于Servlet的HttpServletResponse
	 * @return JWPUserPower  存储临时 值。返回多值* 封装
	 * @throws IOException io流
	 * @throws ServletException Servlet
	 */
	JWPUserPower getUserPowerCode(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  ;
	
}
