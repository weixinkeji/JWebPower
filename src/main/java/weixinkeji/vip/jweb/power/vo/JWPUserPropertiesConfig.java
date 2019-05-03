package weixinkeji.vip.jweb.power.vo;

public class JWPUserPropertiesConfig {
	
//	静态资源前缀
	private String static_resources_prefix="/static/";
//	扫描的目录 
	private String scan_package[];
	
	
	public String getStatic_resources_prefix() {
		return static_resources_prefix;
	}
	public void setStatic_resources_prefix(String static_resources_prefix) {
		this.static_resources_prefix = static_resources_prefix;
	}
	public String[] getScan_package() {
		return scan_package;
	}
	public void setScan_package(String[] scan_package) {
		this.scan_package = scan_package;
	}
	
	
}
