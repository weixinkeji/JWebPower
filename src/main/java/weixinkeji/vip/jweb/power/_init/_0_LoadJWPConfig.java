package weixinkeji.vip.jweb.power._init;

import java.io.File;
import java.util.Map;

import weixinkeji.vip.jweb.power.vo.JWPUserConfigVO;
import weixinkeji.vip.jweb.tools.JWPControllePrint;
import weixinkeji.vip.jweb.tools.JWPPathTool;
import weixinkeji.vip.jweb.tools.JWPPropertiesTool;

/**
 * 初始化用户的系统配置
 * @author wangchunzi
 *
 */
public class _0_LoadJWPConfig {
	
	private Map<String, String> configMap;
	private JWPControllePrint pr;
	public _0_LoadJWPConfig(String configFilePath,JWPControllePrint pr) {
		this.pr=pr;
		 configMap = new JWPPropertiesTool()
					.loadPropertiesToMap(new File(JWPPathTool.getMyProjectPath(null==configFilePath||configFilePath.isEmpty()?"JWP.properties":configFilePath))); 
		 pr.printMessage();
		 pr.clearMessage();
	}
	
	/**
	 * 取得用户配置的参数
	 * @param ContextPath 项目上下文名称
	 * @return JWPUserConfigVO
	 */
	public JWPUserConfigVO getJWPUserConfigVO(String ContextPath) {
		// 扫描的包
		String jwp_scan_package[]=null;
		// 静态资源
		String jwp_static_resources_prefix;
		// true：不在管理范围内的游离路径，允许任何人访问
		// false: 不在管控范围的游离路径，不允许任何人访问！（默认）
		boolean jwp_controller_free_url=false;
		// 默认是支持动态路径（路径即参数的意思）
		boolean jwp_controller_dynamics_url=true;
		// 在控制台输出框架启动信息（默认为false)
		boolean jwp_print_console=false;
		String ppv;
//计算与附值		
		if(null!=(ppv=this.configMap.get("jwp.scan.package"))) {
			jwp_scan_package=ppv.split("[,，]{1}");
		}else {
			return null;
		}
		
		jwp_static_resources_prefix=this.initStaticUrl(ContextPath, this.configMap.get("jwp.static.resources.prefix"));
		if(null!=(ppv=this.configMap.get("jwp.controller.free.url"))) {
			jwp_controller_free_url=Boolean.parseBoolean(ppv);
		}
		if(null!=(ppv=this.configMap.get("jwp.controller.dynamics.url"))) {
			jwp_controller_dynamics_url=Boolean.parseBoolean(ppv);
		}
		if(null!=(ppv=this.configMap.get("jwp.print.console"))) {
			jwp_print_console=Boolean.parseBoolean(ppv);
		}
		return new JWPUserConfigVO(
				jwp_scan_package
				,jwp_static_resources_prefix
				,jwp_controller_free_url
				,jwp_controller_dynamics_url
				,jwp_print_console
				,ContextPath
				);
	}
	// 对静态资源前缀路径进行格式化
	private String initStaticUrl(String ContextPath, final String myStatic_resources_prefix) {
		String static_resources_prefix="/static/";
		if (null == myStatic_resources_prefix || myStatic_resources_prefix.isEmpty()) {
			static_resources_prefix = ContextPath + "/static/";
			pr
					.addErrorMessage("没找找到配置文件中的键值对[static_resources_prefix],或键的值为空。系统自动采用 /static/ 作为静态url的前缀！", 1);
		} else if (!myStatic_resources_prefix.startsWith("/")) {
			static_resources_prefix = ContextPath + "/" + myStatic_resources_prefix;
		} else {
			static_resources_prefix = ContextPath + myStatic_resources_prefix;
		}
		return static_resources_prefix;
	}
	
}
