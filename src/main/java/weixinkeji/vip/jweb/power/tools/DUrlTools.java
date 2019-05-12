package weixinkeji.vip.jweb.power.tools;

import java.util.HashSet;
import java.util.Set;

public class DUrlTools {
	private static char cDKH = "{".toCharArray()[0];
	private static char cDKH2 = "}".toCharArray()[0];
	private static char xg = "/".toCharArray()[0];
	private static Set<char[]> urlExpress=new HashSet<>();
	static {
		urlExpress.add(formatURL("/xxx2/{a1}/bb/ccc/{a2}/{a3}/{a4}/{a5}/{a6}"));
		urlExpress.add(formatURL("/xxx2/{a1}/123"));
	}
	
	public static void main(String args[]) {
		System.out.println(checkURL("/xxx2/参数1/bb/ccc/参数2/参数3/参数4/参数5/12222   2222"));
		System.out.println(checkURL("/xxx2/参数1/bb/ccc/参数2/参数3/参数4/参数5/12222 / 2222"));
		System.out.println(checkURL("/xxx2/参数1"));
		System.out.println(checkURL("/xxx/参数1/6666"));
		System.out.println(checkURL("xxx/参数1/6666"));
		System.out.println(checkURL("/aaxxx/参数1/6666"));
		System.out.println(checkURL("/xxx2/参数1/bb/ccc/参数2/参数3/参数4/参数5"));
		System.out.println(checkURL("/xxx2/参数1/123"));
	}
	public static boolean checkURL(String requestUrl) {
		if(requestUrl.isEmpty()||requestUrl.length()==1) {
			return false;
		}
		char[] url = requestUrl.toCharArray();
		for(char[] model:urlExpress) {
			if(checkOneUrl(model,url)) {
				return true;
			}
		}
		return false;
	}
	private static boolean checkOneUrl(char[] model,char[] url) {
		if(url.length<model.length) {
			return false;
		}
		int urlIndex=0;
		for(int i=0;i<model.length;i++) {
			if(url[urlIndex]==model[i]) {
				urlIndex++;
				continue;
			}
			//开头便不对。
			//这步独立出来。因为下一步有 model[i-1]。没有独立出来，则存在 model[-1]的情况
			if(i==0) {
				return false;
			}
			// 碰上//model
			if(model[i]==xg&&model[i-1]==xg) {
				//System.out.print("    ");
				for(;urlIndex<url.length;urlIndex++) {
					if(url[urlIndex]==xg) {
						break;
					}
					//System.out.print(url[urlIndex]);
//					System.out.print(url[urlIndex]+"-"+urlIndex+" ");
				}
				if(urlIndex==url.length&&i<model.length) {
					return false;
				}
				urlIndex++;
				if(url[urlIndex]==xg) {
					return false;
				}
				//System.out.println("    ");
			}else {
				return false;
			}
		}
		
		//c。请求路径的也刚刚计算完成
		if(urlIndex==url.length) {
			return true;
		}
	//	System.out.println("模型的计算完,urlIndex="+urlIndex);
		//模型的计算完了，但请求路径未计算完
		//模型如果是以/结尾。表示 请求路径最后面的字符可以是任意（当然不能带有/符号）
		if(model[model.length-1]==xg) {
			for(;urlIndex<url.length;urlIndex++) {
				if(url[urlIndex]==xg) {
					//System.err.println(url.length+"//"+url[urlIndex]+"//"+urlIndex);
					return false;
				}
			}
			return true;
		}
		return false;
	}
	public static char[] formatURL(String url) {
		char[] curl = url.toCharArray();
		int startIndex=0;
		StringBuilder sb=new StringBuilder();
		for (char c : curl) {
			if(cDKH==c) {
				startIndex++;
			}else if(cDKH2==c) {
				startIndex--;
				continue;
			}
			if(startIndex==0) {
				sb.append(c);
			}
		}
		System.out.println(sb);
		return sb.toString().toCharArray();
	}
}
