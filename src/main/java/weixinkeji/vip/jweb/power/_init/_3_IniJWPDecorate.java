package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;

import weixinkeji.vip.jweb.power.ann.JWPCode;
import weixinkeji.vip.jweb.power.ann.JWPDecorate;
import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIgnoreDecorate;
import weixinkeji.vip.jweb.power.config.JWPDecorateConfig;
import weixinkeji.vip.jweb.power.tools.JWPTool;

public class _3_IniJWPDecorate extends __InitTool{

	private JWPDecorateConfig config;
	protected Class<?> c;
	private  String codePrefix;//会自动在编号前，加入此值
	private  String codeSuffix;//会自动在编号后，加入此值
	private  String gradesPrefix;//会自动在等级前，加入此值
	private  String gradesSuffix;//会自动在等级后，加入此值
	
	_3_IniJWPDecorate(JWPDecorateConfig config) {
		super(null);
		this.config=config;
	}
	
	void  setClass(Class<?> c) {
		this.c=c;
		JWPDecorate jdc=c.getAnnotation(JWPDecorate.class);
		if(null!=jdc) {
			this.codePrefix=jdc.codePrefix();
			this.codeSuffix=jdc.codeSffix();
			this.gradesPrefix=jdc.gradesPrefix();
			this.gradesSuffix=jdc.gradesSffix();
		}else if(null!=config){
			this.codePrefix=config.codePrefix(c);
			this.codeSuffix=config.codeSuffix(c);
			this.gradesPrefix=config.gradesPrefix(c);
			this.gradesSuffix=config.gradesSuffix(c);
		}else{
			this.codePrefix="";
			this.codeSuffix="";
			this.gradesPrefix="";
			this.gradesSuffix="";
		}
	}
	
	
	/**
	 * 取得JWPCode 实例里的编号
	 * @param code 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getCode(JWPCode code) {
		return JWPTool.formatMyArray(code.value(),this.codePrefix, this.codeSuffix);
	}
	/**
	 * 取得JWPCode 实例里的编号，同时会根据方法，自动判读是否要无视 装饰配置
	 * @param code 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getCode(JWPCode code,Method m) {
		return null==m.getAnnotation(JWPIgnoreDecorate.class)?
				JWPTool.formatMyArray(code.value(),this.codePrefix, this.codeSuffix)
				:JWPTool.formatMyArray(code.value());
	}
	/**
	 * 以方法名，当作编号。并且，当方法名带有_时，会自动切割成多个编号
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getCodeByMethodName(Method m) {
		return null==m.getAnnotation(JWPIgnoreDecorate.class)?
				JWPTool.formatMyArray(m.getName().split("_"),this.codePrefix, this.codeSuffix)
				:JWPTool.formatMyArray(m.getName().split("_"));
	}
	/**
	 * 取得JWPCode 实例里的等级
	 * @param grades 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getGrades(JWPGrades grades) {
		return JWPTool.formatMyArray(grades.value(),this.gradesPrefix, this.gradesSuffix);
	}
	/**
	 * 取得JWPCode 实例里的等级，同时会根据方法，自动判读是否要无视 装饰配置
	 * @param grades 注解类
	 * @param m   方法
	 * @return  String[]
	 */
	String[] getGrades(JWPGrades grades,Method m) {
		return null==m.getAnnotation(JWPIgnoreDecorate.class)?
				JWPTool.formatMyArray(grades.value(),this.gradesPrefix, this.gradesSuffix)
				:JWPTool.formatMyArray(grades.value());
	}
	
	
	
	
}
