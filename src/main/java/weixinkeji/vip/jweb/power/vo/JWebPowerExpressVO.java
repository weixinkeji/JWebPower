package weixinkeji.vip.jweb.power.vo;

/**
 * 用来装箱 “表达式 [[会员等级或权限编号]]” 的切割后的值
 * 
 * @author wangchunzi
 *
 */
final public class JWebPowerExpressVO {
	/**
	 * 表达式
	 */
	private String express;
	/**
	 * 会员等级|权限编号 ........null时，表示出错。空表示所有等级。
	 */
	private String[] values;

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = null == express ? null : express.trim();
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		if (null != values) {
			for (int i = 0; i < values.length; i++) {
				values[i] = values[i].trim();
			}
		}
		this.values = values;
	}

}
