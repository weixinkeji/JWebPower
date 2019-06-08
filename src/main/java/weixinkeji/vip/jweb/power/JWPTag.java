package weixinkeji.vip.jweb.power;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class JWPTag extends SimpleTagSupport {
	
	boolean isCommon;
	boolean isSession;
	private String grades;
	private String code;
	
	
	@Override
	public void doTag() throws JspException, IOException {
		System.out.println(isCommon+"//"+isSession+"//"+grades+"//"+code);
		super.getJspContext().getOut().write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}
	

	public boolean isCommon() {
		return isCommon;
	}


	public boolean isSession() {
		return isSession;
	}


	public String getGrades() {
		return grades;
	}


	public String getCode() {
		return code;
	}


	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}


	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}


	public void setGrades(String grades) {
		this.grades = grades;
	}


	public void setCode(String code) {
		this.code = code;
	}
}
