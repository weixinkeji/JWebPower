package weixinkeji.vip.jweb.tools;

import java.util.ArrayList;
import java.util.List;

public class ControllePrint {

	private static List<ConsoleVO> consoleMsg = new ArrayList<>();

	/**
	 * 控制台打印一条 错误信息
	 * 
	 * @param msg
	 */
	public static void addErrorMessage(String msg) {
		consoleMsg.add(new ConsoleVO(-1, msg));
	}

	/**
	 * 控制台打印一条 错误信息
	 * 
	 * @param msg
	 * @param tabCount 在信息前加入多少个 空格
	 */
	public static void addErrorMessage(String msg, int tabCount) {
		consoleMsg.add(new ConsoleVO(-1, msg, tabCount));
	}

	/**
	 * 控制台打印一条 信息
	 * 
	 * @param msg
	 */
	public static void addMessage(String msg) {
		consoleMsg.add(new ConsoleVO(1, msg));
	}

	/**
	 * 控制台打印一条 信息
	 * 
	 * @param msg      String 信息
	 * @param tabCount 在信息前加入多少个 空格
	 */
	public static void addMessage(String msg, int tabCount) {
		consoleMsg.add(new ConsoleVO(1, msg, tabCount));
	}
	/**
	 * 打印信息
	 */
	public static void printMessage() {
		StringBuilder sb = new StringBuilder();
		for (ConsoleVO vo : consoleMsg) {
			switch(vo.sort) {
			case -1:
				System.out.print(sb.toString());
				sb.setLength(0);
				System.err.println(vo.getMsg());
				continue;
			case 1:
				sb.append(vo.getMsg());
			}
		}
		if(sb.length()>0) {
			System.out.print(sb.toString());
		}
	}
	/**
	 * 清空缓冲区的信息，并设置对象值为null
	 */
	public static void clearMessage() {
		consoleMsg.clear();
		consoleMsg=null;
	}
}

class ConsoleVO {
	public final int sort;
	private String msg;
	private int tabCount = 0;

	private String formatMsg(String text) {
		String tab = " ";
		for (int i = 0; i < this.tabCount; i++) {
			tab += tab;
		}
		return tab + text + "\n";
	}
	/**
	 * 信息
	 * @return String
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 构造方法 
	 * @param i 类型
	 * @param msg 信息
	 */
	public ConsoleVO(int i, String msg) {
		this.sort = i;
		this.msg = formatMsg(msg);
	}
	/**
	 * 构造方法 
	 * @param i 类型
	 * @param msg 信息
	 * @param tabCount 空格
	 */
	public ConsoleVO(int i, String msg, int tabCount) {
		this.sort = i;
		this.tabCount = tabCount;
		this.msg = formatMsg(msg);
	}
}
