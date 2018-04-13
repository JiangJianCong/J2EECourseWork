package com.space.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * @author 
 *
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 格式化模糊查询
	 * @param str
	 * @return
	 */
	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
	
	/**
	 * 过滤掉集合里的空格
	 * @param list
	 * @return
	 */
	public static List<String> filterWhite(List<String> list){
		List<String> resultList=new ArrayList<String>();
		for(String l:list){
			if(isNotEmpty(l)){
				resultList.add(l);
			}
		}
		return resultList;
	}

	/**
	 * 自动生成机构识别码
	 * @return
	 */
	public static String createUDID(){
		String result = "";
		for (int i=0;i<3;i++){
			int random=(int) Math.round(Math.random()*25+65);
			char temp=(char) random;
			result = result + temp;
		}
		for (int i=0;i<4;i++){
			int temp = (int) Math.round(Math.random()*9);
			//System.out.println(temp);
			result = result + temp;
		}
		return result;
	}

	/**
	 * 自动生成课程识别码
	 * @return
	 */
	public static String createCourseID(){
		String result = "";
		for (int i=0;i<2;i++){
			int random=(int) Math.round(Math.random()*25+65);
			char temp=(char) random;
			result = result + temp;
		}
		for (int i=0;i<5;i++){
			int temp = (int) Math.round(Math.random()*9);
			//System.out.println(temp);
			result = result + temp;
		}
		return result;
	}

	/**
	 * 自动生成订单编号
	 * @return
	 */
	public static String createOrderId(){
		String result = "";
		for (int i=0;i<3;i++){
			int random=(int) Math.round(Math.random()*25+65);
			char temp=(char) random;
			result = result + temp;
		}
		for (int i=0;i<8;i++){
			int temp = (int) Math.round(Math.random()*9);
			//System.out.println(temp);
			result = result + temp;
		}
		return result;
//		return null;
	}

}
