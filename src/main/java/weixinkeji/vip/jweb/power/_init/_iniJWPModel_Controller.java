package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.model.DUrlPools;
import weixinkeji.vip.jweb.power.model.JWPControllerModel;
import weixinkeji.vip.jweb.power.model.JWPType;
import weixinkeji.vip.jweb.power.tools.DUrlTools;
import weixinkeji.vip.jweb.power.tools.JWPExpressionTool;
import weixinkeji.vip.jweb.power.vo.JWPCodeVO;
import weixinkeji.vip.jweb.power.vo.JWPExpressConfigVO;
import weixinkeji.vip.jweb.power.vo.JWPExpressVO;
import weixinkeji.vip.jweb.tools.JWPControllePrint;

/**
 * 权限业务处理
 * @author wangchunzi
 *
 */
public class _iniJWPModel_Controller extends _InitTool{

	private _IniJWPExpress express;
	private _IniAbutment_url abutmentUrl;
	private JWPExpressionTool expressTool;
	// 建立的权限模型：路径-权限模型
	private Map<String, JWPControllerModel> modelMap;
	
	
	/**
	 * @param list 扫描到的类
	 * @param express 表达式
	 * @param abutmentUrl 路径对接类
	 */
	_iniJWPModel_Controller(List<Class<?>> list,_IniJWPExpress express,_IniAbutment_url abutmentUrl) {
		super(list);
		this.express=express;
		this.expressTool=this.express.getJWPExpressionTool();//// 实例一个表达式处理工具
		this.abutmentUrl=abutmentUrl;
	}
	
	/**
	 * 直接建立 URL-权限模型
	 * 
	 * @param jwebPowerControllerModel 权限模型
	 */
	public void setControllerPowerModel() {
		// 实例一个专门处理Controller 权限的对象
		JWPExpressConfigVO controllerExpress =this.express.getJWPControllerURLExpresstion();
		this._1_initUserUrlModel(controllerExpress);//从表达式数据中，找到所有表示[直接路径]的，直接为他们建立业务处理模型
		
		//其他情况，需要 权限类-方法 上绑定的路径，一一校对方可。
		for (Class<?> c : this.list) {
			_2_initUserUrlModel(c);
		}
	}
	
	/**
	 * 从表达式数据中，找到所有表示[直接路径]的，直接为他们建立业务处理模型
	 * 
	 * @param controllerExpress Controller表达式
	 */
	private void _1_initUserUrlModel(JWPExpressConfigVO controllerExpress) {
		Set<String> expression = controllerExpress.getPublicPowerExpresstion();
		JWPExpressVO vo;
		String final_requestURL;
		// 先对Controller【公共】区的路径进行检验
		for (String urlOrExpression : expression) {
			// 如果用户写的不是表达式，而是完整的url,进行建模型
			if (null != (vo = this.expressTool.getPowerUrl(urlOrExpression))) {
				JWPControllePrint.addMessage("直接放行的路径：" + vo.getExpress(), 1);
				final_requestURL=vo.getExpress();
				if (final_requestURL.contains("{")) {
					final_requestURL = new String(DUrlTools.formatURL(final_requestURL));
					DUrlPools.addDUrl(final_requestURL);
				}
				modelMap.put(final_requestURL, new JWPControllerModel(JWPType.common, null, null, null));
			}
		}
		// 对Controller【权限等级】区的路径进行检验
		expression = controllerExpress.getSessionPowerExpresstion();
		for (String urlOrExpression : expression) {
			// 如果用户写的不是表达式，而是完整的url,进行建模型
			if (null != (vo = this.expressTool.getPowerUrl(urlOrExpression))) {
//				System.out.println(vo+"直接加入权限模型的【权限等级】路径："+vo.getExpress());
				JWPControllePrint.addMessage(vo + "直接加入权限模型的【权限等级】路径：" + vo.getExpress(), 1);
				
				final_requestURL=vo.getExpress();
				if (final_requestURL.contains("{")) {
					final_requestURL = new String(DUrlTools.formatURL(final_requestURL));
					DUrlPools.addDUrl(final_requestURL);
				}
				modelMap.put(final_requestURL, new JWPControllerModel(JWPType.grades,
						null == vo.getValues() ? JWPExpressVO.EMPTY_POWER : vo.getValues(), null, null));
			}
		}
		// 对Controller【权限编号】区的路径进行检验
		// 如果发现已经存在，执行合并，或覆盖（如果是公共区）
		expression = controllerExpress.getIdentifiterPowerExpression();
		JWPControllerModel model;
		for (String urlOrExpression : expression) {
			// 如果用户写的不是表达式，而是完整的url,进行建模型
			if (null != (vo = this.expressTool.getPowerUrl(urlOrExpression))) {
				if (null == vo.getValues()) {
					JWPControllePrint.addMessage("直接加入权限模型的【权限编号】路径：" + vo.getExpress() + " \t没有指定权限编号 ", 1);
					continue;
				}
				JWPControllePrint.addMessage("直接加入权限模型的【权限编号】路径：" + vo.getExpress(), 1);
				final_requestURL=vo.getExpress();
				if (final_requestURL.contains("{")) {
					final_requestURL = new String(DUrlTools.formatURL(final_requestURL));
					DUrlPools.addDUrl(final_requestURL);
				}
				
				model = modelMap.get(vo.getExpress());
				// 表示还没有此路径的权限模型——直接建立权限模型；或权限权限为null,执行覆盖
				if (null == model || null == model.grades) {
					modelMap.put(final_requestURL,
							new JWPControllerModel(JWPType.identifiter, null, vo.getValues(), null));
				} else {// 执行合并
					modelMap.put(final_requestURL,
							new JWPControllerModel(JWPType.gradesAndIdentifiter, model.grades, vo.getValues(), null));
				}
			}
		}
	}
	
