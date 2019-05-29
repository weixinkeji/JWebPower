package weixinkeji.vip.jweb.power._init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weixinkeji.vip.jweb.power.expresstion.JWPStaticResourcesURLExpresstion;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.model.JWPStaticResourcesModel;

/**
 * 权限业务处理
 * @author wangchunzi
 *
 */
public class _iniJWPModel_Static extends _InitTool{

	private _IniJWPExpress express;
	/**
	 *  需要扫描类的集合
	 * @param list 扫描到的类
	 */
	_iniJWPModel_Static(List<Class<?>> list,_IniJWPExpress express) {
		super(list);
		this.express=express;
	}
	
	/**
	 * 取得 静态资源 处理模型
	 * @return JWPStaticResourcesModel
	 */
	public JWPStaticResourcesModel getJWPStaticResourcesModel() {
		JWPStaticResourcesURLExpresstion sre =this.express.getJWPStaticResourcesURLExpresstion();
		Map<String, Class<? extends JWPListenInterface>> map = new HashMap<>();
		sre.setListen(map);
		return new JWPStaticResourcesModel(map); 
	}
	
}
