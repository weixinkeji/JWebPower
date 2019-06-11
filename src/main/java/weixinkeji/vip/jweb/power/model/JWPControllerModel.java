package weixinkeji.vip.jweb.power.model;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.tools.JWPTool;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;
import weixinkeji.vip.jweb.power.vo.JWPUserPower;

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
	public final String[] code;

	public final boolean isCommon;
	public final boolean isSession;

	private final boolean isGradesNull;
	private final boolean isCodeNull;
	// 监听的实现
	public final JWPListenInterface listen;
	public final JWPListenInterface[] listens;
	public final int listenCount;
	public final boolean isHasListen;

	/**
	 * 
	 * @param vo     JWPCodeVO
	 * @param listen JWPListenInterface 监听器
	 */
	public JWPControllerModel(JWPCodeVO vo, JWPListenInterface listen) {
		this.isCommon = vo.isCommon();
		this.isSession = vo.isSession();
		this.grades = vo.getGrades();
		this.code = vo.getCode();

		this.isGradesNull = null == grades;
		this.isCodeNull = null == code;
		this.listen = listen;
		this.isHasListen = null != this.listen;
		this.listens=null;
		this.listenCount=0;
		if (this.isHasListen && vo.type == JWPType.unknow) {
			this.urlType = JWPType.onlyListen;
		} else {
			this.urlType = vo.type;
		}

	}
	public JWPControllerModel(JWPCodeVO vo, JWPListenInterface[] listens) {
		this.isCommon = vo.isCommon();
		this.isSession = vo.isSession();
		this.grades = vo.getGrades();
		this.code = vo.getCode();

		this.isGradesNull = null == grades;
		this.isCodeNull = null == code;
		this.listen = null;
		this.isHasListen = null != this.listen;
		this.listens=listens;
		this.listenCount=null!=listens?listens.length:0;
		
		if (this.listenCount>0 && vo.type == JWPType.unknow) {
			this.urlType = JWPType.onlyListen;
		} else {
			this.urlType = vo.type;
		}

	}
	/**
	 * 融合 其他的 权限模型
	 * 
	 * @param vo  JWPCodeVO 权限 值对象
	 * @param jwp JWPControllerModel 其他的权限模型
	 * 
	 */
	public JWPControllerModel(JWPCodeVO vo, JWPControllerModel jwp) {
		this.isCommon = jwp.isCommon ? true : vo.isCommon();
		this.isSession = jwp.isSession ? true : vo.isSession();
		this.grades = JWPTool.mergeStringArray(jwp.grades, vo.getGrades());
		this.code = JWPTool.mergeStringArray(jwp.code, vo.getCode());
		this.isGradesNull = null == grades;
		this.isCodeNull = null == code;
		// 重新定位 索引
		if (!isCodeNull && !isGradesNull) {// 编号 与 等级 都不为null时
			this.urlType = JWPType.gradesAndCode;
		} else if (!isCodeNull) {// 编号不为null时
			this.urlType = JWPType.code;
		} else if (!isGradesNull) {// 等级不为null时
			this.urlType = JWPType.grades;
		} else if (isSession) {// 会话时
			this.urlType = JWPType.session;
		} else if (isCommon) {// 公共区时
			this.urlType = JWPType.common;
		} else {// 未知道类型
			this.urlType = JWPType.unknow;
		}

		this.listen = jwp.listen;
		this.isHasListen = jwp.isHasListen;
		
		this.listenCount=jwp.listenCount;
		this.listens=jwp.listens;
		
	}

	/**
	 * 执行监听方法
	 * @param chain      FilterChain
	 * @param req  HttpServletRequest
	 * @param resp	HttpServletResponse
	 * @param requestURL 请求路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param powerCode 用户的权限
	 * 
	 * @return boolean 返回true表示放行，返回 false表示 拦截下来。
	 * 
	 * @throws IOException  IO流异常
	 * @throws ServletException javax.servlet.ServletException
	 */
	public boolean doListen(FilterChain chain, HttpServletRequest req, HttpServletResponse resp, final String requestURL
			,JWPControllerModel powerModel
			,JWPUserPower powerCode)throws IOException, ServletException{
		switch(this.listenCount) {
			case 0:return true;
			case 1:return this.listens[0].doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			case 2:
				return 
						this.listens[0].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[1].doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			case 3:
				return 
						this.listens[0].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[1].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[2].doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			case 4:
				return 
						this.listens[0].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[1].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[2].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[3].doMethod(chain, req, resp, requestURL, powerModel, powerCode);	
			case 5:
				return 
						this.listens[0].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[1].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[2].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[3].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[4].doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			case 6:
				return 
						this.listens[0].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[1].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[2].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[3].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[4].doMethod(chain, req, resp, requestURL, powerModel, powerCode)
						&&this.listens[5].doMethod(chain, req, resp, requestURL, powerModel, powerCode);
			default:{
				for(JWPListenInterface listenObject:this.listens) {
					//一旦发现监听结果是不通过，立马返回false，表示拦截
					if(!listenObject.doMethod(chain, req, resp, requestURL, powerModel, powerCode)) {
						return false;
					}
				}
				return true;
			}
		}
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
	 * 判断权限编号 code 是否符合 url绑定的权限编号
	 * 
	 * @param code 你的权限编号
	 * @return boolean
	 */
	public boolean isCode(String code) {
		if (this.isCodeNull || null == code || code.trim().isEmpty()) {// 不是 代码权限控制 的路径
			return false;
		}
		for (String str : this.code) {
			if (str.equalsIgnoreCase(code)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断权限编号 code 是否符合 url绑定的权限编号
	 * 
	 * @param code String[] 你的权限编号集合
	 * @return boolean
	 */
	public boolean isCode(String[] code) {
		if (this.isCodeNull || null == code || code.length == 0) {
			return false;
		}
		for (String str : code) {
			for (String yourStr : code) {
				if (str.equalsIgnoreCase(yourStr)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断权限等级 yourGrades 权限编号 code 是否符合url绑定的 权限等级、权限编号
	 * 
	 * @param yourGrades String 你的权限等级
	 * @param code       String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndCode(String yourGrades, String[] code) {
		return this.isInGrades(yourGrades) && this.isCode(code);
	}

	/**
	 * 判断权限等级 yourGrades 权限编号 code 是否符合url绑定的 权限等级、权限编号
	 * 
	 * @param yourGrades String[] 你的权限等级
	 * @param code       String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndCode(String yourGrades[], String[] code) {
		return this.isInGrades(yourGrades) && this.isCode(code);
	}

}
