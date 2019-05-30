package weixinkeji.vip.jweb.power.vo;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

public class JWPStaticUrlAndListenVO {
	public final String url;
	public final JWPListenInterface obj;

	public JWPStaticUrlAndListenVO(String url, JWPListenInterface obj) {
		this.url = url;
		this.obj = obj;
	}
}
