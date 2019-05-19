package weixinkeji.vip.jweb.power.model;

import java.util.HashSet;
import java.util.Set;

import weixinkeji.vip.jweb.power.tools.DUrlTools;

public abstract class DUrlPools {
	protected static Set<char[]> urlExpress=new HashSet<>();
	
//	public static void addDUrl(char[] url) {
//		urlExpress.add(url);
//	}
	public static void addDUrl(String url) {
		urlExpress.add(DUrlTools.formatURL(url));
	}
}
