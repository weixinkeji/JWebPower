package weixinkeji.vip.jweb.power.vo;

import weixinkeji.vip.jweb.power.listen.IURLListenMethod;

final public class URLListenModelVO {
	/**
	 * 路径表达式
	 */
	public final String urlExpresstion;

	/**
	 * 绑定监听对象
	 */
	public final IURLListenMethod obj;

	public URLListenModelVO(String urlExpresstion, IURLListenMethod obj) {
		this.urlExpresstion = urlExpresstion;
		this.obj = obj;
	}
}
