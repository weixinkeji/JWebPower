package weixinkeji.vip.jweb.power.expresstion;

import java.util.Set;

public interface IControllerURLExpresstion {
	
	/**
	 * 设置公共访问的请求路径
	 * <p>
	 * 假设请求的路径是 /jweb/adf/abc.jw
	 * <p>
	 * 凡是.jw后缀的，我们可以设置：set.add("*.jw");
	 * <p>
	 * 凡是/jweb/adf/前缀的，我们可以设置：set.add("/jweb/adf/*");
	 * <p>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add("/jweb/adf/*.jw");
	 * <p>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add("regex:正则表达式"); 注regex:前缀是必需的
	 * <p>
	 * 
	 * 
	 * 【 总结】<br>
	 * 路径选择器的手段1：简单表达式 <br>
	 * 路径选择器的手段2：regex:正则表达式
	 * 
	 * <p>
	 * 
	 * @param set
	 */
	public void setRequestURL_Public(Set<String> set);
	
	/**
	 * 设置已经会话的请求路径
	 * <p>
	 * 假设请求的路径是 /jweb/adf/abc.jw；会员采用等级制。现在等级1,2,3,4,5,6
	 * <p>
	 * 凡是.jw后缀的1、2、3、4级会员，我们可以设置：set.add("*.jw [[1,2,3,4]]");
	 * <p>
	 * 凡是/jweb/adf/前缀的所有等级会员，我们可以设置：set.add("/jweb/adf/*");
	 * <p>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add("/jweb/adf/*.jw");
	 * <p>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add("regex:正则表达式 [[会员等级]] "); 注regex:前缀是必需的
	 * 
	 * <p>
	 *【 总结】<br>
	 * 路径选择器的手段1：简单表达式 [[会员等级]]<br>
	 * 路径选择器的手段2：regex:正则表达式 [[会员等级]]<br>
	 * 其中，会员等级 可以省略不写
	 * 
	 * @param set
	 */
	public void setRequestURL_Session(Set<String> set);
	
	/**
	 * 设置绑定编号的请求路径
	 * <p>
	 * 假设请求的路径是 /jweb/adf/abc.jw；绑定的编号是1,2,3,4,5,6
	 * <p>
	 * 我们可以设置：set.add("*.jw [[1,2,3,4,5,6]]");
	 * <p>
	 * 凡是/jweb/adf/前缀的所有等级会员，我们可以设置：set.add("/jweb/adf/* [[1,2,3,4,5,6]]");
	 * <p>
	 * 凡是/jweb/adf/前缀，.jw后缀的，我们可以设置：set.add("/jweb/adf/*.jw [[1,2,3,4,5,6]]");
	 * <p>
	 * 使用非常复杂的，我们可以使用正则表达式：set.add("regex:正则表达式 [[编号]] "); 注regex:前缀是必需的
	 * 
	 * <p>
	 * 【 总结】<br>
	 * 路径选择器的手段1：简单表达式 [[编号]]<br>
	 * 路径选择器的手段2：regex:正则表达式 [[编号]]<br>
	 * 其中，会员等级 可以省略不写
	 * 
	 * @param set
	 */
	public void setRequestURL_Identifiter(Set<String> set);
	
}
