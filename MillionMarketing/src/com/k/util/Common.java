/**
 * 
 */
package com.k.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class Common {
	String datatime;
	public String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		datatime=df.format(new Date());
		System.out.println("getDate()输出:"+datatime);
		return datatime;
	}
}
