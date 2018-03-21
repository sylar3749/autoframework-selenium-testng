/**
 * Project Name:ZhongZhengPH2.0
 * File Name:Bytes.java
 * Package Name:com.zhongzheng.conmon
 * Date:2016年8月30日上午11:26:20
 * Copyright (c) 2016, otctop.com All Rights Reserved.
 */
package Framework;

/**
 * ClassName: Bytes <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年8月30日 上午11:26:20 <br/>
 *
 * @author think
 * @version
 * @since JDK 1.7
 */
public class Bytes {

	public static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		StringBuilder tgt = new StringBuilder();
		for (int i = start_idx; i <= end_idx; i++) {
			tgt.append((char) b[i]);
		}
		return tgt.toString();
	}
}
