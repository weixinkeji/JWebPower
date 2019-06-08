package weixinkeji.vip.jweb.power.vo;

import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.tools.JWPTool;

/**
 * 存储临时 值。返回多值* 封装
 * 
 * @author wangchunzi
 */
final public class JWPCodeVO {
	public final JWPType type;
	private boolean isCommon = false;
	private boolean isSession = false;
	private String[] grades;
	private String[] code;
	
	public static JWPCodeVO merge(JWPCodeVO vo1,JWPCodeVO vo2) {
		return new JWPCodeVO(
				vo1.isCommon?true:vo2.isCommon
				,vo1.isSession?true:vo2.isSession
				,JWPTool.mergeStringArray(vo1.grades,vo2.grades)
				,JWPTool.mergeStringArray(vo1.code,vo2.code)
				);
	}
	public JWPCodeVO(boolean isPublic, boolean isSession, String[] grades, String[] code) {
		this.grades = grades;
		this.code = code;
		boolean identifiter_notNull = null != code;
		boolean grades_notNull = null != this.grades;
		if (identifiter_notNull && grades_notNull) {
			this.type = JWPType.gradesAndCode;
		} else if (identifiter_notNull) {
			this.type = JWPType.code;
		} else if (grades_notNull) {
			this.type = JWPType.grades;
		} else if (isSession) {
			this.type = JWPType.session;
		} else if (isPublic) {
			this.type = JWPType.common;
		}else {
			this.type=JWPType.unknow;
		}
		//有等级与编号 ，都意味着用户已经登陆。所以，他们一旦存在，直接就设置
		this.isSession =identifiter_notNull||grades_notNull?false: isSession;
		
		this.isCommon =
				identifiter_notNull||grades_notNull
				?false//有等级与编号 ，都意味着 绝不可能是放行。直接设置成false
				:(this.isSession?false:isPublic);//如果设置了会话，绝不可能是放行区。直接设置false
		
	}
	
	public String[] getGrades() {
		return grades;
	}
	public boolean isCommon() {
		return isCommon;
	}
	public String[] getCode() {
		return code;
	}
	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}
	public void setCode(String[] code) {
		this.code = code;
	}
	public boolean isSession() {
		return isSession;
	}

	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}

	public void setGrades(String[] grades) {
		this.grades = grades;
	}
}
