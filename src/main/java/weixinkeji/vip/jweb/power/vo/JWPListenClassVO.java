package weixinkeji.vip.jweb.power.vo;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

public class JWPListenClassVO {
	public final String url;
	public final Class<?extends JWPListenInterface> jwpListenClass;

	public JWPListenClassVO(String url, Class<?extends JWPListenInterface> jwpListenClass) {
		this.url = url;
		this.jwpListenClass = jwpListenClass;
	}
}
