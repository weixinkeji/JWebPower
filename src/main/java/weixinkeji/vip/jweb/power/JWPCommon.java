package weixinkeji.vip.jweb.power;

import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.event.JWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.JWPGlobalEvent;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.tools.JWPRequestUrlTool;

public class JWPCommon {
	
//配置区-------------------------
	// 扫描的包
	public  String[] scan_package;
	// 静态资源
	public  String static_resources_prefix;
	// true：不在管理范围内的游离路径，允许任何人访问
	// false: 不在管控范围的游离路径，不允许任何人访问！（默认）
	public  boolean free_url_open;
	// 默认是支持动态路径（路径即参数的意思）
	public  boolean dynamics_controller_url;
	// 在控制台输出框架启动信息（默认为false)
	public  boolean console_print;

//对接区-------------------------
	//系统权限路径--对接
	private JWPSystemInterfaceConfig siConfig;
	//用户权限-对接
	private JWPUserInterface userPower;
		
//事件区-------------------------	
	// 全局事件
	private JWPGlobalEvent jwpGlobalEvent;
	// 事件
	private JWPControllerURLPowerEvent controllerUrlPowerEvent;
	
//权限处理区
	private JWPStaticResourcesModel staticResourcesModel;
	private JWPRequestUrlTool requestUrlTool;
	
}
