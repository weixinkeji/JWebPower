//package weixinkeji.vip.jweb.power.vo;
//
///**
// * 用来装箱 “表达式 [[权限等级]]” 的切割后的值
// * 
// * @author wangchunzi
// *
// */
//final public class IdentifiterPowerExpressVO {
//	/**
//	 * 表达式
//	 */
//	private String express;
//	/**
//	 * 权限等级。null时，表示出错。空表示所有等级。
//	 */
//	private String[] grades;
//
//	public String getExpress() {
//		return express;
//	}
//
//	public void setExpress(String express) {
//		this.express = null == express ? null : express.trim();
//	}
//
//	public String[] getGrades() {
//		return grades;
//	}
//
//	public void setGrades(String[] grades) {
//		if (null != grades) {
//			for (int i = 0; i < grades.length; i++) {
//				grades[i] = grades[i].trim();
//			}
//		}
//		this.grades = grades;
//	}
//
//}
