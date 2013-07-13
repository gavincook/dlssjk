package com.greejoy.dlssjk.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-5-26
 */
public class Ping {

	public static boolean ping(String target){
		try{
			Process pro = Runtime.getRuntime().exec("ping "+target);
	        InputStream in =pro.getInputStream();
	        pro.waitFor();
	        byte[] data = new byte[1024];
	        int length = in.read(data);
	        String respone = new String(data,0,length,"GBK");
	        return respone.contains("数据包: 已发送 = 4，已接收 = 4，丢失 = 0");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
