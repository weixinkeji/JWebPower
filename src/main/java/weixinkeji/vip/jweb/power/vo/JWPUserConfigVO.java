package weixinkeji.vip.jweb.power.vo;

final public class JWPUserConfigVO {
	// 扫描的包
	public final String[] scan_package;
	// 静态资源
	public final String static_resources_prefix;
	// true：不在管理范围内的游离路径，允许任何人访问
	// false: 不在管控范围的游离路径，不允许任何人访问！（默认）
	public final boolean free_url_open;
	// 默认是支持动态路径（路径即参数的意思）
	public final boolean dynamics_controller_url;
	// 在控制台输出框架启动信息（默认为false)
	public final boolean console_print;

	public JWPUserConfigVO(String[] scan_package, String static_resources_prefix, boolean free_url_open,
			boolean dynamics_controller_url, boolean console_print) {
		this.scan_package = scan_package;
		this.static_resources_prefix = static_resources_prefix;
		this.free_url_open = free_url_open;
		this.dynamics_controller_url = dynamics_controller_url;
		this.console_print = console_print;
	}

}
