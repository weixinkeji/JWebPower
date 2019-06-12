package weixinkeji.vip.jweb.power.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weixinkeji.vip.jweb.power.vo.JWPExpressVO;

/**
 * 权限表达式 工具
 * 
 * @author wangchunzi
 *
 */
public class JWPExpressionTool extends JWPExpressToolFather {
	private final static String SIMPLE_REGEX_ALL = "[./a-zA-Z0-9_-}{?=&]";
	private final static String SIMPLE_REGEX_AREA = "[.}{a-zA-Z0-9_-?=&]";
	public final static String REGEX_SIMPLE="simple:";
	public final static String REGEX_COMPLETE="regex:";
	
	/**
	 * 把简化正则表达式，变成正真的正则表达式
	 * 
	 * @param yourExpress 你的简化正则表达式
	 * @return String
	 */
	public static String formatSimpleRegexExpression(String yourExpress) {
		return yourExpress.replace("**", SIMPLE_REGEX_ALL).replace("*", SIMPLE_REGEX_AREA);
	}
	
	/**
	 * 找到集合中的简化正则表达式，变成正真的正则表达式
	 * 
	 * @param yourExpress 你的达式
	 * @return Set
	 */
	public static Set<String> formatSimpleRegexExpression(Set<String> yourExpress) {
		if(null==yourExpress) {
			return null;
		}
		if(yourExpress.size()==0) {
			return yourExpress;
		}
		Set<String> myFormatExpression=new HashSet<>();
		for(String str:yourExpress) {
			myFormatExpression.add(str.startsWith(REGEX_SIMPLE)?formatSimpleRegexExpression(str):str);
		}
		return myFormatExpression;
	}
	
	
	/**
	 * 检查 用户写的是表达式(表达式返回null)，还是直接路径（直接路径返回JWebPowerExpressVO)
	 * 
	 * @param expression 用户配置的字符串（可能是表达式，亦可能是 直接路径）
	 * @return JWebPowerExpressVO
	 */
	public JWPExpressVO getPowerUrl(String expression) {
		JWPExpressVO vo = null;
		if (expression.contains("[[")) {// 如果字符中包含[[，表达有权限等级或权限编号 权限控制。分割 路径 与其权限
			vo = super.splitExpressStr(expression);
			// 判断表达式是否包含指定*或regex:,没有，则表示 此路径是完整的路径。可以直接加入权限模型
			if (!vo.getExpress().startsWith(REGEX_SIMPLE) && !vo.getExpress().startsWith(REGEX_COMPLETE)) {
				return vo;
			} else {// 包含了指定的字符，表示不是完整路径
				return null;
			}
		}
		
		if (!expression.startsWith(REGEX_SIMPLE) && !expression.startsWith(REGEX_COMPLETE)) {
			vo = new JWPExpressVO();
			vo.setExpress(expression);
			return vo;
		}

		return null;
	}

