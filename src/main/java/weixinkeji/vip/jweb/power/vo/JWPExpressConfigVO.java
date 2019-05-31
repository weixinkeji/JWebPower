package weixinkeji.vip.jweb.power.vo;

import java.util.HashSet;
import java.util.Set;

final public class JWPExpressConfigVO {
	// 公共权限的表达式
	private Set<String> publicPowerExpresstion = new HashSet<String>();
	// 权限等级的表达式
	private Set<String> gradesExcpresstion = new HashSet<String>();;
	// 权限编号的表达式
	private Set<String> identifiterPowerExpression = new HashSet<String>();;
//	//map 路径(key)-监听类（value)
//	private Map<String, Class<?extends JWPListenInterface>> urlAndListen=new HashMap<>();
	
	/**
	 * 取得 公共权限的表达式
	 * 
	 * @return Set
	 */
	public Set<String> getPublicPowerExpresstion() {
		return publicPowerExpresstion;
	}

	/**
	 * 取得 权限等级的表达式
	 * 
	 * @return Set
	 */
	public Set<String> getSessionPowerExpresstion() {
		return gradesExcpresstion;
	}

	/**
	 * 取得 权限编号的表达式
	 * 
	 * @return Set
	 */
	public Set<String> getIdentifiterPowerExpression() {
		return identifiterPowerExpression;
	}

//	/**
//	 * 注册监听 路径(key)-监听类（value)
//	 * @return Map
//	 */
//	public Map<String, Class<? extends JWPListenInterface>> getUrlAndListen() {
//		return urlAndListen;
//	}
//
//	/**
//	 * 注册监听的类
//	 * @param urlAndListen 路径(key)-监听类（value)
//	 */
//	public void setUrlAndListen(Map<String, Class<? extends JWPListenInterface>> urlAndListen) {
//		this.urlAndListen = urlAndListen;
//	}

	
}
