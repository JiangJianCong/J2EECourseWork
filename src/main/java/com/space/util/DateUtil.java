package com.space.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateUtil {

	/**
	 * 日期对象转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 字符串转日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}

	/**
	 * 计算出这个时间与现在的时间相差多久
	 * @param befDate
	 * @return
	 */
	public static long calculateDif(Date befDate){
		Date date = new Date();
		return (date.getTime()-befDate.getTime())/1000;

	}

	public static void main(String[] args) throws InterruptedException {

		String time = "2018-03-26";
		try {
			Date date = formatString(time, "yyyy-MM-dd");
//			System.out.println(formatDate(date,"yyyy-MM-dd"));

			int dayDif = (int) -(calculateDif(date)/(60*60*24));

			System.out.println(dayDif);


		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
