package weixinkeji.vip.jweb.power.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlExpressTool {

	public static void main(String args[]) {
		// 一个*表示一个 小段的路径（不包含/） 如： 路径1
		// 连续二个* 表示任意字符的路径 如： /路径1/路径2/路径3......

		String regex = "a**weixinkeji/**/*/add.jw";
		String your = "weixinkeji/vip/subject/add.jw";
		String[] xuexiao = regex.split("\\*\\*");
		String[] bangjixuesheng;
		List<char[]> list=new ArrayList<>();
		
		for(int i=0;i<(xuexiao=regex.split("[*]{2}")).length;i++) {
			if(xuexiao[i].isEmpty()) {
				list.add(UrlExpressTool.formatByUrlInfo(urlInfo.xuexiao,null));
				continue;
			}
			for(int j=0;j<(bangjixuesheng=xuexiao[i].split("[*]{1}")).length;j++) {
				if(bangjixuesheng[j].isEmpty()) {
					list.add(UrlExpressTool.formatByUrlInfo(urlInfo.bangji,null));
					continue;
				}
				list.add(UrlExpressTool.formatByUrlInfo(urlInfo.xuesheng, bangjixuesheng[j]));
				if(j<bangjixuesheng.length-1) {
					list.add(UrlExpressTool.formatByUrlInfo(urlInfo.bangji,null));
				}
			}
			if(i<xuexiao.length-1) {
				list.add(UrlExpressTool.formatByUrlInfo(urlInfo.xuexiao,null));
			}
		}
		
		for(char[] cstr:list) {
			System.out.println("  "+(null!=cstr?new String(cstr):null));
		}
		
	}

	public static char[] formatByUrlInfo(urlInfo ui, String s) {
		
		if (null == ui) {
			return null;
		}
		char[] chars;
		if (null==s||s.length() == 0) {
			chars = new char[1];
			chars[0] = ui.index;
			return chars;
		} else {
			char[] schar = s.toCharArray();
			chars = new char[schar.length + 1];
			int j = 1;
			chars[0]=ui.index;
			for (int i = 0; i < schar.length; i++) {
				chars[j++] = schar[i];
			}
			return chars;
		}
	}
}

enum urlInfo {
	xuexiao('0'), bangji('1'), xuesheng('2');
	public final char index;

	private urlInfo(char index) {
		this.index = index;
	}
}

class urlVo {

}