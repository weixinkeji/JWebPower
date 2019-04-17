package weixinkeji.vip.jweb.power.model;

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
public class JWebPowerStaticResourcesModel {

	public final JWebPowerType urlType;
	/**
	 * 会员等级。null时，表示出错。空表示所有等级。
	 */
	public final String[] grades;
	
	/**
	 * 绑定的绑定集合
	 */
	public final String[] identifier;
	private final boolean isGradesNull;
	private final boolean isIdentifierNull;

	public JWebPowerStaticResourcesModel(JWebPowerType urlType, String[] grades, String[] identifier) {
		this.urlType = urlType;
		this.grades = grades;
		this.identifier = identifier;
		this.isGradesNull = null == grades;
		this.isIdentifierNull = null == identifier;
	}

	/**
	 * 判断会员等级 yourGrades 是否符合url绑定的 会员等级
	 * 
	 * @param yourGrades 你的会员等级
	 * @return boolean
	 */
	public boolean isInGrades(String yourGrades) {
		// 不是会员等级 控制的路径 或你没有权限
		if (this.isGradesNull || null == yourGrades || yourGrades.trim().isEmpty()) {
			return false;
		}
		for (String str : grades) {
			if (str.equalsIgnoreCase(yourGrades)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断会员等级 yourGrades 是否符合url绑定的 会员等级
	 * 
	 * @param yourGrades 你的会员等级集合
	 * @return boolean
	 */
	public boolean isInGrades(String[] yourGrades) {
		// 不是会员等级 控制的路径 或你没有权限
		if (this.isGradesNull || null == yourGrades || yourGrades.length == 0) {
			return false;
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
	 * 判断权限编号 yourIdentifier 是否符合 url绑定的权限编号
	 * 
	 * @param yourIdentifier 你的权限编号
	 * @return boolean
	 */
	public boolean isInIdentifier(String yourIdentifier) {
		if (this.isIdentifierNull || null == yourIdentifier || yourIdentifier.trim().isEmpty()) {// 不是 代码权限控制 的路径
			return false;
		}
		for (String str : this.identifier) {
			if (str.equalsIgnoreCase(yourIdentifier)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断权限编号 yourIdentifier 是否符合 url绑定的权限编号
	 * 
	 * @param yourIdentifier String[] 你的权限编号集合
	 * @return boolean
	 */
	public boolean isInIdentifier(String[] yourIdentifier) {
		if (this.isIdentifierNull || null == yourIdentifier || yourIdentifier.length == 0) {
			return false;
		}
		for (String str : identifier) {
			for (String yourStr : yourIdentifier) {
				if (str.equalsIgnoreCase(yourStr)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断会员等级 yourGrades 权限编号 yourIdentifier 是否符合url绑定的 会员等级、权限编号
	 * 
	 * @param yourGrades String 你的会员等级
	 * @param yourIdentifier String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndIdentifier(String yourGrades, String[] yourIdentifier) {
		return this.isInGrades(yourGrades) && this.isInIdentifier(yourIdentifier);
	}
	/**
	 * 判断会员等级 yourGrades 权限编号 yourIdentifier 是否符合url绑定的 会员等级、权限编号
	 * 
	 * @param yourGrades String[] 你的会员等级
	 * @param yourIdentifier String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndIdentifier(String yourGrades[], String[] yourIdentifier) {
		return this.isInGrades(yourGrades) && this.isInIdentifier(yourIdentifier);
	}
	
}