	private void _2_initUserUrlModel(Class<?> c) {
		String headURL = this.abutmentUrl.getJWPSystemInterfaceConfig().getURLByClass(c);
		if (null == headURL) {// 不是控制类
			return;
		}
		_AnnotationVOService annService=new _AnnotationVOService(c);
		_JWPInfoVO finalVO;
		Method[] methods = c.getMethods();
		String methodURL = null;
		// 存放计算结果
		String final_requestURL = null;
		JWPType final_powerType = null;
		JWPCodeVO final_powerCode = null;
		JWPListenInterface final_listen = null;// 监听的实现
		
		int i = 0;
		for (Method m : methods) {
			// 表示请求路径由 类相关url+方法相关url 组成！
			if (null != (methodURL = this.abutmentUrl.getJWPSystemInterfaceConfig().getURLByMethod(m))) {
				i++;
				annService.setMethod(m);
				finalVO=annService.get_JWPAnnotationVO();
				
				final_requestURL = this.abutmentUrl.getURLByClassUrlAndMethodUrl(headURL, methodURL);// 完整的请求路径
				if (final_requestURL.contains("{")) {
					final_requestURL = new String(DUrlTools.formatURL(final_requestURL));
					DUrlPools.addDUrl(final_requestURL);
				}

				// 方法优先级最高。如果方法的注解权限不为null,以方法的注解权限为准
				if (null != finalVO.powerType) {
					final_powerCode=finalVO.codeVO;
					final_powerType=finalVO.powerType;
					final_listen=finalVO.jwepListen;
				} else {// 否则执行表达式的权限处理
					// 检验表达式，取得url相关权限
					final_powerCode =this.express.getExpressPowerCode(final_requestURL);
					//this.getExpressPowerCode(final_requestURL, controllerExpress);
					final_powerType = this.getURLPowerType(final_powerCode);
				}
				// 只有监听的情况
				if (null != final_listen && null == final_powerType) {
					JWPControllePrint.addErrorMessage(final_requestURL + " 注意，此路径只有监听！", 1);
					modelMap.put(final_requestURL,
							new JWPControllerModel(JWPType.onlyListen, null, null, final_listen));
					continue;
				}

				// 如果权限类型为null,表示此路径不在权限管理范围内
				if (null == final_powerType) {
					JWPControllePrint.addErrorMessage(final_requestURL + " 不在监控范围内！", 1);
					continue;
				}
				JWPControllePrint
						.addMessage(final_requestURL + ":权限检验，权限等级" + Arrays.deepToString(final_powerCode.getGrades())
								+ "    权限编号：" + Arrays.deepToString(final_powerCode.getIdentifiter()), 1);

				modelMap.put(final_requestURL, new JWPControllerModel(final_powerType, final_powerCode.getGrades(),
						final_powerCode.getIdentifiter(), final_listen));
			}
		}
		if (i == 0) {// 可能是Servlet的权限模型
			final_requestURL = headURL;
			if (final_requestURL.contains("{")) {
				final_requestURL = new String(DUrlTools.formatURL(final_requestURL));
				DUrlPools.addDUrl(final_requestURL);
			}
			finalVO=annService.get_JWPAnnotationVO();
			// 如果方法没有注解权限，以类的注解权限为准
			if  (null != finalVO.powerType) {
				final_powerCode=finalVO.codeVO;
				final_powerType=finalVO.powerType;
				final_listen=finalVO.jwepListen;
			} else {// 否则执行表达式的权限处理
				// 检验表达式，取得url相关权限
				final_powerCode = this.express.getExpressPowerCode(final_requestURL);
				final_powerType = this.getURLPowerType(final_powerCode);
			}
			// 只有监听的情况
			if (null != final_listen && null == final_powerType) {
				JWPControllePrint.addErrorMessage(final_requestURL + " 注意，此路径只有监听！", 1);
				modelMap.put(final_requestURL, new JWPControllerModel(JWPType.onlyListen, null, null, final_listen));
			}
			// 如果权限类型为null,表示此路径不在权限管理范围内
			if (null == final_powerType) {
				JWPControllePrint.addErrorMessage(final_requestURL + " 不在监控范围内！", 1);
				return;
			}
			JWPControllePrint
					.addMessage(final_requestURL + ":权限检验，权限等级" + Arrays.deepToString(final_powerCode.getGrades())
							+ "    权限编号：" + Arrays.deepToString(final_powerCode.getIdentifiter()), 1);
			modelMap.put(final_requestURL, new JWPControllerModel(final_powerType, final_powerCode.getGrades(),
					final_powerCode.getIdentifiter(), final_listen));
		}	
	}
}



