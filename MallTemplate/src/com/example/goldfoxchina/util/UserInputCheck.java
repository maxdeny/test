package com.example.goldfoxchina.util;

/**
 * 输入检测
 * @author kysl
 *
 */

public class UserInputCheck {
	
	/**
	 * 用户登录 注册检测
	 * @param string
	 * @return
	 */
	
	public static boolean checkCode(String string) {

		boolean flag = false;
		if (string.length() > 5 && string.length() <= 16) { // 6到16个字符
			flag = true;
		}
		return flag;
	}

}