	/**
	 * 检验url是否符合放行要求
	 * 
	 * @param requestURL 请求路径
	 * @param expression 表达式
	 * @return boolean 请求路径 是否符合表达式
	 */
	public boolean isCommonPower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return false;
		}
		expression = expression.trim();
		// 表示采用正则表达检验
		if (expression.startsWith(REGEX_COMPLETE)) {
			return toRegexStr(toRemoveStr(expression), requestURL);
		}
		// 表示采用简化正则表达式
		if (expression.startsWith(REGEX_SIMPLE)) {
			return requestURL.matches(toRemoveStr(expression));
		}
		// 表示 完整路径，直接比较（无视大小写字符）
		return requestURL.equalsIgnoreCase(expression);
	}

	/**
	 * 检验url是否符合会话要求
	 * 
	 * @param requestURL 请求路径
	 * @param expression 表达式
	 * @return boolean 请求路径 是否符合表达式
	 */
	public boolean isSessionPower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return false;
		}
		expression = expression.trim();
		// 表示采用正则表达检验
		if (expression.startsWith(REGEX_COMPLETE)) {
			return toRegexStr(toRemoveStr(expression), requestURL);
		}
		// 表示采用简化正则表达式
		if (expression.startsWith(REGEX_SIMPLE)) {
			return requestURL.matches(toRemoveStr(expression));
		}
		return requestURL.equalsIgnoreCase(expression);
	}

	/**
	 * 检验url是否是权限等级
	 * 
	 * @param expression 表达式 [[权限等级]]
	 * @param requestURL 请求路径
	 * 
	 * @return boolean 请求路径 是否符合表达式
	 */
	public String[] isGradePower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return null;
		}
		// 对权限的表达式（表达式 [[权限等级]] ）进行切割 。 分成 匹配表达式，与等级
		JWPExpressVO expressionVO = super.splitExpressStr(expression.trim());
		expression = expressionVO.getExpress();

		// 表示采用正则表达检验
		if (expression.startsWith(REGEX_COMPLETE)) {
			// 对表达式的 装饰符 regex: 进行切除
			// 通过检验，返回权限等级
			return toRegexStr(toRemoveStr(expression), requestURL) ? expressionVO.getValues() : null;
		}
		// 表示采用简化正则表达式
		if (expression.startsWith(REGEX_SIMPLE)) {
			// 通过检验，返回权限等级
			return requestURL.matches(toRemoveStr(expression)) ? expressionVO.getValues() : null;
		}
		// 通过检验，返回权限等级
		return requestURL.matches(expression) ? expressionVO.getValues() : null;
	}

	/**
	 * 检验url是否是权限编号
	 * 
	 * @param expression 表达式 [[权限编号]]
	 * @param requestURL 请求路径
	 * @return boolean 请求路径 是否符合表达式
	 */
	public String[] isCodePower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return null;
		}
		// 对权限的表达式（表达式 [[权限编号]] ）进行切割 。 分成 匹配表达式，与等级
		JWPExpressVO expressionVO = super.splitExpressStr(expression.trim());
		expression = expressionVO.getExpress();
		// 表示采用正则表达检验
		if (expression.startsWith(REGEX_COMPLETE)) {
			// 对表达式的 装饰符 regex: 进行切除
			// 通过检验，返回权限编号
			return toRegexStr(toRemoveStr(expression), requestURL) ? expressionVO.getValues() : null;
		}
		// 表示采用简化正则表达式
		if (expression.startsWith(REGEX_SIMPLE)) {
			// 通过检验，返回权限编号
			return requestURL.matches(toRemoveStr(expression)) ? expressionVO.getValues() : null;
		}
		// 通过检验，返回权限编号
		return requestURL.matches(expression) ? expressionVO.getValues() : null;
	}
	
	/**
	 * 用户的路径（url）与 监听器上的路径（listenRegexExpress）是否匹配。
	 * @param url 用户的路径 
	 * @param listenRegexExpress 合监听器上的路径表达式
	 * @return boolean 用户的路径 是否符合监听器上的路径表达式
	 */
	public boolean isUrlListen(String url,String listenRegexExpress) {
		if (null == url || url.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return false;
		}
		url = url.trim();
		String regexStr;
		// listenRegexExpress 表示采用正则表达检验
		if (listenRegexExpress.startsWith(REGEX_COMPLETE)) {
			regexStr = toRemoveStr(listenRegexExpress);
			return toRegexStr(regexStr, url);
		}
		// 表示采用简化正则表达式
		if (listenRegexExpress.startsWith(REGEX_SIMPLE)) {
			regexStr = toRemoveStr(listenRegexExpress);
			return url.matches(regexStr);
		}
		// 表示 完整路径，直接比较（无视大小写字符）
		return url.equalsIgnoreCase(listenRegexExpress);
	}
	
	public boolean isRegexExpression(String url) {
		return url.startsWith(REGEX_COMPLETE);
	}
	public boolean isSimpleRegexExpression(String url) {
		// 表示采用简化正则表达式
		return url.startsWith(REGEX_SIMPLE);
	}
	
	
	public String[] mergeArrayList(final List<String[]> lists) {
		Set<String> rs = new HashSet<>();
		for (String[] codes : lists) {
			for (String co : codes) {
				rs.add(co);
			}
		}
		return rs.toArray(new String[rs.size()]);
	}

}
