package weixinkeji.vip.jweb.tools;

public class AF {
	
	public static void main(String[] args) {
//		*  =>[a-zA-Z]+
//		** =>[a-zA-Z./]+
		String regex = "*{1}w/**{0,30}/s/add.jw**{0,30}";
		
		String regex2 = "^([a-zA-Z]{1}w/[a-zA-Z]{0,30}/s/add.jw[a-zA-Z]{0,30})$";
		
		
		String your = "ww/v/s/add.jw";
		
		
		System.out.println(your.matches(regex2));
		
		
		
	}

}
