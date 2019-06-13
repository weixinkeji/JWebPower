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
	//---------开启自动编号、等级 的装饰（在编号或等级前，自动加入一些前缀或后缀）
	// {CcC}  大写C,小写c,大写C   ：表示权限所在类的 类名
	// {ccC}  小写c,小写c,大写C   ：表示权限所在类的 类名(并强制小写类名的首字母)
	// {ccc}  小写c,小写c,小写c   ：表示权限所在类的 类名(并强制小写类名的所有字母)
	// {CCC}  大写C,大写C,大写C   ：表示权限所在类的 类名(并强制大写类名的所有字母)
	public final boolean jwp_decorate_auto_map;
	//会自动在编号前，加入此值
	public final String jwp_decorate_codePrefix;
	// 会自动在编号后，加入此值
	public final String jwp_decorate_codeSffix;
	//会自动在等级前，加入此值
	public final String jwp_decorate_gradesPrefix;
	//会自动在等级后，加入此值
	public final String jwp_decorate_gradesSffix;
	
	public JWPUserConfigVO(
			String[] jwp_scan_package
			, String jwp_static_resources_prefix
			, boolean jwp_controller_free_url
			, boolean jwp_controller_dynamics_url
			, boolean jwp_print_console
			, boolean jwp_decorate_auto_map
			,String jwp_decorate_codePrefix
			,String jwp_decorate_codeSffix
			,String jwp_decorate_gradesPrefix
			,String jwp_decorate_gradesSffix
			,String webContextPath
			) {
		this.jwp_scan_package = jwp_scan_package;
		this.jwp_static_resources_prefix = jwp_static_resources_prefix;
		this.jwp_controller_free_url = jwp_controller_free_url;
		this.jwp_controller_dynamics_url = jwp_controller_dynamics_url;
		this.jwp_print_console = jwp_print_console;
		this.jwp_decorate_auto_map=jwp_decorate_auto_map;
		this.jwp_decorate_codePrefix=jwp_decorate_codePrefix;
		this.jwp_decorate_codeSffix=jwp_decorate_codeSffix;
		this.jwp_decorate_gradesPrefix=jwp_decorate_gradesPrefix;
		this.jwp_decorate_gradesSffix=jwp_decorate_gradesSffix;
		
		this.webContextPath=webContextPath;
	}

}
