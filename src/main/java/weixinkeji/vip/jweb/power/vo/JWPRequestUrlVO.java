package weixinkeji.vip.jweb.power.vo;

public class JWPRequestUrlVO {

	/**
	 * 项目上下文
	 */
	public final String contextPath;
	private final int contextPathLength;
	private final boolean hasContextPath;
	/**
	 * 请求路径后缀
	 */
	public final String[] suffix;
	private final int suffixIndex;

	/**
	 * /请求路径 形式时，i=0
	 * <br><br>
	 * /请求路径.hz 形式时，i=2
	 * <br><br>
	 * /项目名/请求路径 形式时，i=1
	 * <br><br>
	 * /项目名/请求路径.hz 形式时，i=12
	 * <br><br>
	 */
	public final int i;

	/**
	 * RequestURLVO
	 * 
	 * @param paramPath 项目的上下文contextPath
	 * @param paramSuffix 请求路径的后缀 
	 */
	public JWPRequestUrlVO(String paramPath, String[] paramSuffix) {
		this.contextPath = null == paramPath ? "" : paramPath.trim();
		this.suffix = null == paramSuffix ? null : paramSuffix;
		if (this.contextPath.isEmpty() && null == this.suffix) {// 无前缀、后缀。
			this.i = 0;
		} else if (null == this.suffix) {// 只有前缀
			this.i = 1;
		} else if (this.contextPath.isEmpty()) {// 只有后缀
			this.i = 2;
		} else {// 前缀和后缀都有
			this.i = 12;
		}
		this.suffixIndex = null == suffix ? 0 : suffix.length;
		this.contextPathLength = this.contextPath.length();
		this.hasContextPath=this.contextPathLength>0;
	}

	/**
	 * 处理请求路径，返回符合框架使用的请求路径
	 * @param url 请求路径
	 * @return String
	 */
	public String formatRequestURL(final String url) {
		switch (i) {
		case 0: {// /请求路径 形式时，i=0
			return url;
		}
		case 1: {// /项目名/请求路径 形式时，i=1
			return url.substring(this.contextPathLength);
		}
		case 2: {// /请求路径.hz 形式时，i=2 后缀时，有可能是自定义的后缀。
			String mySuffix = this.findUrlSuffix(url);// 找到url的后缀
			if (null == mySuffix) {
				return url;
			}
			return url.substring(0, url.length() - mySuffix.length());
		}
		case 12: {// /项目名/请求路径.hz 形式时，i=12
			String mySuffix = this.findUrlSuffix(url);// 找到url的后缀
			if (null != mySuffix) {
				return url.substring(this.contextPathLength, url.length() - mySuffix.length());
			}
			return url.substring(this.contextPathLength);
		}

		}
		return null;
	}
	/**
	 * 处理静态资源请求路径（主要去除contextPath前缀），返回符合框架使用的请求路径
	 * @param url 请求路径
	 * @return String
	 */
	public String formatStaticRequestURL(final String url) {
		if(this.hasContextPath) {
			return url.substring(contextPathLength);
		}
		return url;
	}
	
	private String findUrlSuffix(final String url) {
		switch (this.suffixIndex) {
		case 0:// 没有后缀
			return null;
		case 1: {
			// 只有一个后缀.2种情况： /H.elloServlet.json 或 /H.elloServlet
			return url.endsWith(this.suffix[0]) ? this.suffix[0] : null;
		}
		case 2: {
			// 只有2个后缀 三种情况： /H.elloServlet.json 或 /H.elloServlet.xml /H.elloServlet
			boolean hasSuffix1 = url.endsWith(this.suffix[0]);
			boolean hasSuffix2 = url.endsWith(this.suffix[1]);
			String mySuffix = hasSuffix1 ? this.suffix[0] : this.suffix[1];
			if (hasSuffix1 || hasSuffix2) {
				return mySuffix;
			}
			return null;
		}
		default: {
			for (String ms : this.suffix) {
				if (url.endsWith(ms)) {
					return ms;
				}
			}
			return null;
		}
		}
	}
}
