package weixinkeji.vip.jweb.power._init;

import java.util.List;

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
		// 准备一个容器装数据
		JWPExpressConfigVO vo = new JWPExpressConfigVO();
		// 找到用户配置的表达式，没有用户配置，则加载默认的配置
		JWPControllerURLExpresstion sue = super.findObject(JWPControllerURLExpresstion.class, new DefaultJWPControllerURLExpresstion());
		sue.setRequestURL_Public(vo.getPublicPowerExpresstion());
		sue.setRequestURL_Session(vo.getSessionPowerExpresstion());
		sue.setRequestURL_grades(vo.getGradesExcpresstion());
		sue.setRequestURL_Identifiter(vo.getIdentifiterPowerExpression());
		
		this.controllerExpress=vo;
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
	
	/**
	 * 检验url相关联的表达式，取得其绑定在表达式上的相关[权限]
	 * @param url 请求路径
	 * @param controllerExpress 与他关联的表达式
	 * @return JWPCodeVO
	 */
//	JWPCodeVO getExpressPowerCode(String url) {
//		List<String[]> sessionCode = new ArrayList<>();
//		List<String[]> identifiter = new ArrayList<>();
//		String[] powercode;
//		boolean ishasSessionPowerCode = false, isHasIdentifiterPowerCode = false;
//		JWPCodeVO vo = new JWPCodeVO();
//		// 找到所有与url有关的表达式所附加的【权限等级】权限。
//		for (String express : this.controllerExpress.getSessionPowerExpresstion()) {
//			if (null != (powercode = this.expressTool.isGradePower(express, url))) {
//				ishasSessionPowerCode = true;
//				sessionCode.add(powercode);// 加入权限等级集合
//			}
//		}
//
//		// 找到所有与url有关的表达式所附加的【权限编号 】权限。
//		for (String express : this.controllerExpress.getIdentifiterPowerExpression()) {
//			if (null != (powercode = this.expressTool.isIdentifiterPower(express, url))) {
//				isHasIdentifiterPowerCode = true;
//				identifiter.add(powercode);// 加入权限编号集合
//			}
//		}
//		// 不是权限等级与权限编号 绑定的路径
//		if (sessionCode.isEmpty() && identifiter.isEmpty()) {
//			// 检测是否绑定了放行权限
//			for (String express : this.controllerExpress.getPublicPowerExpresstion()) {
//				if (this.expressTool.isPublicPower(express, url)) {
//					vo.setPublic(true);
//				}
//			}
//			return vo;
//		}
//		if (ishasSessionPowerCode) {
//			// 合并权限等级 权限
//			vo.setGrades(this.expressTool.mergeArrayList(sessionCode));
//		}
//		if (isHasIdentifiterPowerCode) {
//			// 合并权限编号 权限
//			vo.setIdentifiter(this.expressTool.mergeArrayList(identifiter));
//		}
//		return vo;
//	}
}
