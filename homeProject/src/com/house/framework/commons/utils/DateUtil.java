package com.house.framework.commons.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 *功能说明:日期辅助类
 *
 */
public class DateUtil {
	public final static int HOURS_PER_DAY				= 24;
	public final static int MINS_PER_HOUR				= 60; 
	public final static int SECS_PER_MIN 				= 60;
	public final static int MSECS_PER_SEC				= 1000;
	public final static int MINS_PER_DAY				= HOURS_PER_DAY * MINS_PER_HOUR;
	public final static int SECS_PER_DAY				= MINS_PER_DAY * SECS_PER_MIN;
	public final static int MSECS_PER_MIN				= MSECS_PER_SEC * SECS_PER_MIN;
	public final static int MSECS_PER_HOUR				= MINS_PER_HOUR * MSECS_PER_MIN;
	public final static int MSECS_PER_DAY				= SECS_PER_DAY * MSECS_PER_SEC;
	public final static int DAYS_PER_YEAR				= 365;
	public final static int DAYS_PER_LEAP_YEAR			= 366;
	public final static int DAYS_PER_WEEK				= 7;
	
	public final static int RECODE_LEAVE_FIELD_AS_IS 	= 65535;
	
	public final static String FORMAT_SHORT     		= "yyyy-MM-dd";
	public final static String FORMAT_LONG	     		= "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_FULL 				= "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String FORMAT_SHORT_CN 			= "yyyy年MM月dd";
	public final static String FORMAT_LONG_CN 			= "yyyy年MM月dd日  HH时mm分ss秒";
	public final static String FORMAT_FULL_CN 			= "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
	
	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay()
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month)
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	public static Date getYesterday(Date date){
		Date yesDate = new Date(date.getTime() - 24*60*60*1000);
		return yesDate;
	}
	
	public static Date getOldDayByNum(Date date, Long day){
		Date yesDate = new Date(date.getTime() - day*24*60*60*1000);
		return yesDate;
	}
	
	public static String getNowDateString(){
		Date date = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
        return sdf.format(date); 
	}
	
	public static Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
        return date;
	}
	public static String DateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str;
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			str = "";
		}
        return str;
	}
	public static String DateToString(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str;
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			str = "";
		}
        return str;
	}
	
	/**
	 * 
	 *功能说明:将java.sql.Timestamp转化为java.util.Date
	 *@param timestamp
	 *@return Date
	 */
	public static Date timeStampToDate(Timestamp timestamp){
		if(timestamp == null)
			return null;
		return new Date(timestamp.getTime());
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
    /**
     *计算时间差精确到秒
     */
    public static Long getDiffTime(String oldTime,String newTime){
    	  SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          long between = 0;
          try {
              Date begin = dfs.parse(oldTime);
              Date end = dfs.parse(newTime);
              between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
          } catch (Exception ex) {
              ex.printStackTrace();
          }
          
//          long day = between / (24 * 60 * 60 * 1000);
//          long hour = (between / (60 * 60 * 1000) - day * 24);
//          long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//          long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//          long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
//                  - min * 60 * 1000 - s * 1000);
       return between/1000;
    }
    
    public static Date addDate(Date date, int num) {
		if (null == date) {
			return date;
		}
		Calendar c = Calendar.getInstance();
        c.setTime(date);   //设置当前日期
        c.add(Calendar.DATE, num); //日期加1天
//     c.add(Calendar.DATE, -1); //日期减1天
        date = c.getTime();
        return date;
	}
    
    public static Date addDateOneDay(Date date) {
    	return addDate(date,1);
	}
    
    /**
	 * 返回天数差d1-d2
	 * @param
	 * @return
	 */
    public static long dateDiff(Date d1,Date d2){
    	long days = 0;
    	try{
    		long diff = d1.getTime() - d2.getTime();
            days = diff / (1000 * 60 * 60 * 24);
    	}catch(Exception e){
    		
    	}
    	return days;
    }
    
    /**
	 * 返回天数差d1-d2，只计算date差值
	 * @param
	 * @return
	 */
    public static long dateDiffByDate(Date d1,Date d2){
    	long days = 0;
    	try{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            String sd1 = sdf.format(d1);
            String sd2 = sdf.format(d2);
            days = dateDiff(parse(sd1,"yyyy-MM-dd"),parse(sd2,"yyyy-MM-dd"));
    	}catch(Exception e){
    		
    	}
    	return days;
    }
    
	/**
	 * java.util.Date转化成java.util.Calendar
	 * @param date
	 * @return
	 */
	public static Calendar dateToCal(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * 判断两个时间是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		return getYear(date1) == getYear(date2) && getMonth(date1) == getMonth(date2) &&
			   getDay(date1) == getDay(date2);
	}
	
	/**
	 * 判断两个时间的时、分、秒、毫秒是否相等
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameTime(Date date1, Date date2) {
		return getHour(date1) == getHour(date2) && getMinute(date1) == getMinute(date2) &&
			   getSecond(date1) == getSecond(date2) && getMilliSecond(date1) == getMilliSecond(date2);	
	}
	
	/**
	 * 判断两个时间是否是同一天且时、分、秒、毫秒都一样
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDateTime(Date date1, Date date2) {
		return isSameDate(date1, date2) && isSameTime(date1, date2);
	}
	
	/**
	 * 给定年份是否闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
	}
	
	/**
	 * 给定时间的年份是否闰年
	 * @param date
	 * @return
	 */
	public static boolean isInLeapYear(Date date) {
		return isLeapYear(getYear(date));
	}
	
	/**
	 * 获取日期中的某数值。如获取月份 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static int getInteger(Date date, int dateType) {
		if (date == null) return -1;
		
		return dateToCal(date).get(dateType);
	}
	
	/**
	 * 返回给定时间的年份部分
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}
	
	/**
	 * 返回给定时间的月份部分，一月返回0，二月返回1，以此类推
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH);
	}
	
	/**
	 * 返回给定时间的天
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 返回给定时间的小时部分
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 返回给定时间的分
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}
	
	/**
	 * 返回给定时间的秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}
	
	/**
	 * 返回给定时间的毫秒
	 * @param date
	 * @return
	 */
	public static int getMilliSecond(Date date) {
		return getInteger(date, Calendar.MILLISECOND);
	}
	
	/**
	 * 给定时间是否下午
	 * @param date
	 * @return
	 */
	public static boolean isPM(Date date) {
		return getHour(date) >= 12;
	}
	
	/**
	 * 给定的年月日是否合法的日期数值
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean isValidDate(int year, int month, int day) {
		return (year >= 1) && (year <= 9999) &&
			   (month >= 0) && (month <= 11) &&
			   (day >= 1) && (day <= daysInAMonth(year, month));
	}
	
	/**
	 * 返回给定年份有多少天
	 * @param year
	 * @return
	 */
	public static int daysInAYear(int year) {
		if (isLeapYear(year)) {
			return DAYS_PER_LEAP_YEAR;
		} else {
			return DAYS_PER_YEAR; 
		}
	}
	
	/**
	 * 返回给定年月有多少天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int daysInAMonth(int year, int month) {
        int[][] monthDays = {{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},  
                			 {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}};  

        if (isLeapYear(year)) {  
            return monthDays[1][month];  
        } else {  
            return monthDays[0][month];  
        }  
	}
	
	/**
	 * 给定年月日，将其转化为java.util.Date，注意：月份从0开始
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date encodeDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 将给定的时、分、秒、毫秒数值转化为时间，转化后的日期为当天
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	public static Date encodeTime(int hour, int minute, int second, int milliSecond) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, milliSecond);
		return cal.getTime();
	}
	
	/**
	 * 将给定的年月日时分秒毫秒数值转化为java.util.Date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	public static Date encodeDateTime(int year, int month, int day,
			int hour, int minute, int second, int milliSecond) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		cal.set(Calendar.MILLISECOND, milliSecond);
		return cal.getTime();
	}
	
	/**
	 * 将时、分、秒、毫秒都置为0 
	 * @param date
	 * @return
	 */
	public static Date startOfTheDay(Date date) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 返回给定日期最后一秒的值 
	 * @param date
	 * @return
	 */
	public static Date endOfTheDay(Date date) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 990);
		return cal.getTime(); 
	}
	
	/**
	 * 返回给定日期所处年份的第一天
	 * @param date
	 * @return
	 */
	public static Date startOfTheYear(Date date) {
		/*return encodeDate(getYear(date), 1, 1); date类型一月是0 modify by zb on 20200303*/
		return encodeDate(getYear(date), 0, 1);
	}
	
	/**
	 * 返回给定日期所处年份的最后一天
	 * @param date
	 * @return
	 */
	public static Date endOfTheYear(Date date) {
		/*return endOfTheDay(encodeDate(getYear(date), 12, 31)); date类型十二月是11 modify by zb on 20200303*/
		return endOfTheDay(encodeDate(getYear(date), 11, 31));
	}
	
	/**
	 * 返回给定年份的第一天
	 * @param year
	 * @return
	 */
	public static Date startOfAYear(int year) {
		return encodeDate(year, 1, 1);
	}
	
	/**
	 * 返回给定年份的最后一天
	 * @param year
	 * @return
	 */
	public static Date endOfAYear(int year) {
		return endOfTheDay(encodeDate(year, 12, 31));
	}
	
	/**
	 * 返回给定日期所在月份的第一天
	 * @param date
	 * @return
	 */
	public static Date startOfTheMonth(Date date) {
		return encodeDate(getYear(date), getMonth(date), 1);
	}
	
	/**
	 * 返回给定日期所在月份的最后一天
	 * @param date
	 * @return
	 */
	public static Date endOfTheMonth(Date date) {
		int year = getYear(date);
		int month = getMonth(date);
		return endOfTheDay(encodeDate(year, month, daysInAMonth(year, month)));
	}
	
	/**
	 * 返回给定年月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date startOfAMonth(int year, int month) {
		return encodeDate(year, month, 1);
	}
	
	/**
	 * 返回给定年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date endOfAMonth(int year, int month) {
		return endOfTheDay(encodeDate(year, month, daysInAMonth(year, month)));
	}
	
	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * @param date
	 * @param dateType
	 * @param num
	 * @return
	 */
	public static Date addInteger(Date date, int dateType, int num) {
		if (date == null) return null;
		
		Calendar cal = dateToCal(date);
		cal.add(dateType, num);
		return cal.getTime();
	}
	
	/**
	 * 返回给定时间向后推移指定天数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addDay(Date date, int numberOfDays) {
		return addInteger(date, Calendar.DATE, numberOfDays);
	}
	
	/**
	 * 返回给定时间向后推移1天后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addDay(Date date) {
		return addDay(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定周数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addWeek(Date date, int numberOfWeeks) {
		return addDay(date, numberOfWeeks * DAYS_PER_WEEK);
	}
	
	/**
	 * 返回给定时间向后推移1周后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addWeek(Date date) {
		return addWeek(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定月数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addMonth(Date date, int numberOfMonths) {
		return addInteger(date, Calendar.MONTH, numberOfMonths);
	} 
	
	/**
	 * 返回给定时间向后推移1月后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addMonth(Date date) {
		return addMonth(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定年数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addYear(Date date, int numberOfYears) {
		return addInteger(date, Calendar.YEAR, numberOfYears);
	}
	
	/**
	 * 返回给定时间向后推移1年后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addYear(Date date) {
		return addYear(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定小时数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addHour(Date date, int numberOfHours) {
		return addInteger(date, Calendar.HOUR_OF_DAY, numberOfHours);
	}
	
	/**
	 * 返回给定时间向后推移1小时后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addHour(Date date) {
		return addHour(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定分钟数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addMinute(Date date, int numberOfMinutes) {
		return addInteger(date, Calendar.MINUTE, numberOfMinutes);
	}
	
	/**
	 * 返回给定时间向后推移1分钟后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addMinute(Date date) {
		return addMinute(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定秒数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addSecond(Date date, int numberOfSeconds) {
		return addInteger(date, Calendar.SECOND, numberOfSeconds);
	}
	
	/**
	 * 返回给定时间向后推移1秒后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addSecond(Date date) {
		return addSecond(date, 1);
	}
	
	/**
	 * 返回给定时间向后推移指定毫秒数后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addMilliSecond(Date date, int numberOfMilliSeconds) {
		return addInteger(date, Calendar.MILLISECOND, numberOfMilliSeconds);
	}
	
	/**
	 * 返回给定时间向后推移1毫秒后的值
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addMilliSecond(Date date) {
		return addMilliSecond(date, 1);
	}
	
	/**
	 * 返回当前时间
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}
	
	/**
	 * 返回当前日期，时分秒毫秒都为0
	 * @return
	 */
	public static Date getToday() {
		return startOfTheDay(new Date());
	}
	
	/**
	 * 返回当前日期的前一天，时分秒毫秒都为0
	 * @return
	 */
	public static Date getTomorrow() {
		return startOfTheDay(addDay(new Date(), 1));
	} 
	
	/**
	 * 返回当前日期的后一天，时分秒毫秒都为0
	 * @return
	 */
	public static Date getYestoday() {
		return startOfTheDay(addDay(new Date(), -1));
	}
	
	/**
	 * 返回两个日期相隔几周（包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static double weekSpan(Date startDate, Date endDate) {
		return 1.0 * (endDate.getTime() - startDate.getTime()) / (MSECS_PER_DAY * DAYS_PER_WEEK);
	}
	
	/**
	 * 返回两个时间相隔几天（包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static double daySpan(Date startDate, Date endDate) {
		return 1.0 * (endDate.getTime() - startDate.getTime()) / MSECS_PER_DAY;
	}
	
	/**
	 * 返回两个时间相隔几小时（包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static double hourSpan(Date startDate, Date endDate) {
		return 1.0 * (endDate.getTime() - startDate.getTime()) / MSECS_PER_HOUR;
	}
	
	/**
	 * 返回两个时间相隔几分钟（包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static double minuteSpan(Date startDate, Date endDate) {
		return 1.0 * (endDate.getTime() - startDate.getTime()) / MSECS_PER_MIN;
	}
	
	/**
	 * 返回两个时间相隔几秒（包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static double secondSpan(Date startDate, Date endDate) {
		return 1.0 * (endDate.getTime() - startDate.getTime()) / MSECS_PER_SEC;
	}
	
	/**
	 * 返回两个时间相隔几周（不包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int weeksBetween(Date startDate, Date endDate) {
		return (int) weekSpan(startDate, endDate);
	}
	
	/**
	 * 返回两个时间相隔几天（不包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		return (int) daySpan(startDate, endDate);
	}
	
	/**
	 * 返回两个时间相隔几小时（不包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long hoursBetween(Date startDate, Date endDate) {
		return (long) hourSpan(startDate, endDate);
	}
	
	/**
	 * 返回两个时间相隔几分钟（不包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long minutesBetween(Date startDate, Date endDate) {
		return (long) minuteSpan(startDate, endDate);
	}
	
	/**
	 * 返回两个时间相隔几秒（不包含小数）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long secondsBetween(Date startDate, Date endDate) {
		return (long) secondSpan(startDate, endDate);
	}
	
	/**
	 * 返回两个时间相隔几毫秒
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long milliSecondsBetween(Date startDate, Date endDate) {
		return endDate.getTime() - startDate.getTime();
	}	
	
	/**
	 * 重置给定日期的年份
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date recodeYear(Date date, int year) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的月份
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date recodeMonth(Date date, int month) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date recodeDay(Date date, int day) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的小时
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date recodeHour(Date date, int hour) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的分钟
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date recodeMinute(Date date, int minute) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的秒
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date recodeSecond(Date date, int second) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的毫秒
	 * @param date
	 * @param milliSecond
	 * @return
	 */
	public static Date recodeMilliSecond(Date date, int milliSecond) {
		Calendar cal = dateToCal(date);
		cal.set(Calendar.MILLISECOND, milliSecond);
		return cal.getTime();
	}
	
	/**
	 * 重置给定日期的年月日
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date recodeDate(Date date, int year, int month, int day) {
		return recodeDateTime(date, year, month, day, 
				RECODE_LEAVE_FIELD_AS_IS, RECODE_LEAVE_FIELD_AS_IS, 
				RECODE_LEAVE_FIELD_AS_IS, RECODE_LEAVE_FIELD_AS_IS);
	}
	
	/**
	 * 重置给定日期的时分秒毫秒
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	public static Date recodeTime(Date date, int hour, int minute, int second, int milliSecond) {
		return recodeDateTime(date, RECODE_LEAVE_FIELD_AS_IS, RECODE_LEAVE_FIELD_AS_IS, RECODE_LEAVE_FIELD_AS_IS, 
				hour, minute, second, milliSecond);
	}
	
	/**
	 * 重置给定日期的年月日时分秒毫秒
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	public static Date recodeDateTime(Date date, int year, int month, int day,
			int hour, int minute, int second, int milliSecond) {
		Calendar cal = dateToCal(date);
		if (year != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.YEAR, year);
		if (month != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.MONTH, year);
		if (day != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.DAY_OF_MONTH, year);
		if (hour != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.HOUR_OF_DAY, year);
		if (minute != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.MINUTE, year);
		if (second != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.SECOND, year);
		if (milliSecond != RECODE_LEAVE_FIELD_AS_IS) cal.set(Calendar.MILLISECOND, year);
		return cal.getTime();
	}
	
	/**
	 * 返回默认的格式化字符串
	 * @return
	 */
	public static String getDefaultPattern() {
        return FORMAT_LONG;
    }
	
	/**
	 * 返回格式化后的时间
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
	}
	
	/**
	 * 根据默认的格式化字符串来格式化给定时间
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDefaultPattern());
	}
	
	/**
	 * 将给定字符串转化为时间
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * 根据默认的格式化字符串将指定字符串转化为时间
	 * @param strDate
	 * @return
	 */
	public static Date parse(String strDate) {
        return parse(strDate, getDefaultPattern());
    }
	
	/**
	 * 判断时间是否在某一个时间段里
	 * @param beginDate
	 * @param endDate
	 * @param date
	 * @return
	 */
	public static boolean isInDateRange(Date beginDate, Date endDate, Date date){
		if(beginDate.getTime()-date.getTime()<=0 && endDate.getTime()-date.getTime()>=0){
			return true;
		}
		return false;
	}
	/**
	 * 判断一个字符串是否是有效日期格式
	 * @param str
	 * @param pattern
	 * @param 
	 * @return
	 */
	public static boolean strIsValidDate(String str, String pattern) {
	    // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
    	     // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			 format.setLenient(false);
			 format.parse(str);
       	} catch (ParseException e) {
        	 return false;
       	} 
		return true;
	}
	/**
	 * 获取前n个月的第m天
	 * @author cjg
	 * @date 2019-5-13
	 * @param preNum
	 * @param day
	 * @return
	 */
	public static Date getPreMonth(int preNum,int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -preNum);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	/**
	 * 
	 * @author cjg
	 * @date 2020-1-3
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
        return cal.getTime();  
   }
}
