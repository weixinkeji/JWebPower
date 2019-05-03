package weixinkeji.vip.jweb.power.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.tools.PowerExpressionTool;
import weixinkeji.vip.jweb.power.vo.ExpressConfigVO;
import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

/**
 * 权限模型
 * <p>
 * 通过配置，最后产生这个对象集合。
 * <p>
 * 它描述了 [访问路径]与 [什么类型 的权限关联]
 * 
 * @author wangchunzi
 *
 */
public class JWebPowerStaticResourcesValidateModel {
	/**
	 * 放行区表达式
	 */
	private final Set<String> publicPowerExpresstion;
	/**
	 * 等级区表达式
	 */
	private final Set<String> sessionPowerExpresstion;
	/**
	 * 编号区表达式
	 */
	private final Set<String> identifiterPowerExpression;
	/**
	 * 缓存检验结果
	 */
	private static Map<String, JWebPowerStaticResourcesModel> cacheVlidateResult = new HashMap<>();

	/**
	 * 表达式校验工具
	 */
	private final PowerExpressionTool tool = new PowerExpressionTool();

	public JWebPowerStaticResourcesValidateModel(ExpressConfigVO vo) {
		this.publicPowerExpresstion = vo.getPublicPowerExpresstion();
		this.sessionPowerExpresstion = vo.getSessionPowerExpresstion();
		this.identifiterPowerExpression = vo.getIdentifiterPowerExpression();
	}

	public JWebPowerStaticResourcesModel getCode(String url) {
		JWebPowerStaticResourcesModel sr = cacheVlidateResult.get(url);
		if (null == sr) {

		}
		return sr;
	}

	// 公共路径
	private boolean isPublicURL(final String url) {
		for (String expresstion : this.publicPowerExpresstion) {
			if (tool.isPublicPower(expresstion, url)) {
				return true;
			}
		}
		return false;
	}

	// 需要会员访问的路径
	private String[] isSessionURL(final String url) {
		String[] grades = null;
		for (String expresstion : this.sessionPowerExpresstion) {
			if (null != (grades = tool.isSessionPower(expresstion, url))) {
				return grades;
			}
		}
		return null;
	}

	// 需要指定编号权限的路径
	private String[] isIdentifiterURL(final String url) {
		String[] identifiter = null;
		for (String expresstion : this.identifiterPowerExpression) {
			if (null != (identifiter = tool.isIdentifiterPower(expresstion, url))) {
				return identifiter;
			}
		}
		return null;
	}

	// 需要会员，同时指定编号权限的路径
	private SessionCodeAndIdentifiterCodeVO isSessionAndIdentifiterURL(final String url) {
		SessionCodeAndIdentifiterCodeVO si = new SessionCodeAndIdentifiterCodeVO();
		String values[];
		for (String expresstion : this.identifiterPowerExpression) {
			if (null != (values = tool.isIdentifiterPower(expresstion, url))) {
				si.setIdentifiter(values);// 设置权限编号
				break;
			}
		}
		// 如果没有 【权限编号】，直接返回null, 表示 此路径不是 会员与编号 同时绑定的路径
		if (null == si.getIdentifiter()) {
			return null;
		}
		for (String expresstion : this.sessionPowerExpresstion) {
			if (null != (values = tool.isSessionPower(expresstion, url))) {
				si.setGrades(values);// 设置会员等级
				return si;
			}
		}
		return null;
	}
}