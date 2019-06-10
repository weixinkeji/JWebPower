package weixinkeji.vip.jweb.power.vo;

import java.util.Set;

import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;

final public class JWPExpressConfigVO {
	// 公共权限的表达式
	private Set<String> commonPowerExpresstion;
	// 会话权限的表达式
	private Set<String> sessionPowerExpresstion;

	// 权限等级的表达式
	private Set<String> gradesExcpresstion;
	// 权限编号的表达式
	private Set<String> codePowerExpression;
	
	public JWPExpressConfigVO(
			Set<String> commonPowerExpresstion,Set<String> sessionPowerExpresstion,
			Set<String> gradesExcpresstion,Set<String> codePowerExpression) {
		this.commonPowerExpresstion = JWPExpressionTool.formatSimpleRegexExpression(commonPowerExpresstion);
		this.sessionPowerExpresstion =JWPExpressionTool.formatSimpleRegexExpression(sessionPowerExpresstion) ;
		this.gradesExcpresstion =JWPExpressionTool.formatSimpleRegexExpression(gradesExcpresstion)  ;
		this.codePowerExpression =JWPExpressionTool.formatSimpleRegexExpression(codePowerExpression)  ;
	}
	
	
	public Set<String> getCommonPowerExpresstion() {
		return commonPowerExpresstion;
	}
	public Set<String> getSessionPowerExpresstion() {
		return sessionPowerExpresstion;
	}
	public Set<String> getGradesExcpresstion() {
		return gradesExcpresstion;
	}
	public Set<String> getCodePowerExpression() {
		return codePowerExpression;
	}
}
