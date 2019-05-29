package weixinkeji.vip.jweb.power._init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weixinkeji.vip.jweb.power.ann.JWPGrades;
import weixinkeji.vip.jweb.power.ann.JWPIdentifiter;
import weixinkeji.vip.jweb.power.ann.JWPPublic;
import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;

public abstract class _InitTool {
	protected List<Class<?>> list;
	/**
	 *  需要扫描类的集合
	 * @param list 扫描到的类
	 */
	_InitTool() {
		this.list = new ArrayList<>();
	}
	
	
	/**
	 *  需要扫描类的集合
	 * @param list 扫描到的类
	 */
	_InitTool(List<Class<?>> list) {
		this.list = list;
	}

	/**
	 * 工具：找到T的实现类，并返回一个实例。
	 * <p>
	 * 找不到，则返回用户指定的实例。
	 * 
	 * @param yourClass 你要找的类型
	 * @param obj       用户指定的实例
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	<T> T findObject(Class<T> yourClass, T obj) {
		for (Class<?> c : list) {
			// 从集合中，找到实现了IPublicSystemInterfaceConfig接口的类。
			if (yourClass.isAssignableFrom(c) && !c.equals(yourClass)) {
				try {
					obj = null;
					return (T) c.getConstructor().newInstance();
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				}
			}
		}
		return obj;
	}
	
	/**
	 * 把 {"a,a1","b"} 变成 {"a","a1","b"}
	 * 
	 * @param powers String[] 权限数据
	 * @return String[]
	 */
	protected String[] getStringArray(String[] powers) {
		Set<String> set = new HashSet<>();
		String[] sonPower;
		for (String p : powers) {
			if (p.contains(",")) {
				sonPower = p.split(",");
				for (String son : sonPower) {
					set.add(son);
				}
			} else {
				set.add(p);
			}
		}
		String[] mypower = new String[set.size()];
		return set.toArray(mypower);
	}
	



	/**
	 * 取得权限代码
	 * 
	 * @param type JWebPowerType 权限类型
	 * @param sp   SessionPower 注解：权限等级
	 * @param ip   IdentifiterPower 注解：权限编号
	 * @return SessionCodeAndIdentifiterCodeVO
	 */
	protected JWPCodeVO getPowerCode(JWPType type, JWPGrades sp, JWPIdentifiter ip) {
		if (null == type) {
			return null;
		}
		JWPCodeVO vo = new JWPCodeVO();
		switch (type) {
		case common:
			return vo;
		case grades:
			vo.setGrades(this.getStringArray(sp.value()));
			return vo;
		case identifiter:
			vo.setIdentifiter(this.getStringArray(ip.value()));
			return vo;
		case gradesAndIdentifiter:
			vo.setGrades(this.getStringArray(sp.value()));
			vo.setIdentifiter(this.getStringArray(ip.value()));
			return vo;
		default:
			return null;
		}
	}

	/**
	 * 取得权限类型
	 * 
	 * @param pp PublicPower 注解：公共权限
	 * @param sp SessionPower 注解：权限等级
	 * @param ip IdentifiterPower 注解：权限编号
	 * @return JWebPowerType
	 */
	protected JWPType getURLPowerType(JWPPublic pp, JWPGrades sp, JWPIdentifiter ip) {
		if (null != sp && null != ip) {// 权限等级+权限编号
			return JWPType.gradesAndIdentifiter;
		}
		if (null != sp) {// 仅权限等级
			return JWPType.grades;
		}
		if (null != ip) {// 仅权限编号
			return JWPType.identifiter;
		}
		if (null != pp) {// 默认公共路径
			return JWPType.common;
		}
		return null;
	}

	protected JWPType getURLPowerType(JWPCodeVO powerCode) {
		if (null != powerCode.getGrades() && null != powerCode.getIdentifiter()) {// 权限等级+权限编号
			return JWPType.gradesAndIdentifiter;
		}
		if (null != powerCode.getGrades()) {// 仅权限等级
			return JWPType.grades;
		}
		if (null != powerCode.getIdentifiter()) {// 仅权限编号
			return JWPType.identifiter;
		}
		return powerCode.isPublic() ? JWPType.common : null;
	}
}
