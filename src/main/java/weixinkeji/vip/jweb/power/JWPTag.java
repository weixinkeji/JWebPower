package weixinkeji.vip.jweb.power;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import weixinkeji.vip.jweb.power.vo.JWPUserPower;

public class JWPTag extends SimpleTagSupport {

	boolean session;
	private String grades=null;
	private String code=null;
	private JWPUserPower p=null;
	private PageContext pc=null;
	private HttpServletRequest req=null;
	private HttpServletResponse resp=null;
	
	@Override
	public void doTag() throws JspException, IOException {
		pc = (PageContext) super.getJspContext();
		req = (HttpServletRequest) pc.getRequest();
		resp = (HttpServletResponse) pc.getResponse();
		try {
			p = JWPFilterCommonInterface.getPowergetUserPowerCode(req,resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			p = null;
		}
		
		boolean b_grades_notNull=null != grades;
		boolean b_code_notNull=null != this.code;
		
		//只需登录即可访问
		if(!b_code_notNull&&!b_code_notNull&&this.session&& null!=p) {
			//显示内容
			super.getJspBody().invoke(pc.getOut());
			return;
		}
		
		
		try {
			
			if (b_grades_notNull &&b_code_notNull ) {
				
				if (null==p||code.isEmpty()||null==p.grades||null==p.code) {
					return;// 不合法的编号
				}
				if (this.isHasCodePower(this.code, req, resp)// 并拥有指定的权限时
						&&(this.grades.isEmpty()||this.isHasGradesPower(grades, req, resp))//并且 （空等级-表示全部等级都可以  或 有明确的等级权限 ）
						) {
					//显示内容
					super.getJspBody().invoke(pc.getOut());
				}
				return;
			}
			
			if (b_code_notNull) {
				//输入非法值  或  用户没有编号权限时时
				if (null==p||code.isEmpty()||null==p.code||p.code.length==0) {
					return;// 不合法的编号
				}
				// 拥有指定的权限时
				if (this.isHasCodePower(this.code, req, resp)) {
					//显示内容
					super.getJspBody().invoke(pc.getOut());
				}
				return;
			}
			if (b_grades_notNull) {
				//用户没有等级权限时时
				if (null==p||null==p.grades||p.grades.length==0) {
					return;// 不合法的编号
				}
				// 拥有指定的权限等级时（空表示所有权限等级都可以通过）
				if (this.grades.isEmpty()||this.isHasGradesPower(this.grades, req, resp)) {
					//显示内容
					super.getJspBody().invoke(pc.getOut());
				}
				return;
			}
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public boolean isHasGradesAndCodePower(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		return this.isHasGradesPower(grades, req, resp) && this.isHasCodePower(code, req, resp);
	}

	public boolean isHasGradesPower(String yourCode, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (!yourCode.contains(",")) {
			for (String code : p.grades) {
				if (yourCode.equals(code)) {
					return true;
				}
			}
		} else {
			for (String code : p.grades) {
				for (String ycode : yourCode.split(",")) {
					if (ycode.equals(code)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isHasCodePower(String yourCode, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (!yourCode.contains(",")) {
			for (String code : p.code) {
				if (yourCode.equals(code)) {
					return true;
				}
			}
		} else {
			for (String code : p.code) {
				for (String ycode : yourCode.split(",")) {
					if (ycode.equals(code)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isSession() {
		return session;
	}

	public String getGrades() {
		return grades;
	}

	public String getCode() {
		return code;
	}

	public void setSession(boolean session) {
		this.session = session;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
