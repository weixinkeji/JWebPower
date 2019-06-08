package weixinkeji.vip.jweb.power._init;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;
import weixinkeji.vip.jweb.tools.JWPControllePrint;

/**
 * 权限业务处理
 * 
 * @author wangchunzi
 *
 */
public class _5_iniJWPModel_Controller extends __InitTool {

	// 建立的权限模型：路径-权限模型
	private Map<String, JWPControllerModel> modelMap = new HashMap<>();
	private _4_IniJWPPowerCode_Controller powerCode;
	private _3_IniJWPListen listen;
	private JWPControllePrint pr;

	/**
	 * @param powerCode 权限管理中心
	 * @param listen    监听管理中心
	 */
	_5_iniJWPModel_Controller(_4_IniJWPPowerCode_Controller powerCode, _3_IniJWPListen listen, JWPControllePrint pr) {
		this.pr = pr;
		this.powerCode = powerCode;
		this.listen = listen;
		this._1_createMode_express();// 从表达式中，建立权限模型
		this._2_createMode_MethodAndClass();// 从方法、类中，建立 权限模型

		pr.addMessage("[目录]控制区权限模型区");
		prModel();// 打印信息
	}

	public void prModel() {
		this.prModelByType(JWPType.common, false);
		this.prModelByType(JWPType.session, false);
		this.prModelByType(JWPType.grades, false);
		this.prModelByType(JWPType.code, false);
		this.prModelByType(JWPType.gradesAndCode, false);
		this.prModelByType(JWPType.onlyListen, false);
		this.prModelByType(JWPType.unknow, false);
		pr.printMessage();
		pr.clearMessage();
	}

	private void prModelByType(JWPType type, boolean error) {
		StringBuilder sb = new StringBuilder();
		JWPControllerModel model;
		int messageHead;
		for (Map.Entry<String, JWPControllerModel> kv : this.modelMap.entrySet()) {
			model = kv.getValue();
			if (model.urlType == type) {
				sb.append(" [").append(this.prModelByType_chooseType(model.urlType)).append("]：");
				messageHead=sb.length();
//				if(model.urlType==JWPType.common||model.urlType==JWPType.session||model.urlType==JWPType.unknow||model.urlType==JWPType.onlyListen) {
//					//sb.append("放行区=").append(model.isCommon ? "是" : "否");
//				}
				if(model.urlType==JWPType.session) {
					//sb.append(messageHead==sb.length()?"":"，").append("会话区=").append(model.isSession ? "是" : "否");
				}
				else if(model.urlType==JWPType.grades||model.urlType==JWPType.gradesAndCode) {
					sb.append(messageHead==sb.length()?"":"，").append("权限等级=").append(null != model.grades ? Arrays.deepToString(model.grades) : null);
				}
				else if(model.urlType==JWPType.code||model.urlType==JWPType.gradesAndCode) {
					sb.append(messageHead==sb.length()?"":"，").append("权限编号=").append(null != model.code ? Arrays.deepToString(model.code) : null);
				}
				
				sb.append(messageHead==sb.length()?"":"，").append("路径=").append(kv.getKey());
				
				if (model.isHasListen) {
					sb.append(messageHead==sb.length()?"":"，").append("监听类=").append(model.listen.getClass().getName());
				}
				
				if (error) {
					pr.addErrorMessage(sb.toString(), 1);
				} else {
					pr.addMessage(sb.toString(), 1);
				}

				sb.setLength(0);
			}
		}
	}

	private String prModelByType_chooseType(JWPType type) {
		switch (type) {
		case common:
			return "公共区";
		case session:
			return "会话区";
		case grades:
			return "等级区";
		case code:
			return "编号区";
		case gradesAndCode:
			return "等级+编号区";
		case onlyListen:
			return "只监听";
		case unknow:
			return "未知区";
		default:
			return "异常区";
		}

	}

	/**
	 * 取得控制区的权限处理模型
	 * 
	 * @return Map
	 */
	public Map<String, JWPControllerModel> getModel_controller() {
		return this.modelMap;
	}

