package weixinkeji.vip.jweb.power._init;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weixinkeji.vip.jweb.power.expresstion.DefaultJWPControllerURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.DefaultJWPStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.JWPControllerURLExpresstion;
import weixinkeji.vip.jweb.power.expresstion.JWPStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;
import weixinkeji.vip.jweb.power.vo.JWPExpressConfigVO;

/**
 * 初始化 权限配置--表达式
 * 
 * @author wangchunzi
 *
 */
public class _3_IniJWPExpress extends __InitTool{
	JWPExpressionTool expressTool=new JWPExpressionTool();
	private JWPExpressConfigVO controllerExpress;
	
	/**
	 * @param list 扫描到的类
	 */
	_3_IniJWPExpress(List<Class<?>> list){
		super(list);
		initJWPControllerURLExpresstion();
	}
	/**
	 * Controller权限 表达式
	 * 
	 * @return ExpressConfigVO
	 */
	private void initJWPControllerURLExpresstion() {
		// 找到用户配置的表达式，没有用户配置，则加载默认的配置
		JWPControllerURLExpresstion sue = super.findObject(JWPControllerURLExpresstion.class, new DefaultJWPControllerURLExpresstion());
		
		// 公共权限的表达式
		Set<String> commonPowerExpresstion = new HashSet<String>();
		// 会话权限的表达式
		Set<String> sessionPowerExpresstion = new HashSet<String>();
		// 权限等级的表达式
		Set<String> gradesExcpresstion = new HashSet<String>();;
		// 权限编号的表达式
		Set<String> codePowerExpression = new HashSet<String>();
		
		sue.setRequestURL_common(commonPowerExpresstion);
		sue.setRequestURL_session(sessionPowerExpresstion);
		sue.setRequestURL_grades(gradesExcpresstion);
		sue.setRequestURL_code(codePowerExpression);
		
		
		this.controllerExpress= new JWPExpressConfigVO(commonPowerExpresstion,sessionPowerExpresstion ,gradesExcpresstion,codePowerExpression );
	}
	
	/**
	 * 取得静态资源的 表达式
	 * @return
	 */
	JWPStaticResourcesURLExpresstion getJWPStaticResourcesURLExpresstion() {
		return super.findObject(JWPStaticResourcesURLExpresstion.class,new DefaultJWPStaticResourcesURLExpresstion());
	}
	
	/**
	 * Controller权限 表达式
	 * 
	 * @return ExpressConfigVO
	 */
	JWPExpressConfigVO getJWPControllerURLExpresstion() {
		return this.controllerExpress;
	}
	/**
	 * 表达式处理工具
	 * 
	 * @return ExpressConfigVO
	 */
	JWPExpressionTool getJWPExpressionTool() {
		return expressTool;
	}
	
}
