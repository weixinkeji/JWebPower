package weixinkeji.vip.jweb.power.model;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.tools.JWPTool;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

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
public class JWPControllerModel {

	public final JWPType urlType;

	/**
	 * 权限等级。null时，表示出错。空表示所有等级。
	 */
	public final String[] grades;

	/**
	 * 绑定的绑定集合
	 */
	public final String[] identifier;

	public final boolean isPublic;
	public final boolean isSession;

	private final boolean isGradesNull;
	private final boolean isIdentifierNull;
	// 监听的实现
	public final JWPListenInterface listen;
	public final boolean isHasListen;

	/**
	 * 
	 * @param urlType    放行、会话、等级、编号、等级+编号
	 * @param grades     权限等级 没有权限请设置为null，有所有的权限请设置为new String[]{}
	 * @param identifier 权限编号 没有权限请设置为null，有所有的权限请设置为new String[]{}
	 * @param listen     IURLListenMethod 监听器
	 */
//	public JWPControllerModel(JWPType urlType, String[] grades, String[] identifier, JWPListenInterface listen) {
//		this.urlType = urlType;
//		this.grades = grades;
//		this.identifier = identifier;
//		this.isGradesNull = null == grades;
//		this.isIdentifierNull = null == identifier;
//		this.listen = listen;
//		this.isHasListen = null != this.listen;
//	}

	/**
	 * 
	 * @param vo    JWPCodeVO
	 * @param listen  JWPListenInterface 监听器
	 */
	public JWPControllerModel(JWPCodeVO vo, JWPListenInterface listen) {
		this.urlType = vo.type;
		this.isPublic = vo.isPublic();
		this.isSession = vo.isSession();
		this.grades = vo.getGrades();
		this.identifier = vo.getIdentifiter();
		
		this.isGradesNull = null == grades;
		this.isIdentifierNull = null == identifier;
		this.listen = listen;
		this.isHasListen = null != this.listen;
	}
	
	/**
	 * 融合 其他的 权限模型
	 * @param vo JWPCodeVO 权限 值对象
	 * @param jwp JWPControllerModel 其他的权限模型
	 * 
	 */
	public JWPControllerModel(JWPCodeVO vo,JWPControllerModel jwp) {
		this.isPublic = jwp.isPublic?true:vo.isPublic();
		this.isSession =jwp.isSession?true: vo.isSession();
		this.grades = JWPTool.mergeStringArray(jwp.grades,vo.getGrades());
		this.identifier =JWPTool.mergeStringArray(jwp.identifier,vo.getIdentifiter());
		this.isGradesNull = null == grades;
		this.isIdentifierNull = null == identifier;
		//重新定位 索引
		if (!isIdentifierNull && !isGradesNull) {//编号 与 等级 都不为null时
			this.urlType = JWPType.gradesAndIdentifiter;
		} else if (!isIdentifierNull) {//编号不为null时
			this.urlType = JWPType.identifiter;
		} else if (!isGradesNull) {//等级不为null时
			this.urlType = JWPType.grades;
		} else if (isSession) {//会话时
			this.urlType = JWPType.session;
		} else if (isPublic) {//公共区时
			this.urlType = JWPType.common;
		}else {//未知道类型
			this.urlType=JWPType.unknow;
		}
		
		this.listen=jwp.listen;
		this.isHasListen =jwp.isHasListen;
	}
	/**
	 * 判断权限等级 yourGrades 是否符合url绑定的 权限等级
	 * 
	 * @param yourGrades 你的权限等级
	 * @return boolean 真或假
	 */
	public boolean isInGrades(String yourGrades) {
		// 不是权限等级 控制的路径 或你没有权限
		if (this.isGradesNull || null == yourGrades) {
			return false;
		}
		if (grades.length == 0) {// 只要不为null的权限等级，都通过
			return true;
		}
		for (String str : grades) {
			if (str.equalsIgnoreCase(yourGrades)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断权限等级 yourGrades 是否符合url绑定的 权限等级
	 * 
	 * @param yourGrades 你的权限等级集合
	 * @return boolean
	 */
	public boolean isInGrades(String[] yourGrades) {
		// 不是权限等级 控制的路径 或你没有权限
		if (this.isGradesNull || null == yourGrades) {
			return false;
		}
		if (grades.length == 0) {// 只要不为null的权限等级，都通过
			return true;
		}
		for (String str : grades) {
			for (String yourStr : yourGrades) {
				if (str.equalsIgnoreCase(yourStr)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断权限编号 yourIdentifier 是否符合 url绑定的权限编号
	 * 
	 * @param yourIdentifier 你的权限编号
	 * @return boolean
	 */
	public boolean isInIdentifier(String yourIdentifier) {
		if (this.isIdentifierNull || null == yourIdentifier || yourIdentifier.trim().isEmpty()) {// 不是 代码权限控制 的路径
			return false;
		}
		for (String str : this.identifier) {
			if (str.equalsIgnoreCase(yourIdentifier)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断权限编号 yourIdentifier 是否符合 url绑定的权限编号
	 * 
	 * @param yourIdentifier String[] 你的权限编号集合
	 * @return boolean
	 */
	public boolean isInIdentifier(String[] yourIdentifier) {
		if (this.isIdentifierNull || null == yourIdentifier || yourIdentifier.length == 0) {
			return false;
		}
		for (String str : identifier) {
			for (String yourStr : yourIdentifier) {
				if (str.equalsIgnoreCase(yourStr)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断权限等级 yourGrades 权限编号 yourIdentifier 是否符合url绑定的 权限等级、权限编号
	 * 
	 * @param yourGrades     String 你的权限等级
	 * @param yourIdentifier String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndIdentifier(String yourGrades, String[] yourIdentifier) {
		return this.isInGrades(yourGrades) && this.isInIdentifier(yourIdentifier);
	}

	/**
	 * 判断权限等级 yourGrades 权限编号 yourIdentifier 是否符合url绑定的 权限等级、权限编号
	 * 
	 * @param yourGrades     String[] 你的权限等级
	 * @param yourIdentifier String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndIdentifier(String yourGrades[], String[] yourIdentifier) {
		return this.isInGrades(yourGrades) && this.isInIdentifier(yourIdentifier);
	}

}
