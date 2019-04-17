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
public class JWebPowerControllerModel {

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

	/**
	 * 
	 * @param urlType    0:放行区 1：会话区 2：编号区 10:会话+编号 一起
	 * @param grades     会员等级 没有权限请设置为null，有所有的权限请设置为new String[]{}
	 * @param identifier 权限编号 没有权限请设置为null，有所有的权限请设置为new String[]{}
	 */
	public JWebPowerControllerModel(JWebPowerType urlType, String[] grades, String[] identifier) {
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
		if (this.isGradesNull || null == yourGrades) {
			return false;
		}
		if(grades.length==0) {//只要不为null的会员等级，都通过
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
	 * 判断会员等级 yourGrades 是否符合url绑定的 会员等级
	 * 
	 * @param yourGrades 你的会员等级集合
	 * @return boolean
	 */
	public boolean isInGrades(String[] yourGrades) {
		// 不是会员等级 控制的路径 或你没有权限
		if (this.isGradesNull || null == yourGrades) {
			return false;
		}
		if(grades.length==0) {//只要不为null的会员等级，都通过
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
	 * @param yourGrades     String 你的会员等级
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
	 * @param yourGrades     String[] 你的会员等级
	 * @param yourIdentifier String[] 你的权限编号集合
	 * 
	 * @return boolean
	 */
	public boolean isInGradesAndIdentifier(String yourGrades[], String[] yourIdentifier) {
		return this.isInGrades(yourGrades) && this.isInIdentifier(yourIdentifier);
	}

}
