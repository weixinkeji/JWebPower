package weixinkeji.vip.jweb.power.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.config.IJWebPowerUserInterface;
import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

/**
 * 客户端请求的url 触发权限检验时，权限框架会调用此接口的实例对象的方法（主要是为了拿到用户的权限
 * SessionCodeAndIdentifiterCode）
 * 
 * @author wangchunzi
 *
 */
public class DefaultJWebPowerUserInterface implements IJWebPowerUserInterface {

	@Override
	public SessionCodeAndIdentifiterCodeVO getUserPowerCode(HttpServletRequest req, HttpServletResponse resp, String requestURL) {
		SessionCodeAndIdentifiterCodeVO sai = new SessionCodeAndIdentifiterCodeVO();
		sai.setGrades(new String[] {});//"11","1"
		sai.setIdentifiter(new String[]{});//"11","2","1"
		return sai;
	}
}
