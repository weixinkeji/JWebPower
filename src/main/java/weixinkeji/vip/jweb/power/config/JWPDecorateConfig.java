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
	
	default String substringSuffix(String name,String suffix) {
		if(null==name||name.isEmpty()) {
			return "";
		}
		if(name.endsWith(suffix)) {
			return name.substring(0,name.length()-suffix.length());
		}
		return name;
	}
}
