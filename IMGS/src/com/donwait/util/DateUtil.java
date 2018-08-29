package com.donwait.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtil {
	public static String YYYY_MM_DD="yyyy-MM-dd";
	public static String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 格式化
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 取得yyyy-MM-dd HH:ss:mm格式数据
	 * @param date
	 * @return
	 */
	public static String getStringByDate(Date date,String pattern){
		/*SimpleDateFormat dateFormat=new SimpleDateFormat(pattern);
		return dateFormat.format(date);*/
		if(date!=null){
			return DateFormatUtils.format(date, pattern);
		}
		return null;
	}
	/**
	 * 比较两个时间之差,返回年
	 * @param now
	 * @param date
	 */
	@SuppressWarnings("deprecation")
	public static Integer minus(Date now, Date date) {
		// TODO Auto-generated method stub
		Integer result=now.getYear()-date.getYear();
		return result;
	};
	
	/**
	 * 两个时间相差天数
	 * @param s
	 * @param e
	 * @return
	 */	
	public static Integer dayDiff(Date s , Date e){
		long diff = e.getTime() - s.getTime();
		Integer days = (int) (diff/(1*24*60*60*1000));
		return days + 1;
	}
	
	/**
	 * 日期增加n天
	 * @param s
	 * @param e
	 * @return
	 */
	public static Date addDay(Date date, Integer gap){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, gap);
		return c.getTime();
	}
}
