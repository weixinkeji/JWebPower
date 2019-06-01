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
	private boolean isPublic = false;
	private boolean isSession = false;
	private String[] grades;
	private String[] identifiter;
	
	public static JWPCodeVO merge(JWPCodeVO vo1,JWPCodeVO vo2) {
		return new JWPCodeVO(
				vo1.isPublic?true:vo2.isPublic
				,vo1.isSession?true:vo2.isSession
				,JWPTool.mergeStringArray(vo1.grades,vo2.grades)
				,JWPTool.mergeStringArray(vo1.identifiter,vo2.identifiter)
				);
	}
	public JWPCodeVO(boolean isPublic, boolean isSession, String[] grades, String[] identifiter) {
		this.isPublic = isPublic;
		this.isSession = isSession;
		this.grades = grades;
		this.identifiter = identifiter;
		boolean identifiter_notNull = null != identifiter;
		boolean grades_notNull = null != this.grades;
		if (identifiter_notNull && grades_notNull) {
			this.type = JWPType.gradesAndIdentifiter;
		} else if (identifiter_notNull) {
			this.type = JWPType.identifiter;
		} else if (grades_notNull) {
			this.type = JWPType.grades;
		} else if (isSession) {
			this.type = JWPType.session;
		} else if (isPublic) {
			this.type = JWPType.common;
		}else {
			this.type=JWPType.unknow;
		}
	}
	
	public String[] getGrades() {
		return grades;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public boolean isSession() {
		return isSession;
	}

	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setGrades(String[] grades) {
		this.grades = grades;
	}

	public String[] getIdentifiter() {
		return identifiter;
	}

	public void setIdentifiter(String[] identifiter) {
		this.identifiter = identifiter;
	}
}
