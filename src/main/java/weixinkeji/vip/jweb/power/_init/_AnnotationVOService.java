package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;

import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIdentifiter;
import weixinkeji.vip.jweb.power.ann.JWPListen;
import weixinkeji.vip.jweb.power.ann.JWPPublic;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.JWPListenPool;
import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

/**
 * 所在可能标注在类上的注解
 * 
 * @author wangchunzi
 */
class _AnnotationVOService {

	private _JWPAnnotationVO_Class cVo;
	private _JWPAnnotationVO_Method mVo;

	public _AnnotationVOService(Class<?> c) {
		this.cVo = new _JWPAnnotationVO_Class(c);
	}

	public void setMethod(Method m) {
		this.mVo = new _JWPAnnotationVO_Method(m);
	}

	/**
	 * 取出在类-方法  中的最终 数据
	 * @return JWPCodeVO
	 */
	public _JWPAnnotationVO get_JWPAnnotationVO() {
		return new _JWPAnnotationVO(
				null==mVo.powerType?cVo.powerType:mVo.powerType
				,null==mVo.codeVO?cVo.codeVO:mVo.codeVO
				,null==mVo.jwepListen?cVo.jwepListen:mVo.jwepListen
				);
	}

}
/**
 * 核心
 * 
 * @author wangchunzi
 */
class _JWPAnnotationVO{
	/**
	 * 计算权限优先级，从而得出类的权限归属（是公共，还是等级，还是编号，还是混合的权限）
	 */
	final JWPType powerType;
	/**
	 * 计算注解在类上的权限代码
	 */
	final JWPCodeVO codeVO;
	/**
	 * 检查 listen 注解，提取实现监听的类 注：前提是 {@listen} 必须存在，并有值。
	 */
	final JWPListenInterface jwepListen;
	public _JWPAnnotationVO(JWPType powerType, JWPCodeVO codeVO, JWPListenInterface jwepListen) {
		super();
		this.powerType = powerType;
		this.codeVO = codeVO;
		this.jwepListen = jwepListen;
	}
}

/**
 * 所在可能标注在类上的注解
 * 
 * @author wangchunzi
 */
class _JWPAnnotationVO_Class extends _InitTool {
	/**
	 * 计算权限优先级，从而得出类的权限归属（是公共，还是等级，还是编号，还是混合的权限）
	 */
	final JWPType powerType;
	/**
	 * 计算注解在类上的权限代码
	 */
	final JWPCodeVO codeVO;
	/**
	 * 检查 listen 注解，提取实现监听的类 注：前提是 {@listen} 必须存在，并有值。
	 */
	final JWPListenInterface jwepListen;

	public _JWPAnnotationVO_Class(Class<?> c) {
		// 可能标注在类上的权限标识符
		// 可能标注在方法上的权限标识符
		JWPPublic ann_public = c.getAnnotation(JWPPublic.class);
		JWPGrades ann_grades = c.getAnnotation(JWPGrades.class);
		JWPIdentifiter ann_identifiter = c.getAnnotation(JWPIdentifiter.class);
		JWPListen ann_listen = c.getAnnotation(JWPListen.class);
		// 监听实现类
		jwepListen = null == ann_listen ? null : JWPListenPool.getIURLListenMethod(ann_listen.value());
		// 计算归属与拥有的权限
		powerType = getURLPowerType(ann_public, ann_grades, ann_identifiter);
		codeVO = getPowerCode(powerType, ann_grades, ann_identifiter);

	}
}

/**
 * 所在可能标注在方法上的注解
 * 
 * @author wangchunzi
 */
class _JWPAnnotationVO_Method extends _InitTool {
	/**
	 * 计算权限优先级，从而得出方法的权限归属（是公共，还是等级，还是编号，还是混合的权限）
	 */
	final JWPType powerType;
	/**
	 * 计算注解在方法上的权限代码
	 */
	final JWPCodeVO codeVO;
	/**
	 * 检查 listen 注解，提取实现监听的方法 注：前提是 {@listen} 必须存在，并有值。
	 */
	final JWPListenInterface jwepListen;
	
	public _JWPAnnotationVO_Method(Method method) {
		if(null==method) {
			jwepListen = null;
			powerType=null;
			codeVO=null;
			return;
		}
		// 可能标注在方法上的权限标识符
		JWPPublic ann_public = method.getAnnotation(JWPPublic.class);
		JWPGrades ann_grades = method.getAnnotation(JWPGrades.class);
		JWPIdentifiter ann_identifiter = method.getAnnotation(JWPIdentifiter.class);
		JWPListen ann_listen = method.getAnnotation(JWPListen.class);

		jwepListen = null == ann_listen ? null : JWPListenPool.getIURLListenMethod(ann_listen.value());
		powerType = getURLPowerType(ann_public, ann_grades, ann_identifiter);
		codeVO = getPowerCode(powerType, ann_grades, ann_identifiter);

	}
}
