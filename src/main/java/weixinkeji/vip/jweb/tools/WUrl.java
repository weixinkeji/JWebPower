package weixinkeji.vip.jweb.tools;

import java.util.ArrayList;
import java.util.List;

public class WUrl {
	private final static char[] c0 = new char[2];
	private final static char[] c1 = new char[1];
	private final static char[] c2 = new char[1];
	static {
		//0组 =1+2
		c0[0] = '*';
		c0[1] = '*';
		//1组-模糊
		c1[0] = '*';
		//2组-边界
		c2[0] = '/';
	}
	
	public static void main(String args[]) {
		String regex = "**w/**/s/add.jw**";
		String your = "w/v/s/ad2d.jw";

		for (char[] cs : formatUrlByWUrl(regex)) {
			System.out.println(new String(cs));
		}
		//检验规则：
		//一、遇0组
		//	1.1、检验组已经是最后一组。检验成功。【结束】
		//  1.2、直接检出下一组,并拿出第2个字符，比较用户剩余的字符。直到 用户的字符=检验组的第2个字符为止
		//		1.2.1、如果一直找不到。检验失败。【结束】
		//		1.2.2、如果找到。继续检验字符【继续】 
		//遇1组，
		
		
	}
	public static boolean checkUrl(String regex,List<char[]> list) {
		char[] yoursChar=regex.toCharArray();
		int yourIndex=0;
		int statusCode=-1;
		
		for(char[] sysc:list) {
			//如果是0组
			if(sysc[0]==WUrlInfo.all.index) {
				statusCode=sysc[0];
				continue;
			}
			//上次是0组，本次是3组（内容组），把用户组的下标，移到与sysc[1]同样的地方
			if(statusCode==0&&sysc[0]==3) {
				//移动用户组的下标。
				for(;yourIndex<yoursChar.length;yourIndex++) {
					if(sysc[1]==yoursChar[yourIndex]) {
						break;
					}
				}
				//开始与用户组的内容，一一对应
				for(;yourIndex<yoursChar.length;yourIndex++) {
					
					if(sysc[1]==yoursChar[yourIndex]) {
						break;
					}
					
				}
				
				continue;//继续下一次的循环
			}
			
		}
		return true;
	}
	
	//格式化字符串
	private static List<char[]> formatUrlByWUrl(String regex) {
		char[] s = regex.toCharArray();
		char c;
		char[] myrs;
		List<char[]> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < s.length; i++) {
			c = s[i];
			if (c0[0] == c) {
				myrs = findChar(i, s, c0);
				if (null != myrs) {
					recordStr(sb, list);
					i = i + c0.length - 1;
					list.add(addIndexToChar(WUrlInfo.all, c0));
					continue;
				}
			}
			if (c1[0] == c) {
				recordStr(sb, list);
				list.add(addIndexToChar(WUrlInfo.area, c1));
				continue;
			} else if (c2[0] == c) {
				recordStr(sb, list);
				list.add(addIndexToChar(WUrlInfo.splitLine, c2));
				continue;
			}
			sb.append(c);
		}
		return list;
	}
	
	private static void recordStr(StringBuilder sb, List<char[]> contair) {
		if (sb.length() > 0) {
			contair.add(addIndexToChar(WUrlInfo.content, sb.toString().toCharArray()));
			sb.setLength(0);
		}
	}
	
	//找到指定的字符，并返回。找不到，返回null
	private static char[] findChar(int sources_startIndex, char[] sources, char[] needFind) {
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
	
	//以 索引+内容方式，组装一起
	private static char[] addIndexToChar(WUrlInfo ui, char[] schar) {
		if (null == ui) {
			return null;
		}
		char[] chars;
		if (null == schar || schar.length== 0) {
			chars = new char[1];
			chars[0] = ui.index;
			return chars;
		} else {
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

enum WUrlInfo {
	all('0'), area('1'), splitLine('2'), content('3');
	public final char index;
	
	private WUrlInfo(char index) {
		this.index = index;
	}
}