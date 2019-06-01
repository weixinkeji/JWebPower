package weixinkeji.vip.jweb.power._init;

import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;

/**
 * 权限业务处理
 * 
 * @author wangchunzi
 *
 */
public class _5_iniJWPModel_Static extends __InitTool {

	private _3_IniJWPListen listen;

	/**
	 * 静态模型处理
	 * 
	 * @param listen 监听器
	 */
	_5_iniJWPModel_Static(_3_IniJWPListen listen) {
		this.listen = listen;
	}

	/**
	 * 取得 静态资源 处理模型
	 * 
	 * @return JWPStaticResourcesModel
	 */
	public JWPStaticResourcesModel getJWPStaticResourcesModel() {
		return new JWPStaticResourcesModel(listen.getJWPListenInterface_static());
	}

}
