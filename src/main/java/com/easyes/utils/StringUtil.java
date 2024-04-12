package com.easyes.utils;


import java.util.regex.Pattern;

public class StringUtil {
	
	private StringUtil() {
	}
	
	
	/**
	 * 第一个字符转换为小写
	 *
	 * @param string
	 */
	public static String firstCharToLowerCase(String string) {
		char firstChar = string.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = string.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return string;
	}
	
	
	/**
	 * 第一个字符转换为大写
	 *
	 * @param string
	 */
	public static String firstCharToUpperCase(String string) {
		char firstChar = string.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = string.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return string;
	}
	
	
	/**
	 * 驼峰转下划线格式
	 *
	 * @param string
	 */
	public static String camelToUnderline(String string) {
		if (isBlank(string)) {
			return "";
		}
		int strLen = string.length();
		StringBuilder sb = new StringBuilder(strLen);
		for (int i = 0; i < strLen; i++) {
			char c = string.charAt(i);
			if (Character.isUpperCase(c) && i > 0) {
				sb.append('_');
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}
	
	/**
	 * 下划线转驼峰格式
	 *
	 * @param string
	 */
	public static String underlineToCamel(String string) {
		if (isBlank(string)) {
			return "";
		}
		if (Character.isUpperCase(string.charAt(0))) {
			string = string.toLowerCase();
		}
		int strLen = string.length();
		StringBuilder sb = new StringBuilder(strLen);
		for (int i = 0; i < strLen; i++) {
			char c = string.charAt(i);
			if (c == '_') {
				if (++i < strLen) {
					sb.append(Character.toUpperCase(string.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 删除字符串中的字符
	 */
	public static String deleteChar(String string, char deleteChar) {
		if (isBlank(string)) {
			return "";
		}
		char[] chars = string.toCharArray();
		StringBuilder sb = new StringBuilder(string.length());
		for (char aChar : chars) {
			if (aChar != deleteChar) {
				sb.append(aChar);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 字符串为 null 或者内部字符全部为 ' ', '\t', '\n', '\r' 这四类字符时返回 true
	 */
	public static boolean isBlank(String str) {
		if (str == null) {
			return true;
		}
		
		for (int i = 0, len = str.length(); i < len; i++) {
			if (str.charAt(i) > ' ') {
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean isAnyBlank(String... strings) {
		if (strings == null || strings.length == 0) {
			throw new IllegalArgumentException("args is empty.");
		}
		
		for (String str : strings) {
			if (isBlank(str)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	
	public static boolean areNotBlank(String... strings) {
		return !isAnyBlank();
	}
	
	
	/**
	 * 这个字符串是否是全是数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (isBlank(str)) {
			return false;
		}
		for (int i = str.length(); --i >= 0; ) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57) {
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean startsWithAny(String str, String... prefixes) {
		if (isBlank(str) || prefixes == null || prefixes.length == 0) {
			return false;
		}
		
		for (String prefix : prefixes) {
			if (str.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean endsWithAny(String str, String... suffixes) {
		if (isBlank(str) || suffixes == null || suffixes.length == 0) {
			return false;
		}
		
		for (String suffix : suffixes) {
			if (str.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static String trimOrNull(String string) {
		return string != null ? string.trim() : null;
	}
	
	
	/**
	 * 正则匹配
	 *
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean matches(String regex, String input) {
		if (null == regex || null == input) {
			return false;
		}
		return Pattern.matches(regex, input);
	}
	
	
	public static String buildSchemaWithTable(String schema, String tableName) {
		return isNotBlank(schema) ? schema + "." + tableName : tableName;
	}
	
	public static String[] getSchemaAndTableName(String tableNameWithSchema) {
		int index = tableNameWithSchema.indexOf(".");
		return index <= 0 ? new String[] {null, tableNameWithSchema.trim()}
				: new String[] {tableNameWithSchema.substring(0, index).trim(),
						tableNameWithSchema.substring(index + 1).trim()};
	}
	
	public static String[] getTableNameWithAlias(String tableNameWithAlias) {
		int index = tableNameWithAlias.indexOf(".");
		return index <= 0 ? new String[] {tableNameWithAlias, null}
				: new String[] {tableNameWithAlias.substring(0, index), tableNameWithAlias.substring(index + 1)};
	}
	
	public static String tryTrim(String string) {
		return string != null ? string.trim() : null;
	}
	
	public static String substringAfterLast(String text, String str) {
		if (text == null) {
			return null;
		}
		if (str == null) {
			return text;
		}
		return text.substring(text.lastIndexOf(str) + 1);
	}
	
	
}
