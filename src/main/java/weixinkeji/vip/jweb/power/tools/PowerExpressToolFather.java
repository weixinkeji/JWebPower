package weixinkeji.vip.jweb.power.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import weixinkeji.vip.jweb.power.vo.JWebPowerExpressVO;

public abstract class PowerExpressToolFather {

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
		byte[] b = strs.getBytes();
		byte[] rs = new byte[b.length - 6];
		int rsindex = 0;
		for (int i = 6; i < b.length; i++) {
			rs[rsindex++] = b[i];
		}
		return new String(rs);
	}

	/**
	 * 从strs，切除“*”前缀|后缀
	 * 
	 * @param strs  本框架定义的表达式
	 * @param index 0表示切除前缀*，1表示切除后缀*
	 * @return String
	 */
	protected String toRemoveStrX(String strs, int index) {
		byte[] b = strs.getBytes();
		byte[] rs = new byte[b.length - 1];
		int rsindex = 0;
		if (index == 0) {
			for (int i = 1; i < b.length; i++) {
				rs[rsindex++] = b[i];
			}
		} else {
			for (int i = 0; i < b.length - 1; i++) {
				rs[rsindex++] = b[i];
			}
		}
		return new String(rs);
	}

	/**
	 * 执行校验
	 * 
	 * @param express 正则表达式
	 * @param srcStr  被检验的字符串
	 * @return boolean
	 */
	protected boolean toRegexStr(String express, String srcStr) {
		// System.out.println("正则表达式:" + express + "//被检验的字符串:" + srcStr);
		// return Pattern.compile(express, 0).matcher(srcStr).matches();
		return getPattern(express).matcher(srcStr).matches();
	}

	/**
	 * 表达式 [[会员等级|权限编号]] 切割符
	 * 
	 * @param express 本框架定义的表达式
	 * @return JWebPowerExpressVO
	 */
	public JWebPowerExpressVO splitExpressStr(String express) {
		JWebPowerExpressVO vo = new JWebPowerExpressVO();
		if (express.contains("[[")) {
			String sstr[] = express.split("\\[\\[");
			vo.setExpress(sstr[0]);
			String str = sstr[1].replace("]]", "").trim();
			vo.setValues(str.isEmpty() ? JWebPowerExpressVO.EMPTY_POWER : str.split("[,，]"));
			System.out.println("splitSessionExpressStr：" + vo.getExpress() + " " + Arrays.deepToString(vo.getValues()));
		} else {
			vo.setExpress(express);
			vo.setValues(JWebPowerExpressVO.EMPTY_POWER);
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