package weixinkeji.vip.jweb.scan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weixinkeji.vip.jweb.tools.JWPPathTool;

public class JWPScanClassFactory {
	private JWPScanClassFactory() {
	}

	public static List<Class<?>> getClassByFilePath(String path) {
		JWPScanClassFactory sf = new JWPScanClassFactory();
		// 项目根目录
		File root = new File(JWPPathTool.getMyProjectPath(""));
		// 项目根目录共长（+1是因为路径/也算进去）
		int index = root.getAbsolutePath().length() + 1;
		// 用户路径
		File f = null == path || path.isEmpty() ? root : new File(root, path.replace(".", "/"));
		// 文件扫描工具类
		JWPFindFileTool ft = new JWPFindFileTool();
		// 开始在指定路径下，进行递归扫描
		ft.findFile(f);
		// 将class类型的文件，加载到内存
		List<Class<?>> list = new ArrayList<>();
		Class<?> c;
		for (File file : ft.getFile()) {
			c = sf.fileToClass(file, index);
			if (null != c) {
//				System.out.println("扫描到的类：" + c.getName());
				list.add(c);
			}
		}
		sf = null;
		ft = null;
		return list;
	}

	public static List<Class<?>> getClassByFilePath(String... paths) {
		JWPScanClassFactory sf = new JWPScanClassFactory();
		// 项目根目录
		File root = new File(JWPPathTool.getMyProjectPath(""));
		// 项目根目录共长（+1是因为路径/也算进去）
		int index = root.getAbsolutePath().length() + 1;
		// 用户路径
		File f;
		JWPFindFileTool ft = new JWPFindFileTool();
		for (String path : paths) {
			f = null == path || path.isEmpty() ? root : new File(root, path.replace(".", "/"));
			// 文件扫描工具类
			// 开始在指定路径下，进行递归扫描
			ft.findFile(f);
		}
		// 将class类型的文件，加载到内存
		List<Class<?>> list = new ArrayList<>();
		Class<?> c;
		for (File file : ft.getFile()) {
			c = sf.fileToClass(file, index);
			if (null != c) {
//				System.out.println("扫描到的类：" + c.getName());
				list.add(c);
			}
		}
		return list;
	}

	/**
	 * 找到的文件，转java类
	 *
	 * @param f 文件
	 */
	private Class<?> fileToClass(File f, int index) {
		if (!f.getAbsolutePath().endsWith(".class")) {
			return null;
		}
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(f.getAbsolutePath().substring(index)
					.replace("\\", ".").replace("/", ".").replaceFirst(".class", ""));
		} catch (ClassNotFoundException ex) {
			System.err.println(f.getAbsolutePath());
			return null;
		}
	}
}
