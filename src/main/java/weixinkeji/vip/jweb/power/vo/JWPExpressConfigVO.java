package weixinkeji.vip.jweb.power.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;

final public class JWPExpressConfigVO {
	// 公共权限的表达式
	private Set<String> publicPowerExpresstion = new HashSet<String>();
	// 会员权限的表达式
	private Set<String> sessionPowerExpresstion = new HashSet<String>();;
	// 权限编号的表达式
	private Set<String> identifiterPowerExpression = new HashSet<String>();;
	//map 路径(key)-监听类（value)
	private Map<String, Class<?extends JWPListenInterface>> urlAndListen=new HashMap<>();
	
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

	/**
	 * 注册监听 路径(key)-监听类（value)
	 * @return Map
	 */
	public Map<String, Class<? extends JWPListenInterface>> getUrlAndListen() {
		return urlAndListen;
	}

	/**
	 * 注册监听的类
	 * @param urlAndListen 路径(key)-监听类（value)
	 */
	public void setUrlAndListen(Map<String, Class<? extends JWPListenInterface>> urlAndListen) {
		this.urlAndListen = urlAndListen;
	}

	
}
