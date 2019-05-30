package weixinkeji.vip.jweb.power._init;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weixinkeji.vip.jweb.power.ann.JWPRegListen;
import weixinkeji.vip.jweb.power.ann.JWPRegListenUrl;
import weixinkeji.vip.jweb.power.listen.JWPListenInterface;
import weixinkeji.vip.jweb.power.listen.JWPListenPool;
import weixinkeji.vip.jweb.power.vo.JWPStaticUrlAndListenVO;

/**
 * 检出所有权限编号、权限等级、会话区、放行区
 * 
 * @author wangchunzi
 *
 */
public class _IniJWPPowerCode extends _InitTool {
//	private JWPSystemInterfaceConfig siConfig = super.findObject(JWPSystemInterfaceConfig.class,
//			new DefaultJWPSystemInterfaceConfig());

//注：收集监听的信息中，三个HashMap有可能有重复的收集。所以在使用时，优先使用 方法上的（如果有），次使用类上的（如果有），最后使用扫描的（如果有）

	/**
	 * @param list 扫描到的类
	 */
	_IniJWPPowerCode(List<Class<?>> list) {
		super(list);
		this.iniCMListen();
	}

}

class InMethodPowerCode {
	// 在方法上的 权限编号--Controller
	private Map<Method, String[]> inMethod_identifiter = new HashMap<>();
	// 在方法上的 权限等级--Controller
	private Map<Method, String[]> inMethod_grades = new HashMap<>();
	// 在方法上的 会话--Controller
	private Map<Method,Boolean> inMethod_session = new HashMap<>();
	// 在方法上的 放行区--Controller
	private Map<Method,Boolean> inMethod_public = new HashMap<>();
	/**
	 * 设置权限编号
	 * @param m 方法
	 * @param code 权限编号
	 */
	public void setIdentifiter(Method m,String[] code) {
		this.inMethod_identifiter.put(m, code);
	}
	
	/**
	 * 取得权限编号
	 * @param m 方法
	 * @return String[]
	 */
	public String[] getIdentifiter(Method m) {
		return this.inMethod_identifiter.get(m);
	}
	
	/**
	 *  设置权限等级
	 * @param m	方法
	 * @param String[]
	 */
	public void setGrades(Method m,String[] code) {
		this.inMethod_grades.put(m, code);
	}
	
	/**
	 *  取得权限等级
	 * @param m	方法
	 * @return String[]
	 */
	public String[] getGrades(Method m) {
		return this.inMethod_grades.get(m);
	}
	
	/**
	 * 设置会话
	 * @param m 方法
	 * @return code 是否会话区
	 */
	public void setSession(Method m,Boolean code) {
		this.inMethod_session.put(m, code);
	}
	
	/**
	 * 取得会话
	 * @param m
	 * @return
	 */
	public Boolean getSession(Method m) {
		return this.inMethod_session.get(m);
	}

	/**
	 * 设置公共区
	 * @param m
	 * @return
	 */
	public void setPublic(Method m,Boolean code) {
		this.inMethod_public.put(m, code);
	}
	
	/**
	 * 取得公共区
	 * @param m
	 * @return
	 */
	public Boolean getPublic(Method m) {
		return this.inMethod_public.get(m);
	}
	
}

class InClassPowerCode {
	// 在类上的 权限编号--Controller
		private Map<Class<?>, String[]> inClass_identifiter = new HashMap<>();
		// 在类上的 权限等级--Controller
		private Map<Class<?>, String[]> inClass_grades = new HashMap<>();
		// 在类上的 会话--Controller
		private Map<Class<?>,Boolean> inClass_session = new HashMap<>();
		// 在类上的 放行区--Controller
		private Map<Class<?>,Boolean> inClass_public = new HashMap<>();
		/**
		 * 设置权限编号
		 * @param c 类
		 * @param code 权限编号
		 */
		public void setIdentifiter(Class<?> c,String[] code) {
			this.inClass_identifiter.put(c, code);
		}
		
		/**
		 * 取得权限编号
		 * @param c 类
		 * @return String[]
		 */
		public String[] getIdentifiter(Class<?> c) {
			return this.inClass_identifiter.get(c);
		}
		
		/**
		 *  设置权限等级
		 * @param c 类
		 * @param String[]
		 */
		public void setGrades(Class<?> c,String[] code) {
			this.inClass_grades.put(c, code);
		}
		
		/**
		 *  取得权限等级
		 * @param c 类
		 * @return String[]
		 */
		public String[] getGrades(Class<?> c) {
			return this.inClass_grades.get(c);
		}
		
		/**
		 * 设置会话
		 * @param c 类
		 * @param code 是否会话区
		 */
		public void setSession(Class<?> m,Boolean code) {
			this.inClass_session.put(m, code);
		}
		
		/**
		 * 取得会话
		 * @param c 类
		 * @return Boolean
		 */
		public Boolean getSession(Class<?> m) {
			return this.inClass_session.get(m);
		}

		/**
		 * 设置公共区
		 * @param c 类
		 * @param code 是否公共区
		 */
		public void setPublic(Class<?> m,Boolean code) {
			this.inClass_public.put(m, code);
		}
		
		/**
		 * 取得公共区
		 * @param c 类
		 * @return  Boolean
		 */
		public Boolean getPublic(Class<?> m) {
			return this.inClass_public.get(m);
		}
}

class InExcpressPowerCode {
	// 在类上的 权限编号--Controller
			private Map<String, String[]> inExcpress_identifiter = new HashMap<>();
			// 在类上的 权限等级--Controller
			private Map<String, String[]> inExcpress_grades = new HashMap<>();
			// 在类上的 会话--Controller
			private Map<String,Boolean> inExcpress_session = new HashMap<>();
			// 在类上的 放行区--Controller
			private Map<String,Boolean> inExcpress_public = new HashMap<>();
			/**
			 * 设置权限编号
			 * @param c 类
			 * @param code 权限编号
			 */
			public void setIdentifiter(String m,String[] code) {
				this.inExcpress_identifiter.put(m, code);
			}
			
			/**
			 * 取得权限编号
			 * @param c 类
			 * @return String[]
			 */
			public String[] getIdentifiter(String m) {
				return this.inExcpress_identifiter.get(m);
			}
			
			/**
			 *  设置权限等级
			 * @param c 类
			 * @param String[]
			 */
			public void setGrades(String m,String[] code) {
				this.inExcpress_grades.put(m, code);
			}
			
			/**
			 *  取得权限等级
			 * @param c 类
			 * @return String[]
			 */
			public String[] getGrades(String m) {
				return this.inExcpress_grades.get(m);
			}
			
			/**
			 * 设置会话
			 * @param c 类
			 * @param code 是否会话区
			 */
			public void setSession(String m,Boolean code) {
				this.inExcpress_session.put(m, code);
			}
			
			/**
			 * 取得会话
			 * @param c 类
			 * @return Boolean
			 */
			public Boolean getSession(String m) {
				return this.inExcpress_session.get(m);
			}

			/**
			 * 设置公共区
			 * @param c 类
			 * @param code 是否公共区
			 */
			public void setPublic(String m,Boolean code) {
				this.inExcpress_public.put(m, code);
			}
			
			/**
			 * 取得公共区
			 * @param c 类
			 * @return  Boolean
			 */
			public Boolean getPublic(String m) {
				return this.inExcpress_public.get(m);
			}
}
