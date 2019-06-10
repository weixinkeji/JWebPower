package weixinkeji.vip.jweb.tools;

public class SimpleRegex {
	public static void main(String[] args) {
//		*  =>[a-zA-Z]+
//		** =>[a-zA-Z./]+
		String regex = "*{1}w/**+/s/add.jw";
		String regex2 =formatToRgexExpress(regex);
		String your = "aw/v/s/add.jw";
		
		System.out.println(your.matches(regex2));
		
		
		
	}
	
	
	public static String formatToRgexExpress(String simpleRegex) {
		return "^("+simpleRegex
		.replace("**",regexMap.all.regex)
		.replace("*",regexMap.area.regex)+")$";
	}
}

enum regexMap{
	  all("[./a-zA-Z_-]")
	,area("[a-zA-Z_-]")
	;
	
	public final String regex;
	
	private regexMap(String str) {
		this.regex=str;
	}
}
