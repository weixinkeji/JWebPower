package weixinkeji.vip.jweb.power.config;

public interface JWPDecorateConfig {
	
	default String codePrefix(Class<?> c) {
		return "";
	}
	default String codeSuffix(Class<?> c) {
		return "";
	}
	default String gradesPrefix(Class<?> c) {
		return "";
	}
	default String gradesSuffix(Class<?> c) {
		return "";
	}
	/**
	 * 主要有来辅助用户，把固定后缀的字符，去掉。<br>
	 * 比如，我们写控制类时，往往喜欢  AController 或 XXController  或_Controller,<br>
	 * 所以，为了方便用户去除固定的后缀。<br>
	 * 
	 * @param name 字符串
	 * @param suffixs 后缀
	 * @return String
	 */
	default String substringSuffix(String name,String ...suffixs) {
		if(null==name||name.isEmpty()) {
			return "";
		}
		for(String suffix:suffixs ) {
			if(name.endsWith(suffix)) {
				return name.substring(0,name.length()-suffix.length());
			}
		}
		return name;
	}
}
