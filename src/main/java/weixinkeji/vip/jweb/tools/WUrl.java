//package weixinkeji.vip.jweb.tools;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WUrl {
//	private final static char[] c0 = new char[2];
//	private final static char[] c1 = new char[1];
//	private final static char[] c2 = new char[1];
//	
//	private final static char E0='0';
//	private final static char E1='1';
//	private final static char E2='2';
//	private final static char E3='3';
//	
//	
//	static {
//		//0组 =1+2
//		c0[0] = '*';
//		c0[1] = '*';
//		//1组-模糊
//		c1[0] = '*';
//		//2组-边界
//		c2[0] = '/';
//	}
//	
//	public static void main(String args[]) {
//		String regex = "*w/**/s/add.jw**";
//		String your = "ww/v/s/add.jw";
//
////		for (char[] cs : formatUrlByWUrl(regex)) {
////			System.out.println(new String(cs));
////		}
//		System.out.println(checkUrl(your, formatUrlByWUrl(regex)));
//		//检验规则：
//		//一、遇0组
//		//	1.1、检验组已经是最后一组。检验成功。【结束】
//		//  1.2、直接检出下一组,并拿出第2个字符，比较用户剩余的字符。直到 用户的字符=检验组的第2个字符为止
//		//		1.2.1、如果一直找不到。检验失败。【结束】
//		//		1.2.2、如果找到。继续检验字符【继续】 
//		//遇1组，
//		
//	}
//	public static boolean checkUrl(String regex,List<char[]> list) {
//		char[] yoursChar=regex.toCharArray();
//		int yourIndex=0;
//		int statusCode=-1;
//		
//		for(char[] sysc:list) {
//			System.out.println("执行"+new String(sysc)+"检验：");
//			switch(sysc[0]) {
//				case E0://任意
//					statusCode=E0;
//					System.out.println(" "+"0组，取下一组");
//					continue;
//				case E1://区域
//					//如果上一组是0组，合并。表示 0组。直接下一次
//					if(statusCode==E0) {
//						System.out.println(" "+"1组，合并上组0组。并取下一组");
//						continue;
//					}
//					//遇中止符结束
//					//移动用户组的下标。如果移到尽头，都找不到与c2[0]边界符配置的字符，直接返回false
//					if(-1==(yourIndex=mv_0_3(c2[0],yourIndex,yoursChar))){
//						System.out.println(" "+"1组，匹配不成功！");
//						return false;
//					}
//					System.out.println(" "+"1组，把用户下标移动到边界符。继续下一组");
//					continue;
//				case E2://分界符
//					//如果上一组是0组，合并。表示 0组。直接下一次
//					if(statusCode==E0) {
//						System.out.println(" "+"2组，合并上组0组。继续下一组");
//						continue;
//					}
//					statusCode=E2;
//					//比较 你的字符中，没有边界符。返回false
//					if(yoursChar[yourIndex++]!=E2) {
//						System.out.println(" "+"2组，边界符校对失败！"+yoursChar[yourIndex++]+"//"+yourIndex);
//						return false;
//					}
//				case E3://内容
//					//上次是0组，本次是3组（内容组），把用户组的下标，移到与sysc[1]同样的地方
//					if(statusCode==E0) {
//						//移动用户组的下标。如果移到尽头，都找不到与sysc[1]配置的字符，直接返回false
//						if(-1==(yourIndex=mv_0_3(sysc[1],yourIndex,yoursChar))){
//							System.out.println(" 移动用户组的下标"+"至跟 3组"+sysc[1]+"同字符时失败。");
//							return false;
//						}
//						if(-1==(yourIndex=eq33(1,sysc,yourIndex,yoursChar))) {
//							System.out.println(" E3区 用户组匹配"+"3组"+sysc[1]+"失败。");
//							return false;//检验不通过。
//						}
//						continue;//继续下一次的循环
//					}
//			}
//		}
//		return true;
//	}
//	
//	/**
//	 * 检验 用户的数据resources   是否包含expresstion
//	 * @param index0	  从哪开始 （expresstion的下标）
//	 * @param expresstion 表达式组
//	 * @param index3	  从哪开始 （resources的下标）
//	 * @param resources  用户的数据
//	 * @return int
//	 */
//	private static int eq33(int index0 ,char[] expresstion,int index3,char[] resources) {
//		System.out.println("-----------表达式"+new String(expresstion)+"/"+index0+"    用户组"+new String(resources)+"/"+index3);
//		for(;index3<resources.length;index3++) {
////			if(index0==expresstion.length) {//1组的检验完后了，2组的还有。明显不匹配。返回-1
////				return -1;
////			}
//			//两组字符存在不一样的字符
//			if(resources[index3]!=expresstion[index0++]) {
//				return -1;
//			}
//			//resources 包含了 expresstion。
//			if(index0==expresstion.length) {
//				return index3;
//			}
//			
//		}
//		//返回第2组，移动的下标
//		if(index0==expresstion.length) {
//			return  index3;
//		}else {
//			//第2组的检验完后，第1组的还有数据。明显不匹配。
//			return -1;
//		}
//	}
//	
//	
//	
//	/**
//	 * 从startIndex开始,移动resources数组下标.直到下标关联的字符等于endChar，然后返回移动后的下标。
//	 * 如果一直没有找到endChar字符，直接-1;
//	 * @param endChar
//	 * @param startIndex
//	 * @param resources
//	 * @return int
//	 */
//	private static int mv_0_3(char endChar,int startIndex,char[] resources) {
//		for(;startIndex<resources.length;startIndex++) {
//			if(resources[startIndex]==endChar) {
//				return startIndex;
//			}
//		}
//		return -1;
//	}
//	
//	//格式化字符串
//	private static List<char[]> formatUrlByWUrl(String regex) {
//		char[] s = regex.toCharArray();
//		char c;
//		char[] myrs;
//		List<char[]> list = new ArrayList<>();
//		StringBuilder sb = new StringBuilder();
//
//		for (int i = 0; i < s.length; i++) {
//			c = s[i];
//			if (c0[0] == c) {
//				myrs = findChar(i, s, c0);
//				if (null != myrs) {
//					recordStr(sb, list);
//					i = i + c0.length - 1;
//					list.add(addIndexToChar(E0, c0));
//					continue;
//				}
//			}
//			if (c1[0] == c) {
//				recordStr(sb, list);
//				list.add(addIndexToChar(E1, c1));
//				continue;
//			} else if (c2[0] == c) {
//				recordStr(sb, list);
//				list.add(addIndexToChar(E2, c2));
//				continue;
//			}
//			sb.append(c);
//		}
//		return list;
//	}
//	
//	private static void recordStr(StringBuilder sb, List<char[]> contair) {
//		if (sb.length() > 0) {
//			contair.add(addIndexToChar(E3, sb.toString().toCharArray()));
//			sb.setLength(0);
//		}
//	}
//	
//	//找到指定的字符，并返回。找不到，返回null
//	private static char[] findChar(int sources_startIndex, char[] sources, char[] needFind) {
//		StringBuilder sb = new StringBuilder();
//		char c;
//		for (int i = 0; i < needFind.length; i++) {
//			if (sources_startIndex == sources.length) {
//				return null;
//			}
//			if (needFind[i] != (c = sources[sources_startIndex++])) {
//				return null;
//			}
//			sb.append(c);
//		}
//		return sb.toString().toCharArray();
//	}
//	
//	//以 索引+内容方式，组装一起
//	private static char[] addIndexToChar(char index, char[] schar) {
//		char[] chars;
//		if (null == schar || schar.length== 0) {
//			chars = new char[1];
//			chars[0] =index;
//			return chars;
//		} else {
//			chars = new char[schar.length + 1];
//			int j = 1;
//			chars[0] =index;
//			for (int i = 0; i < schar.length; i++) {
//				chars[j++] = schar[i];
//			}
//			return chars;
//		}
//	}
//}
//
//enum WUrlInfo {
//	all('0'), area('1'), splitLine('2'), content('3');
//	public final char index;
//	
//	private WUrlInfo(char index) {
//		this.index = index;
//	}
//}