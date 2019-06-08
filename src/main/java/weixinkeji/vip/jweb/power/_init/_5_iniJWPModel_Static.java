package weixinkeji.vip.jweb.power._init;

import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;
import weixinkeji.vip.jweb.power.vo.JWPStaticUrlAndListenVO;
import weixinkeji.vip.jweb.tools.JWPControllePrint;

/**
 * 权限业务处理
 * 
 * @author wangchunzi
 *
 */
public class _5_iniJWPModel_Static extends __InitTool {

	private JWPStaticResourcesModel model_static;
	/**
	 * 静态模型处理
	 * 
	 * @param listen 监听器
	 */
	_5_iniJWPModel_Static(_3_IniJWPListen listen, JWPControllePrint pr) {
		JWPStaticUrlAndListenVO[] vo=listen.getJWPListenInterface_static();
		model_static=new JWPStaticResourcesModel(vo);
		if(null!=vo&&vo.length>0) {
			pr.addMessage("[目录]静态资源权限模型区");
			
			pr.printMessage();
			pr.clearMessage();
			StringBuilder sb = new StringBuilder();
			for(JWPStaticUrlAndListenVO obj:vo) {
				sb.append(" [资源区]：路径=").append(obj.url).append("， 监听类=").append(obj.jwpListenObject.getClass().getName());
				pr.addMessage(sb.toString(), 1);
				sb.setLength(0);
				
				pr.printMessage();
				pr.clearMessage();
				
			}
			pr.addMessage("");
			pr.printMessage();
			pr.clearMessage();
		}
	}

	/**
	 * 取得 静态资源 处理模型
	 * 
	 * @return JWPStaticResourcesModel
	 */
	public JWPStaticResourcesModel getJWPStaticResourcesModel() {
		return model_static;
	}

}
