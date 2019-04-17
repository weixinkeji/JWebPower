package weixinkeji.vip.jweb.power.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weixinkeji.vip.jweb.power.vo.JWebPowerExpressVO;

/**
 * 权限表达式 工具
 * 
 * @author wangchunzi
 *
 */
public class PowerExpressionTool extends PowerExpressToolFather {
	/**
	 * 检验url是否符合放行要求
	 * 
	 * @param requestURL 请求路径
	 * @param expression 表达式
	 * @return boolean 请求路径 是否符合表达式
	 */
	public boolean isPublicPower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return false;
		}
		expression = expression.trim();
		String regexStr;
		// 表示采用正则表达检验
		if (expression.startsWith("regex:")) {
			regexStr = toRemoveStr(expression);
			return toRegexStr(regexStr, requestURL);
		}
		// 表示采用简单表达式 以*开头的检验
		if (expression.startsWith("*")) {
			regexStr = toRemoveStrX(expression, 0);
			return requestURL.endsWith(regexStr);
		}
		// 表示采用简单表达式 以*结尾的检验
		else if (expression.endsWith("*")) {
			regexStr = toRemoveStrX(expression, 1);
			return requestURL.startsWith(regexStr);
		}
		// 表示采用简单表达式 包含1个*
		else if (expression.contains("*")) {
			if (super.countStr(expression, "*".getBytes()[0]) > 1) {
				System.err
						.println("===========注意：如果需要用到复杂表达式，请使用正则表达式。并在表达式前面加上“ regex:”===========，异常字符：" + expression);
				return false;
			}
			String[] regexStr2 = expression.split("[*]{1}");
			return requestURL.startsWith(regexStr2[0]) && requestURL.endsWith(regexStr2[1]);
		} else {
			return requestURL.equals(expression);
		}
	}

	/**
	 * 检验url是否是会员
	 * 
	 * @param requestURL 请求路径
	 * @param expression 表达式 [[会员等级]]
	 * @return boolean 请求路径 是否符合表达式
	 */
	public String[] isSessionPower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return null;
		}
		// 对会员的表达式（表达式 [[会员等级]] ）进行切割 。 分成 匹配表达式，与等级
		JWebPowerExpressVO expressionVO = super.splitSessionExpressStr(expression.trim());
		expression = expressionVO.getExpress();
		// 定义个临时变量，用来装载 处理后的字符串（表达式）
		String regexStr;
		// 表示采用正则表达检验
		if (expression.startsWith("regex:")) {
			// 对表达式的 装饰符 regex: 进行切除
			regexStr = toRemoveStr(expressionVO.getExpress());
			// 执行正则表达式检验
			if (toRegexStr(regexStr, requestURL)) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		}
		// 表示采用简单表达式 以*开头的检验
		if (expression.startsWith("*")) {
			// 对表达式的 装饰符 * 进行切除
			regexStr = toRemoveStrX(expression, 0);
			if (requestURL.endsWith(regexStr)) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		}
		// 表示采用简单表达式 以*结尾的检验
		else if (expression.endsWith("*")) {
			// 对表达式的 装饰符 * 进行切除
			regexStr = toRemoveStrX(expression, 1);
//			System.out.println("对表达式的 装饰符 * 进行切除" + regexStr + "//" + requestURL.startsWith(regexStr));
			if (requestURL.startsWith(regexStr)) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		}
		// 表示采用简单表达式 包含1个*
		else if (expression.contains("*")) {
			if (super.countStr(expression, "*".getBytes()[0]) > 1) {
				System.err
						.println("===========注意：如果需要用到复杂表达式，请使用正则表达式。并在表达式前面加上“ regex:”===========，异常字符：" + expression);
				return null;
			}
			String[] regexStr2 = expression.split("[*]{1}");
			if (requestURL.startsWith(regexStr2[0]) && requestURL.endsWith(regexStr2[1])) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 检验url是否是权限编号
	 * 
	 * @param requestURL 请求路径
	 * @param expression 表达式 [[权限编号]]
	 * @return boolean 请求路径 是否符合表达式
	 */
	public String[] isIdentifiterPower(String expression, String requestURL) {
		if (null == expression || expression.isEmpty()) {// 表达式没有写。直接不符合。返回false
			return null;
		}
		// 对会员的表达式（表达式 [[权限编号]] ）进行切割 。 分成 匹配表达式，与等级
		JWebPowerExpressVO expressionVO = super.splitSessionExpressStr(expression.trim());
		expression = expressionVO.getExpress();
		// 定义个临时变量，用来装载 处理后的字符串（表达式）
		String regexStr;
		// 表示采用正则表达检验
		if (expression.startsWith("regex:")) {
			// 对表达式的 装饰符 regex: 进行切除
			regexStr = toRemoveStr(expressionVO.getExpress());
			// 执行正则表达式检验
			if (toRegexStr(regexStr, requestURL)) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		}
		// 表示采用简单表达式 以*开头的检验
		if (expression.startsWith("*")) {
			// 对表达式的 装饰符 * 进行切除
			regexStr = toRemoveStrX(expression, 0);
			if (requestURL.endsWith(regexStr)) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		}
		// 表示采用简单表达式 以*结尾的检验
		else if (expression.endsWith("*")) {
			// 对表达式的 装饰符 * 进行切除
			regexStr = toRemoveStrX(expression, 1);
//			System.out.println("对表达式的 装饰符 * 进行切除" + regexStr + "//" + requestURL.startsWith(regexStr));
			if (requestURL.startsWith(regexStr)) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		}
		// 表示采用简单表达式 包含1个*
		else if (expression.contains("*")) {
			if (super.countStr(expression, "*".getBytes()[0]) > 1) {
				System.err
						.println("===========注意：如果需要用到复杂表达式，请使用正则表达式。并在表达式前面加上“ regex:”===========，异常字符：" + expression);
				return null;
			}
			String[] regexStr2 = expression.split("[*]{1}");
			if (requestURL.startsWith(regexStr2[0]) && requestURL.endsWith(regexStr2[1])) {
				// 通过检验，返回会员等级
				return expressionVO.getValues();
			}
			return null;
		} else {
			return null;
		}
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
