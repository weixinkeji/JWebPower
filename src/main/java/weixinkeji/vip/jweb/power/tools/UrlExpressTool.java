package weixinkeji.vip.jweb.power.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlExpressTool {
	public static void main(String args[]) {
		// 一个*表示一个 小段的路径（不包含/） 如： 路径1
		String regex = "**w/**/s/add.jw***";
		String your = "w/v/s/ad2d.jw";
//		List<char[]> list=formatExpress(regex);
//		char[] yours=your.toCharArray();

		System.out.println(Arrays.deepToString(regex.split("/")));
		char[] s = regex.toCharArray();
		// 0组
		char[] c0 = new char[2];
		c0[0] = '*';
		c0[1] = '*';
		// 1组
		char[] c1 = new char[1];
		c1[0] = '*';

		// 3组 边界符
		char[] c3 = new char[1];
		c3[0] = '/';
		char c;
		char[] myrs;
		List<char[]> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < s.length; i++) {
			c = s[i];
			if (c0[0] == c) {
				myrs = formatExpress(i, s, c0);
				if (null != myrs) {
					recordStr(sb,list);
					i = i + c0.length-1;
					list.add(formatByUrlInfo(urlInfo.xuexiao, null));
					continue;
				}
			}
			if (c1[0] == c) {
				recordStr(sb,list);
				list.add(formatByUrlInfo(urlInfo.bangji, null));
				continue;
			} else if (c3[0] == c) {
				recordStr(sb,list);
				list.add(formatByUrlInfo(urlInfo.bianjie, null));
				continue;
			}
			sb.append(c);
		}
		
		for(char[] cs:list) {
			System.out.println(new String(cs));
		}
	}

	private static void recordStr(StringBuilder sb, List<char[]> contair) {
		if (sb.length() > 0) {
//			char[] c = sb.toString().toCharArray();
			contair.add(formatByUrlInfo(urlInfo.xuesheng, sb.toString()));
			sb.setLength(0);
		}
	}

	public static char[] formatExpress(int sources_startIndex, char[] sources, char[] needFind) {
		StringBuilder sb = new StringBuilder();
		char c;
		for (int i = 0; i < needFind.length; i++) {
			if (sources_startIndex == sources.length) {
				return null;
			}
			if (needFind[i] != (c = sources[sources_startIndex++])) {
				return null;
			}
			sb.append(c);
		}
		return sb.toString().toCharArray();
	}

	public static char[] formatByUrlInfo(urlInfo ui, String s) {

		if (null == ui) {
			return null;
		}
		char[] chars;
		if (null == s || s.length() == 0) {
			chars = new char[1];
			chars[0] = ui.index;
			return chars;
		} else {
			char[] schar = s.toCharArray();
			chars = new char[schar.length + 1];
			int j = 1;
			chars[0] = ui.index;
			for (int i = 0; i < schar.length; i++) {
				chars[j++] = schar[i];
			}
			return chars;
		}
	}
}

enum urlInfo {
//	xuexiao((char)0), bangji((char)1), xuesheng((char)2);
	xuexiao('0'), bangji('1'), xuesheng('2'), bianjie('3');
	public final char index;

	private urlInfo(char index) {
		this.index = index;
	}
}

class urlVo {

}