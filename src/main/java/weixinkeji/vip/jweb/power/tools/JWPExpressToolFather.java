package weixinkeji.vip.jweb.power.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import weixinkeji.vip.jweb.power.vo.JWPExpressVO;

public abstract class JWPExpressToolFather {

	/**
	 * 用完后，一定要给它设置值为null.因为我把它定位为临时用途。不会再在其他用途用。
	 */
	private Map<String, Pattern> tempMap = new HashMap<>();

	/**
	 * 从strs，切除“regex:”前缀
	 * 
	 * @param strs 本框架定义的表达式
	 * @return String
	 */
	protected String toRemoveStr(String strs) {
		int split = strs.indexOf(":");
		if (split == -1) {
			return strs;
		}
		return strs.substring(split + 1);
	}

	/**
	 * 执行校验
	 * 
	 * @param express 正则表达式
	 * @param srcStr  被检验的字符串
	 * @return boolean
	 */
	protected boolean toRegexStr(String express, String srcStr) {
		return getPattern(express).matcher(srcStr).matches();
	}

	/**
	 * 表达式 [[权限等级|权限编号]] 切割符
	 * 
	 * @param express 本框架定义的表达式
	 * @return JWebPowerExpressVO
	 */
	public JWPExpressVO splitExpressStr(String express) {
		JWPExpressVO vo = new JWPExpressVO();
		if (express.contains("[[")) {
			String sstr[] = express.split("\\[\\[");
			vo.setExpress(sstr[0]);
			String str = sstr[1].replace("]]", "").trim();
			vo.setValues(str.isEmpty() ? JWPExpressVO.EMPTY_POWER : str.split("[,，]"));
		} else {
			vo.setExpress(express);
			vo.setValues(JWPExpressVO.EMPTY_POWER);
		}
		return vo;
	}

	/**
	 * 只能针单个字节 进行处理
	 * 
	 * @param srcStr  字符串
	 * @param findStr 被查找的字符串
	 * @return int
	 */
	protected int countStr(String srcStr, byte findStr) {
		int i = 0;
		for (byte b : srcStr.getBytes()) {
			if (findStr == b) {
				i++;
			}
		}
		return i;
	}

	private Pattern getPattern(String key) {
		Pattern v = tempMap.get(key);
		if (null == v) {
			v = Pattern.compile(key, 0);
			tempMap.put(key, v);
		}
		return v;
	}

	/**
	 * 析放内存
	 */
	public void closeResources() {
		this.tempMap = null;
	}
}