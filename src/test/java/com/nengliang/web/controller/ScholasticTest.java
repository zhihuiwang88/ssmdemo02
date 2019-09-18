package com.nengliang.web.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * 测试时所有的接口
 * @author Dell
 *@Data 20190612
 */

public class ScholasticTest {

     //@Test
	public  void testSelectPerson(){
    	 Date date = new Date();
    	 SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String modifytime = dateFormat.format(new Date());
		//获得系统时间
		 Date date1 = new Date();
		 String nowTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date1);
		 //将时间格式转换成符合Timestamp要求的格式.
		 Timestamp goodsC_date =Timestamp.valueOf(nowTime);
		 System.out.println(nowTime);
		 // 返回结果 2019-04-19 11:04:52.0
		 System.out.println(goodsC_date);
		 
    }
	
	   @Test
		public  void tesPerson1(){
		
		  
		       
		    
		
		
		
	 }

	 
	 // 最后一个
}
