package weixinkeji.vip.jweb.power._init;

import weixinkeji.vip.jweb.power.config.DefaultJWPUserInterface;
import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.vo.JWPUserConfigVO;
import weixinkeji.vip.jweb.scan.JWPScanClassFactory;
import weixinkeji.vip.jweb.tools.JWPControllePrint;

/**
 * 初始化之集中资源
 * @author wangchunzi
 *
 */
final public class _1_IniMain extends __InitTool {
	
	public _1_IniMain(JWPUserConfigVO config,ReturnResultObject rs,JWPControllePrint pr) {
		
		super(JWPScanClassFactory.getClassByFilePath(config.scan_package));
		
		//系统请求路径对接
		_2_IniAbutment abutmentUrl=new _2_IniAbutment(super.list);
		
		//工具
		_3_IniJWPTool tool=new _3_IniJWPTool(super.list,config.webContextPath,abutmentUrl.getJWPSystemInterfaceConfig().getRequestUrlSuffix());
		rs.setRequestUrlTool(tool.getRequestURLTool());
		
		//表达式
		_3_IniJWPExpress express=new _3_IniJWPExpress(super.list);
		//监听
		_3_IniJWPListen listen=new _3_IniJWPListen(super.list);
		
		//权限
		_4_IniJWPPowerCode_Controller controllerPower=new _4_IniJWPPowerCode_Controller(super.list,abutmentUrl.getJWPSystemInterfaceConfig(),express);
		
		//业务处理模型
		_5_iniJWPModel_Static model_static=new _5_iniJWPModel_Static(listen,pr);//静态
		rs.setModel_static(model_static.getJWPStaticResourcesModel());
		_5_iniJWPModel_Controller model_controller=new _5_iniJWPModel_Controller(controllerPower,listen,pr);
		rs.setModel_controller(model_controller.getModel_controller());
		
		//用户的权限
		JWPUserInterface userPower=findObject(JWPUserInterface.class, new DefaultJWPUserInterface());
		rs.setUserPower(userPower);
		
		//事件
		_999_IniJWPEvent event=new _999_IniJWPEvent(super.list);
		rs.setEvent_global(event.getJWPGlobalEvent());
		rs.setEvent_controller(event.getJWPControllerURLPowerEvent());
		
	}
}
