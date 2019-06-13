package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;

import weixinkeji.vip.jweb.power.ann.JWPCode;
import weixinkeji.vip.jweb.power.ann.JWPDecorate;
import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIgnoreDecorate;
import weixinkeji.vip.jweb.power.tools.JWPTool;
import weixinkeji.vip.jweb.power.vo.JWPUserConfigVO;

public class _3_IniJWPDecorate extends __InitTool{

	private JWPUserConfigVO config;
	protected Class<?> c;
	private  String codePrefix;//会自动在编号前，加入此值
	private  String codeSffix;//会自动在编号后，加入此值
	private  String gradesPrefix;//会自动在等级前，加入此值
	private  String gradesSffix;//会自动在等级后，加入此值
	
	//---------开启自动编号、等级 的装饰（在编号或等级前，自动加入一些前缀或后缀）
	// {CcC}  大写C,小写c,大写C   ：表示权限所在类的 类名
	// {ccC}  小写c,小写c,大写C   ：表示权限所在类的 类名(并强制小写类名的首字母)
	// {ccc}  小写c,小写c,小写c   ：表示权限所在类的 类名(并强制小写类名的所有字母)
	// {CCC}  大写C,大写C,大写C   ：表示权限所在类的 类名(并强制大写类名的所有字母)
	private String CcC1;
	private String ccC2;
	private String ccc3;
	private String CCC4;
	
	_3_IniJWPDecorate(JWPUserConfigVO config) {
		super(null);
		this.config=config;
	}
	
	void  setClass(Class<?> c) {
		this.c=c;
		JWPDecorate jdc=c.getAnnotation(JWPDecorate.class);
		if(null!=jdc) {
			this.codePrefix=jdc.codePrefix();
			this.codeSffix=jdc.codeSffix();
			this.gradesPrefix=jdc.gradesPrefix();
			this.gradesSffix=jdc.gradesSffix();
		}else if(config.jwp_decorate_auto_map){
			initformateClassName();
			this.codePrefix=initDecorateValue(config.jwp_decorate_codePrefix);
			this.codeSffix=initDecorateValue(config.jwp_decorate_codeSffix);
			this.gradesPrefix=initDecorateValue(config.jwp_decorate_gradesPrefix);
			this.gradesSffix=initDecorateValue(config.jwp_decorate_gradesSffix);
		}else{
			this.codePrefix="";
			this.codeSffix="";
			this.gradesPrefix="";
			this.gradesSffix="";
		}
	}
	
	// {c1}  ：表示权限所在类的 类名
	// {c2}  ：表示权限所在类的 类名(并强制小写类名的首字母)
	// {c3}  ：表示权限所在类的 类名(并强制小写类名的所有字母)
	// {c4}  ：表示权限所在类的 类名(并强制大写类名的所有字母)	
	private void initformateClassName() {
		String name=this.c.getSimpleName();
		this.CcC1=name;
		this.ccC2=name.substring(0,1).toLowerCase()+name.substring(1);
		this.ccc3=name.toLowerCase();
		this.CCC4=name.toUpperCase();
	}
	private String initDecorateValue(String value) {
		return null==value||value.isEmpty()?"":value
				.replace("{c1}", this.CcC1)
				.replace("{c2}", this.ccC2)
				.replace("{c3}", this.ccc3)
				.replace("{c4}", this.CCC4);
	}
	/**
	 * 取得JWPCode 实例里的编号
	 * @param code 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getCode(JWPCode code) {
		return JWPTool.formatMyArray(code.value(),this.codePrefix, this.codeSffix);
	}
	/**
	 * 取得JWPCode 实例里的编号，同时会根据方法，自动判读是否要无视 装饰配置
	 * @param code 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getCode(JWPCode code,Method m) {
		return null==m.getAnnotation(JWPIgnoreDecorate.class)?
				JWPTool.formatMyArray(code.value(),this.codePrefix, this.codeSffix)
				:JWPTool.formatMyArray(code.value());
	}
	
	/**
	 * 取得JWPCode 实例里的等级
	 * @param grades 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getGrades(JWPGrades grades) {
		return JWPTool.formatMyArray(grades.value(),this.gradesPrefix, this.gradesSffix);
	}
	/**
	 * 取得JWPCode 实例里的等级，同时会根据方法，自动判读是否要无视 装饰配置
	 * @param grades 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getGrades(JWPGrades grades,Method m) {
		return null==m.getAnnotation(JWPIgnoreDecorate.class)?
				JWPTool.formatMyArray(grades.value(),this.gradesPrefix, this.gradesSffix)
				:JWPTool.formatMyArray(grades.value());
	}
	
	
	
	
}
