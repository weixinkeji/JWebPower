package weixinkeji.vip.jweb.power.tools;

import java.util.HashSet;
import java.util.Set;

public class JWPTool {

	/**
	 * 合并多个数组
	 * <p>
	 * 示例：
	 * <p>
	 * null +null =null
	 * <p>
	 * null + []  或 []+null =[]
	 * <p>
	 * []+[]=[]
	 * <p>
	 * ["a"]+["b"]=["a","b"]
	 * 
	 * @param strs 字符数组
	 * @return String[]
	 */
	public static String[] mergeStringArray(String[]... strs) {
		if (null == strs)
			return null;
		Set<String> set = new HashSet<>();
		int notNullCount = 0;//非空数组  计数器

		for (String[] ss : strs) {
			if (null == ss) {
				continue;
			}
			notNullCount++;
			for (String s : ss) {
				set.add(s);
			}
		}
		if (notNullCount == 0) {
			return null;
		}
		String[] s = new String[set.size()];
		set.toArray(s);
		return s;
	}
	

	/**
	 * 合并布尔值
	 * 只要存在true,立马返回true。其他 情况，都返回false
	 * 
	 * @param bs 布 尔值 数组
	 * @return boolean
	 */
	public static boolean mergeStringArray(Boolean... bs) {
		if (null == bs)
			return false;
		for (Boolean ss : bs) {
			if (null != ss&&ss) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查他们的关系 是否是父子关系
	 * @param father 父亲类
	 * @param son  儿子类
	 * @return boolean true:父子关系，false：非父子关系
	 */
	public static boolean isFatherSon(Class<?> father,Class<?> son) {
		return father.isAssignableFrom(son) && !son.equals(father);
	}
	
	/**
	 * 把 {"a,a1","b"} 变成 {"a","a1","b"}
	 * 
	 * @param powers String[] 权限数据
	 * @return String[]
	 */
	public static String[] formatMyArray(String[] powers) {
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
	 * 把 {"a,a1","b"} 变成 {"a","a1","b"}
	 * 
	 * @param powers String[] 权限数据
	 * @param prefix 每个权限的前缀
	 * @param suffix 每个权限的后缀
	 * @return  String[]
	 */
	public static String[] formatMyArray(String[] powers,String prefix,String suffix) {
		Set<String> set = new HashSet<>();
		String[] sonPower;
		for (String p : powers) {
			if (p.contains(",")) {
				sonPower = p.split(",");
				for (String son : sonPower) {
					set.add(prefix+son+suffix);
				}
			} else {
				set.add(prefix+p+suffix);
			}
		}
		String[] mypower = new String[set.size()];
		return set.toArray(mypower);
	}
}
