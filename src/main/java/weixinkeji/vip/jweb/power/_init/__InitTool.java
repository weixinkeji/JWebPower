package weixinkeji.vip.jweb.power._init;

import java.util.ArrayList;
import java.util.List;

public abstract class __InitTool {
	protected List<Class<?>> list;
	/**
	 *  需要扫描类的集合
	 * @param list 扫描到的类
	 */
	__InitTool() {
		this.list = new ArrayList<>();
	}
	
	/**
	 *  需要扫描类的集合
	 * @param list 扫描到的类
	 */
	__InitTool(List<Class<?>> list) {
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
}
