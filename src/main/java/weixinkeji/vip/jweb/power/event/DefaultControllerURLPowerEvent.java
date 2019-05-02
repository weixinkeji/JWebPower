package weixinkeji.vip.jweb.power.event;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinkeji.vip.jweb.power.model.JWebPowerControllerModel;
import weixinkeji.vip.jweb.power.vo.SessionCodeAndIdentifiterCodeVO;

public class DefaultControllerURLPowerEvent implements IControllerURLPowerEvent {
	private void println(String requestURL,JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		String urltype="无";
		if(null!=powerModel) {
			switch(powerModel.urlType) {
			case common:
				urltype="放行区";
				break;
			case grades:
				urltype="会员等级区";
				break;
			case identifiter:
				urltype="编号区";
				break;	
			case gradesAndIdentifiter:
				urltype="会员等级区+编号区";
				break;	
			}
		}
		System.out.println("用户请求地址："+requestURL
				+"  ,路径归属：["+urltype+"]"
				+"  ,路径绑定的权限等级："+(null!=powerModel?Arrays.deepToString(powerModel.grades):null)
				+"  ,路径绑定的权限编号："+(null!=powerModel?Arrays.deepToString(powerModel.identifier):null)
				+"\n   用户权限等级："+(null!=userPower?Arrays.deepToString(userPower.getGrades()):null)
				+"\n   用户权限编号："+(null!=userPower?Arrays.deepToString(userPower.getIdentifiter()):null)
				);
		
	}
	/**
	 * 进入控制区时，自动调用执行的方法。必定执行。
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean jWebPower_start(HttpServletRequest req, HttpServletResponse resp,String requestURL, JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		//this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 服务请求，通过【放行区】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doPublicPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 通过【会员等级】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       ServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doSessionPower_success(HttpServletRequest req, HttpServletResponse resp, String requestURL,JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 未通【会员等级】过验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       ServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 */
	@Override
	public void doSessionPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, powerModel, userPower);
	}

	/**
	 * 通过【权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doIdentifiterPower_success(HttpServletRequest req, HttpServletResponse resp,String requestURL,
			JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
	
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 未通过【权限编号】验证，执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 */
	@Override
	public void doIdentifiterPower_fail(HttpServletRequest req, HttpServletResponse resp, String requestURL,JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, powerModel, userPower);
	}

	/**
	 * 通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpHttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认true放行
	 */
	@Override
	public boolean doSessionAndIdentifierPower_success(HttpServletRequest req, HttpServletResponse resp,String requestURL,
			JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, powerModel, userPower);
		return true;
	}

	/**
	 * 未通过【会话、权限编号】验证，执行此方法
	 * 
	 * @param req        HttpHttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param powerModel 与请求路径关联的权限模型
	 * @param userPower  用户的权限
	 * 
	 */
	@Override
	public void doSessionAndIdentifierPower_fail(HttpServletRequest req, HttpServletResponse resp,String requestURL,
			JWebPowerControllerModel powerModel,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, powerModel, userPower);
	}

	/**
	 * 
	 * 不在监控内 的请求地址。执行此方法
	 * 
	 * @param req        HttpServletRequest
	 * @param resp       HttpServletResponse
	 * @param requestURL String 用户请求的路径
	 * @param userPower  用户的权限
	 * 
	 * @return boolean 默认false,不放行！
	 */
	@Override
	public boolean doOtherURL(HttpServletRequest req, HttpServletResponse resp, String requestURL,SessionCodeAndIdentifiterCodeVO userPower) {
		this.println(requestURL, null, userPower);
		return false;
	}

}
