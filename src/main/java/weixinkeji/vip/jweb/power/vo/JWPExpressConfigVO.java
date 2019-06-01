package weixinkeji.vip.jweb.power.vo;

import java.util.HashSet;
import java.util.Set;

final public class JWPExpressConfigVO {
	// 公共权限的表达式
	private Set<String> publicPowerExpresstion = new HashSet<String>();
	// 会话权限的表达式
	private Set<String> sessionPowerExpresstion = new HashSet<String>();

	// 权限等级的表达式
	private Set<String> gradesExcpresstion = new HashSet<String>();;
	// 权限编号的表达式
	private Set<String> identifiterPowerExpression = new HashSet<String>();
	
	public Set<String> getPublicPowerExpresstion() {
		return publicPowerExpresstion;
	}
	public Set<String> getSessionPowerExpresstion() {
		return sessionPowerExpresstion;
	}
	public Set<String> getGradesExcpresstion() {
		return gradesExcpresstion;
	}
	public Set<String> getIdentifiterPowerExpression() {
		return identifiterPowerExpression;
	}
	public void setPublicPowerExpresstion(Set<String> publicPowerExpresstion) {
		this.publicPowerExpresstion = publicPowerExpresstion;
	}
	public void setSessionPowerExpresstion(Set<String> sessionPowerExpresstion) {
		this.sessionPowerExpresstion = sessionPowerExpresstion;
	}
	public void setGradesExcpresstion(Set<String> gradesExcpresstion) {
		this.gradesExcpresstion = gradesExcpresstion;
	}
	public void setIdentifiterPowerExpression(Set<String> identifiterPowerExpression) {
		this.identifiterPowerExpression = identifiterPowerExpression;
	}

//	//map 路径(key)-监听类（value)
//	private Map<String, Class<?extends JWPListenInterface>> urlAndListen=new HashMap<>();

	

}
