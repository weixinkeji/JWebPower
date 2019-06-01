package weixinkeji.vip.jweb.power._init;

import java.util.Map;

import weixinkeji.vip.jweb.power.config.JWPUserInterface;
import weixinkeji.vip.jweb.power.event.JWPControllerURLPowerEvent;
import weixinkeji.vip.jweb.power.event.JWPGlobalEvent;
import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.tools.JWPRequestUrlTool;

public class ReturnResultObject {

	private Map<String, JWPControllerModel> model_controller;
	private JWPStaticResourcesModel model_static;

	private JWPGlobalEvent event_global;// 全局事件
	private JWPControllerURLPowerEvent event_controller;// 事件

	// 用户权限 -接口
	private JWPUserInterface userPower;

	private JWPRequestUrlTool requestUrlTool;

	public Map<String, JWPControllerModel> getModel_controller() {
		return model_controller;
	}

	public JWPStaticResourcesModel getModel_static() {
		return model_static;
	}

	public JWPGlobalEvent getEvent_global() {
		return event_global;
	}

	public JWPControllerURLPowerEvent getEvent_controller() {
		return event_controller;
	}

	public JWPUserInterface getUserPower() {
		return userPower;
	}

	public JWPRequestUrlTool getRequestUrlTool() {
		return requestUrlTool;
	}

	public void setModel_controller(Map<String, JWPControllerModel> model_controller) {
		this.model_controller = model_controller;
	}

	public void setModel_static(JWPStaticResourcesModel model_static) {
		this.model_static = model_static;
	}

	public void setEvent_global(JWPGlobalEvent event_global) {
		this.event_global = event_global;
	}

	public void setEvent_controller(JWPControllerURLPowerEvent event_controller) {
		this.event_controller = event_controller;
	}

	public void setUserPower(JWPUserInterface userPower) {
		this.userPower = userPower;
	}

	public void setRequestUrlTool(JWPRequestUrlTool requestUrlTool) {
		this.requestUrlTool = requestUrlTool;
	}
}
