package weixinkeji.vip.jweb.power.tools;

public class JWPPathTool {

	/**
	 * 把headUrl和methodUrl联合一起
	 * 
	 * @param headUrl   前段路径
	 * @param methodUrl 后段路径
	 * @return String 完全的路径
	 */
	public static String joinUrl(String headUrl, String methodUrl) {
		if(null==headUrl) {
			return "";
		}
		if(headUrl.isEmpty()) {
			return methodUrl;
		}
		// 对于【/a/b c/d】路径的合并
		if (!headUrl.endsWith("/") && !methodUrl.startsWith("/")) {
			return headUrl + "/" + methodUrl;
		}
		// 对于【/a/b/ /c/d】路径的合并
		if (headUrl.endsWith("/") && methodUrl.startsWith("/")) {
			return headUrl + methodUrl.substring(1);
		}
		return headUrl + methodUrl;
	}
}
