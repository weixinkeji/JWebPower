package weixinkeji.vip.jweb.power.vo;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

public class JWPStaticUrlAndListenVO {
	public final String url;
	public final JWPListenInterface jwpListenObject;

	public JWPStaticUrlAndListenVO(String url, JWPListenInterface obj) {
		this.url = url;
		this.jwpListenObject = obj;
	}
}
