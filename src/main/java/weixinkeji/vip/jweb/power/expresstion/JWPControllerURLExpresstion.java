package weixinkeji.vip.jweb.power.expresstion;

import java.util.Set;

/**
 * 三种表达式写法
 * 
 * <br>
 * 1。直接写完整的路径 （这种情况，会直接比较路径）
 * <br>
 * 2。简化正则表达式写法。
 * <br>   **表示[点、斜杆、下划线、数字、大写小字母] （即会被替换成 [./a-zA-Z0-9_-}{?]）  
 * <br>   * 表达 26大小字母与0-9数字和下划线和一横杠（即会被替换成  [a-zA-Z0-9_-}{?]）  
 * 
 * <br>
 * 3。完整的正则表达式写法
 * <br>
 * 
 * @author wangchunzi
 *
 */
public interface JWPControllerURLExpresstion {

	/**
	 * 设置公共访问的请求路径 <br>
	 * <br>
	 * 假设请求的路径是 /jweb/adf/abc.jw <br>
	 * <br>
	 * 凡是.jw后缀的，我们可以设置：set.add(":**+.jw"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀的，我们可以设置：set.add(":/jweb/adf/**+"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add(":/jweb/adf/**+.jw"); <br>
	 * <p>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add("regex:正则表达式"); 注regex:前缀是必需的 <br>
	 * </p>
	 * 
	 * 
	 * 【 总结】<br>
	 * 路径选择器的手段1：直接写完整的路径 <br>
	 * 路径选择器的手段2：             :简单表达式<br>
	 * 路径选择器的手段3：regex:正则表达式
	 * <br>
	 * 
	 * @param set 存放表达式字符的Set集合
	 */
	public void setRequestURL_common(Set<String> set);

	/**
	 * 设置会话区请求路径 <br>
	 * <br>
	 * 假设请求的路径是 /jweb/adf/abc.jw <br>
	 * <br>
	 * 凡是.jw后缀的，我们可以设置：set.add(":**+.jw"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀的，我们可以设置：set.add(":/jweb/adf/**+"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add(":/jweb/adf/**+.jw"); <br>
	 * <p>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add("regex:正则表达式"); 注regex:前缀是必需的 <br>
	 * </p>
	 * 
	 * 【 总结】<br>
	 * 路径选择器的手段1：直接写完整的路径 <br>
	 * 路径选择器的手段2：             :简单表达式 <br>
	 * 路径选择器的手段3：regex:正则表达式
	 * 
	 * <br>
	 * 
	 * @param set 存放表达式字符的Set集合
	 */
	public void setRequestURL_session(Set<String> set);
	
	/**
	 * 绑定路径与权限等级 <br>
	 * <br>
	 * 假设请求的路径是 /jweb/adf/abc.jw；采用等级制。现在等级1,2,3,4,5,6 <br>
	 * 
	 * <br>
	 * 拥有1、2、3、4 等级权限的用户，都可以访问.jw后缀的路径 ，我们可以设置：set.add(":**+.jw [[1,2,3,4]]"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀的所有等级，我们可以设置：set.add(":/jweb/adf/**+"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add(":/jweb/adf/**+.jw"); <br>
	 * <br>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add("regex:正则表达式 [[会员等级]] "); 注regex:前缀是必需的
	 * 
	 * <p>
	 * 【 总结】<br>
	 * 路径选择器的手段1：直接写完整的路径  [[会员等级]] <br>
	 * 路径选择器的手段2：             :简单表达式  [[会员等级]] <br>
	 * 路径选择器的手段3：regex:正则表达式  [[会员等级]]<br>
	 * 
	 * 注：其中，权限等级 可以省略不写。不写，表示任意的等级
	 * </p>
	 * @param set 存放表达式字符的Set集合
	 */
	public void setRequestURL_grades(Set<String> set);

	/**
	 * 绑定路径与权限编号 <br>
	 * <br>
	 * 假设请求的路径是 /jweb/adf/abc.jw；绑定的编号是1,2,3,4,5,6 <br>
	 * <br>
	 * 我们可以设置：set.add(":**+.jw [[1,2,3,4,5,6]]"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀的，我们可以设置：set.add(":/jweb/adf/**+ [[1,2,3,4,5,6]]"); <br>
	 * <br>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add(":/jweb/adf/**+.jw [[1,2,3,4,5,6]]"); <br>
	 * <br>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add(":regex:正则表达式 [[编号]] "); 注regex:前缀是必需的 <br>
	 * <br>
	 * 【 总结】<br>
	 * 路径选择器的手段1：直接写完整的路径 [[编号]] <br>
	 * 路径选择器的手段2：             :简单表达式 [[编号]]  <br>
	 * 路径选择器的手段3：regex:正则表达式 [[编号]]<br>
	 * 其中，编号是必不可少的
	 *  
	 * @param set 存放表达式字符的Set集合
	 */
	public void setRequestURL_code(Set<String> set);

}
