package weixinkeji.vip.jweb.power.vo;

import java.util.HashSet;
import java.util.Set;

final public class ExpressConfigVO {
	// 公共权限的表达式
	private Set<String> publicPowerExpresstion = new HashSet<String>();
	// 会员权限的表达式
	private Set<String> sessionPowerExpresstion = new HashSet<String>();;
	// 权限编号的表达式
	private Set<String> identifiterPowerExpression = new HashSet<String>();;

	/**
	 * 取得 公共权限的表达式
	 * 
	 * @return Set
	 */
	public Set<String> getPublicPowerExpresstion() {
		return publicPowerExpresstion;
	}

	/**
	 * 取得 会员权限的表达式
	 * 
	 * @return Set
	 */
	public Set<String> getSessionPowerExpresstion() {
		return sessionPowerExpresstion;
	}

	/**
	 * 取得 权限编号的表达式
	 * 
	 * @return Set
	 */
	public Set<String> getIdentifiterPowerExpression() {
		return identifiterPowerExpression;
	}

}
