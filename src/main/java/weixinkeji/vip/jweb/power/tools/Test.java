package weixinkeji.vip.jweb.power.tools;

public class Test {

	public static void main(String[] args) {
		testRegx3();
	}

	public static void testRegx() {
		PowerExpressionTool obj = new PowerExpressionTool();
		String regex = "regex:^([a-z/]{1,100}Add.jw)$";
		String url = "/abc/power/userAdd.jw";
		boolean rs = obj.isPublicPower(regex, url);
		System.out.println(rs);
		obj.closeResources();
	}

	public static void testRegx2() {
		PowerExpressionTool obj = new PowerExpressionTool();
		String regex = "/abc/power/userAdd.jw";
//		String regex = "/abc/power/*";
//		String regex = "/abc/*/userAdd.jw";

		String url = "/abc/power/userAdd.jw";
		boolean rs = obj.isPublicPower(regex, url);
		System.out.println(rs);
		obj.closeResources();
	}

	// 会员等级
	public static void testRegx3() {
		PowerExpressionTool obj = new PowerExpressionTool();
		String regex = "regex:^([a-z/]{1,100}Add.jw)$ [[ 1 ,23,3]]";
//		String regex = "/abc/*/userAdd.jw [[ 1 ,23,3]]";//
		String url = "/abc/po/wer/userAdd.jw";
		String[] rs = obj.isSessionPower(regex, url);
		if (null != rs) {
			if (rs.length == 0) {
				System.out.println("检验成功！所有登陆会员都可以访问此路径");
			} else {
				for (String str : rs) {
					System.out.println("会员等级：" + str);
				}
			}

		} else {
			System.out.println("检验失败！");
		}
		obj.closeResources();
	}
}
