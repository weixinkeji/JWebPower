package weixinkeji.vip.jweb.power.vo;

/**
 * 存储临时 值。返回多值* 封装
 * 
 * @author wangchunzi
 */
final public class JWPCodeVO {
	private boolean isPublic = false;
	private String[] grades;
	private String[] identifiter;

	public String[] getGrades() {
		return grades;
	}

	public boolean isPublic() {
		return isPublic;
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
