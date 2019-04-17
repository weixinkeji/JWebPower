package weixinkeji.vip.jweb.power.vo;

import java.util.HashSet;
import java.util.Set;

final public class ExpressConfigVO {

	private Set<String> publicPowerExpresstion = new HashSet<String>();
	private Set<String> sessionPowerExpresstion = new HashSet<String>();;
	private Set<String> identifiterPowerExpression = new HashSet<String>();;

	public Set<String> getPublicPowerExpresstion() {
		return publicPowerExpresstion;
	}

	public Set<String> getSessionPowerExpresstion() {
		return sessionPowerExpresstion;
	}

	public Set<String> getIdentifiterPowerExpression() {
		return identifiterPowerExpression;
	}

}
