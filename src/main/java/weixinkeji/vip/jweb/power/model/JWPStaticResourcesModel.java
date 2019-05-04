package weixinkeji.vip.jweb.power.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.JWPListenPool;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

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
	private final StaticListenModel[] listenMode;

	public JWPStaticResourcesModel(Map<String, Class<? extends JWPListenInterface>> map) {
		String temkey;
		JWPListenInterface obj;
		Set<StaticListenModel> set = new HashSet<>();
		for (Map.Entry<String, Class<? extends JWPListenInterface>> kv : map.entrySet()) {
			temkey = kv.getKey();
			obj = JWPListenPool.getIURLListenMethod(kv.getValue());
			if (null != temkey && obj != null) {
				set.add(new StaticListenModel(temkey, obj));
			}
		}
		listenMode = new StaticListenModel[set.size()];
		set.toArray(listenMode);
		listenCount = listenMode.length;
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
			final String requestURL, JWPControllerModel powerModel, JWPCodeVO powerCode)
			throws IOException, ServletException {
		switch (this.listenCount) {
		case 0:
			return true;
		case 1:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;

		case 2:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		case 3:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[2].url)) {
				return this.listenMode[2].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		case 4:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[2].url)) {
				return this.listenMode[2].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[3].url)) {
				return this.listenMode[3].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		case 5:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[2].url)) {
				return this.listenMode[2].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[3].url)) {
				return this.listenMode[3].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[4].url)) {
				return this.listenMode[4].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		case 6:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[2].url)) {
				return this.listenMode[2].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[3].url)) {
				return this.listenMode[3].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[4].url)) {
				return this.listenMode[4].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[5].url)) {
				return this.listenMode[5].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		case 7:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[2].url)) {
				return this.listenMode[2].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[3].url)) {
				return this.listenMode[3].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[4].url)) {
				return this.listenMode[4].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[5].url)) {
				return this.listenMode[5].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[6].url)) {
				return this.listenMode[6].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		case 8:
			if (requestURL.startsWith(this.listenMode[0].url)) {
				return this.listenMode[0].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[1].url)) {
				return this.listenMode[1].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[2].url)) {
				return this.listenMode[2].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[3].url)) {
				return this.listenMode[3].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[4].url)) {
				return this.listenMode[4].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[5].url)) {
				return this.listenMode[5].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[6].url)) {
				return this.listenMode[6].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			if (requestURL.startsWith(this.listenMode[7].url)) {
				return this.listenMode[7].obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			}
			return true;
		default: {
			for (StaticListenModel mobj : this.listenMode) {
				if (requestURL.startsWith(mobj.url)) {
					return mobj.obj.doMethod(chain, req, resp, requestURL, powerModel, powerCode);
				}
			}
			return true;
		}
		}
	}

}

class StaticListenModel {
	public final String url;
	public final JWPListenInterface obj;

	public StaticListenModel(String url, JWPListenInterface obj) {
		this.url = url;
		this.obj = obj;
	}
}