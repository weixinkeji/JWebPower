package weixinkeji.vip.jweb.scan;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JWPFindFileTool {

	private Set<File> list = new HashSet<>();

	void findFile(File file) {

		if (file.isFile()) {
			list.add(file);
		}
		File[] fs = file.listFiles();
		if (null != fs) {
			for (File f : fs) {
				findFile(f);
			}
		}
	}
	
	Set<File> getFile() {
		return this.list;
	}
	
/*	public static void main(String args[]) {
		File f = new File("D:\\迅雷下载");
		FindFileTool ft = new FindFileTool();
		ft.findFile(f);
	}*/
}
