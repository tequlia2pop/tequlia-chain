package com.gmail.tequlia2pop.chain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * 字符串工具类。
 * 
 * @author tequlia2pop
 */
public class StringUtil {

	public static void main(String[] args) {
		System.out.println(sha256Hex("hello java!"));

		System.out.println(getDificultyString(1));
		System.out.println(getDificultyString(2));
		System.out.println(getDificultyString(3));
	}

	/**
	 * 计算 SHA256 摘要，将值转换为十六进制字符串并返回。
	 * 
	 * @param input
	 * @return
	 */
	public static String sha256Hex(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

			// 将 byte 转换为有效值为8位的无符号 int，再将其通过 Integer.toHexString() 转换为2位十六进制值（必要时在高位补0）。
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用于将对象转换成 json 字符串的辅助方法。
	 * 
	 * @param o
	 * @return
	 */
	public static String getJson(Object o) {
		JsonbConfig jsonbConfig = new JsonbConfig();
		jsonbConfig.withFormatting(true).withNullValues(true);
		return JsonbBuilder.create(jsonbConfig).toJson(o);
	}

	/**
	 * 返回 difficulty 字符串目标，用于与 hash 进行比较。 
	 * 
	 * 实际上是创建一个用 difficulty * "0" 组成的字符串，
	 * 例如 difficulty 为5将返回 "00000"。
	 * 
	 * @param difficulty
	 * @return
	 */
	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}

}
