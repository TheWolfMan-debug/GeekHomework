package com.wolfman.travel.util;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}

	/**
	 * 产生一个随机数
	 * @return
	 */
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
