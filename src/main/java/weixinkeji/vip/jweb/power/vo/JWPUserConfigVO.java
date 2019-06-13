package weixinkeji.vip.jweb.power.vo;

final public class JWPUserConfigVO {
	// 扫描的包
	public final String[] jwp_scan_package;
	// 静态资源
	public final String jwp_static_resources_prefix;
	// true：不在管理范围内的游离路径，允许任何人访问
	// false: 不在管控范围的游离路径，不允许任何人访问！（默认）
	public final boolean jwp_controller_free_url;
	// 默认是支持动态路径（路径即参数的意思）
	public final boolean jwp_controller_dynamics_url;
	// 在控制台输出框架启动信息（默认为false)
	public final boolean jwp_print_console;
	
	public final String webContextPath;

	
	public JWPUserConfigVO(
			String[] jwp_scan_package
			, String jwp_static_resources_prefix
			, boolean jwp_controller_free_url
			, boolean jwp_controller_dynamics_url
			, boolean jwp_print_console
			,String webContextPath
			) {
		this.jwp_scan_package = jwp_scan_package;
		this.jwp_static_resources_prefix = jwp_static_resources_prefix;
		this.jwp_controller_free_url = jwp_controller_free_url;
		this.jwp_controller_dynamics_url = jwp_controller_dynamics_url;
		this.jwp_print_console = jwp_print_console;
		this.webContextPath=webContextPath;
	}

}
