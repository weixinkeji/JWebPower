package weixinkeji.vip.jweb.power._init;

import weixinkeji.vip.jweb.power.config.DefaultJWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.DefaultJWPUserInterface;
import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;
import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.scan.JWPScanClassFactory;

/**
 * 初始化之集中资源
 * @author wangchunzi
 *
 */
final public class _IniMain extends _InitTool {
	private JWPUserInterface userPower;
	private _IniAbutment_url abutmentUrl;
	
	private _IniJWPEvent event;
	private _IniJWPTool tool;
	private _IniJWPExpress express;
	
	private _iniJWPModel_Controller model_controller;
	private _iniJWPModel_Static model_static;

	public _IniMain(String contextPath,String[] path) {
		
		super(JWPScanClassFactory.getClassByFilePath(path));
		
		//对接
		abutmentUrl=new _IniAbutment_url(super.list);
		this.userPower=findObject(JWPUserInterface.class, new DefaultJWPUserInterface());
		
		
		//事件
		this.event=new _IniJWPEvent(list);
		
		//工具
		this.tool=new _IniJWPTool(list,contextPath,this.abutmentUrl.getJWPSystemInterfaceConfig().getRequestUrlSuffix());
		
		//表达式
		this.express=new _IniJWPExpress(super.list);
		
		//业务处理模型
		this.model_static=new _iniJWPModel_Static(super.list,this.express);//静态
		this.model_controller=new _iniJWPModel_Controller(super.list,this.express,abutmentUrl);//web入口处理
		
		
	}
}
