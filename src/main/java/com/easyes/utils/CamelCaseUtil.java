package com.easyes.utils;

/**
 * @author wenzb
 **/
public class CamelCaseUtil {
	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串
	 *
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String convertToCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		if (name == null || name.isEmpty()) {
			return "";
		} else if (!name.contains("_")) {
			return name.toLowerCase();
		}
		String[] camels = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.substring(0, 1).toLowerCase() + result.substring(1, result.toString().length());
	}
}
