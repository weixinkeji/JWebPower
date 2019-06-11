package weixinkeji.vip.jweb.power.model;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.vo.JWPStaticUrlAndListenVO;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;

/**
 * 权限模型
 * <p>
 * 通过配置，最后产生这个对象集合。
 * <p>
 * 它描述了 [访问路径]与 [什么类型 的权限关联]
 * 
 * @author wangchunzi
 *
 */
public class JWPStaticResourcesModel {

	private final int listenCount;
	private final JWPStaticUrlAndListenVO[] listenMode;

	public JWPStaticResourcesModel(JWPStaticUrlAndListenVO[] listenMode) {
		this.listenMode=listenMode;
		this.listenCount=this.listenMode.length;
		
	}
	
	/**
	 * 执行监听的方法
	 * 
	 * @param chain      FilterChain
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL 请求路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param powerCode  用户的权限
	 * 
	 * @return boolean 返回true表示放行，返回 false表示 拦截下来。
	 * 
	 * @throws IOException      IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	public boolean doListen(FilterChain chain, HttpServletRequest req, HttpServletResponse resp,
			final String requestURL, JWPControllerModel powerModel, JWPUserPower powerCode)
			throws IOException, ServletException {
		switch (this.listenCount) {
		case 0:
			return true;
		case 1:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;

		case 2:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		case 3:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[2].url)
					&&!this.listenMode[2].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		case 4:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[2].url)
					&&!this.listenMode[2].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[3].url)
					&&!this.listenMode[3].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		case 5:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[2].url)
					&&!this.listenMode[2].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[3].url)
					&&!this.listenMode[3].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[4].url)
					&&!this.listenMode[4].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		case 6:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[2].url)
					&&!this.listenMode[2].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[3].url)
					&&!this.listenMode[3].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[4].url)
					&&!this.listenMode[4].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[5].url)
					&&!this.listenMode[5].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		case 7:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[2].url)
					&&!this.listenMode[2].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[3].url)
					&&!this.listenMode[3].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[4].url)
					&&!this.listenMode[4].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[5].url)
					&&!this.listenMode[5].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[6].url)
					&&!this.listenMode[6].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		case 8:
			if (requestURL.startsWith(this.listenMode[0].url)
					&&!this.listenMode[0].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[1].url)
					&&!this.listenMode[1].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[2].url)
					&&!this.listenMode[2].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[3].url)
					&&!this.listenMode[3].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[4].url)
					&&!this.listenMode[4].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[5].url)
					&&!this.listenMode[5].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[6].url)
					&&!this.listenMode[6].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			if (requestURL.startsWith(this.listenMode[7].url)
					&&!this.listenMode[7].jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
				return false;
			}
			return true;
		default: {
			for (JWPStaticUrlAndListenVO mobj : this.listenMode) {
				if (requestURL.startsWith(mobj.url)
						&&!mobj.jwpListenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
					return false;
				}
			}
			return true;
		}
		}
	}
}