	/**
	 * 先为表达式中的权限 建立模型
	 */
	private void _1_createMode_express() {
		// 先对表达式上的直接权限 建立权限模型
		InExcpressDirectPowerCode edp = powerCode.getEdp();
		
		//找到请求路径关联的方法类。用途：优先使用方法上的监听
		Map<String, MethodAndClass> mc = powerCode.getUrlAndMethodAndClass();
		MethodAndClass mcValue;//方法类临时存
		
		for (Map.Entry<String, JWPCodeVO> kv : edp.getMethodPowerCode().entrySet()) {
			mcValue=mc.get(kv.getKey());
			modelMap.put(kv.getKey(),
					new JWPControllerModel(kv.getValue(), listen.getJWPListenInterface(null!=mcValue?mcValue.m:null, null!=mcValue?mcValue.c:null, kv.getKey())));
		}
		// 对表达式上的权限，建立权限模型
		InExcpressPowerCode ep = powerCode.getEp();

		if (powerCode.isMethodUrl()) {
			for (Map.Entry<String, MethodAndClass> kv : powerCode.getUrlAndMethodAndClass().entrySet()) {
				mcValue=mc.get(kv.getKey());
				this._1_createMode_express_model1(ep, kv.getKey(),mcValue);
			}
		} else {
			for (Map.Entry<String, Class<?>> kv : powerCode.getUrlAndClass().entrySet()) {
				mcValue=mc.get(kv.getKey());
				this._1_createMode_express_model1(ep, kv.getKey(),mcValue);
			}
		}
	}

	private void _1_createMode_express_model1(InExcpressPowerCode ep, String request,MethodAndClass mcValue) {
		JWPControllerModel jwp = modelMap.get(request);
		JWPCodeVO vo = ep.getPowerCode(request);
		if (null == jwp) {
			modelMap.put(request, new JWPControllerModel(vo, listen.getJWPListenInterface(null!=mcValue?mcValue.m:null,null!=mcValue?mcValue.c:null, request)));
		} else {
			// 对表达式上的权限进行融合
			modelMap.put(request, new JWPControllerModel(vo, jwp));
		}
	}

	/**
	 * 为注解在方法和类上的权限 建立模型
	 */
	private void _2_createMode_MethodAndClass() {
		JWPCodeVO vo;
		// 绑定在类上的权限
		InClassPowerCode cp = powerCode.getCp();
		// 绑定在方法上的权限
		InMethodPowerCode mp = powerCode.getMp();

		if (powerCode.isMethodUrl()) {
			for (Map.Entry<String, MethodAndClass> kv : powerCode.getUrlAndMethodAndClass().entrySet()) {
				// 合并方法和类上的权限
				vo = JWPCodeVO.merge(mp.getPowerCode(kv.getValue().m)// 方法上的权限
						, cp.getPowerCode(kv.getValue().c)// 类上的权限
				);
				if (vo.type == JWPType.unknow && modelMap.get(kv.getKey()) != null) {
					continue;// 没有权限注解在方法、类上。那么，采用表达式的权限。
				}
				modelMap.put(kv.getKey(), new JWPControllerModel(vo,
						listen.getJWPListenInterface(kv.getValue().m, kv.getValue().c, kv.getKey())));

			}
		} else {
			for (Map.Entry<String, Class<?>> kv : powerCode.getUrlAndClass().entrySet()) {
				vo = cp.getPowerCode(kv.getValue()); // 类上的权限
				modelMap.put(kv.getKey(),
						new JWPControllerModel(vo, listen.getJWPListenInterface(null, kv.getValue(), kv.getKey())));
			}
		}
	}

//	/**
//	 * 取得 静态资源 处理模型
//	 * @return JWPStaticResourcesModel
//	 */
//	public JWPStaticResourcesModel getJWPStaticResourcesModel() {
//		JWPStaticResourcesURLExpresstion sre =this.express.getJWPStaticResourcesURLExpresstion();
//		Map<String, Class<? extends JWPListenInterface>> map = new HashMap<>();
//		sre.setListen(map);
//		return new JWPStaticResourcesModel(map); 
//	}

}
