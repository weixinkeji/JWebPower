package weixinkeji.vip.jweb.power.vo;

public class JWPUserPower {

	public final String[] grades;
	public final String[] code;
	
	/**
	 * 
	 * @param grades 权限等级
	 * @param code	 权限编号
	 */
	public JWPUserPower(String[] grades, String[] code) {
		this.grades = grades;
		this.code = code;
	}
}
