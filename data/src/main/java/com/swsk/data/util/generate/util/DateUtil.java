package com.swsk.data.util.generate.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtil {
	/**默认的格式化方式*/
	private static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";
	/**
	 * Field value: Year
	 */
	public static final int YEAR = 1;

	/**
	 * Field value: Month
	 */
	public static final int MONTH = 2;

	/**
	 * Field value: Day
	 */
	public static final int DAY = 3;
	/**
	 * Field value: Week 周
	 */
	public static final int WEEK = 4;

	/**
	 * Field value: Hour
	 */
	public final static int HOUR = 10;

	/**
	 * Field value: Hour of Day
	 */
	public final static int HOUR_OF_DAY = 11;

	/**
	 * Field value: Minute
	 */
	public final static int MINUTE = 12;

	/**
	 * Field value: Second
	 */
	public final static int SECOND = 13;
	/**
	 * 获得本年第一天日期
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String getDateString(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static String fixTimestamp(String str) {
		if (str.indexOf(':') == -1) {
			return qualify(str) + " 00:00:00";
		} else {
			int i = str.indexOf(' ');
			return qualify(str.substring(0, i)) + str.substring(i);
		}
	}

	private static String qualify(String dateStr) {
		if (dateStr.length() == 10) {
			return dateStr;
		}
		String[] sec = dateStr.split("-");
		if (sec.length == 3) {
			StringBuilder buf = new StringBuilder(10);
			buf.append(sec[0]);
			buf.append("-");
			if (sec[1].length() == 1) {
				buf.append("0");
			}
			buf.append(sec[1]);
			buf.append("-");
			if (sec[2].length() == 1) {
				buf.append("0");
				buf.append(sec[2]);
			}
			return buf.toString();
		} else {
			return dateStr;
		}
	}

	public static String fixTime(String str) {
		if (str.indexOf(':') == -1) {
			return "00:00:00";
		}
		int b = str.indexOf(' '), e = str.indexOf('.');
		if (b == -1) {
			b = 0;
		}
		if (e == -1) {
			e = str.length();
		}
		return str.substring(b, e);
	}


	/**
	 * 根据日期获取当月总天数
	 * @param date
	 * @return
	 */
	public static int daysInMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据日期获取这个月的第几天
	 * @param date
	 * @return
	 */
	public static int dayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据日期获取日期中的年
	 * @param date
	 * @return
	 */
	public static int yearOf(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 根据日期获取这个年的第几天
	 * @param date
	 * @return
	 */
	public static int dayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 根据日期获取这个星期的第几天
	 * @param date
	 * @return
	 */
	public static int dayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取该日期的小时
	 * @param date
	 * @return
	 */
	public static int hourOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 根据日期获取该日期的字符
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		Timestamp t = new Timestamp(date.getTime());
		return t.toString();
	}

	/**
	 * 当前日期累加年
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date addYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/**
	 * 当前日期累加月
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date incMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 当前日期累加天数
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, long days) {
		return new Date(date.getTime() + 86400000 * days);
	}

	/**
	 * 当前日期累加小时
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHour(Date date, long hours) {
		return new Date(date.getTime() + 3600000 * hours);
	}

	/**
	 * 当前日期累加秒
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSecond(Date date, long seconds) {
		return new Date(date.getTime() + 1000 * seconds);
	}

	/**
	 * 对比两个日期是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Boolean isSameDay(Date date1,Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		String s1 = sdf.format(date1);
		String s2 = sdf.format(date2);
		if(s1.equals(s2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前日期属于该年的第几周
	 * @param dateStr
	 * @return
	 */
	public static int getWeekNumOfYear(String dateStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);

		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	private static String getFormatDate(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取当前日期
	 * @return yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		String format="yyyy-MM-dd";
		return getFormatDate(new Date(),format);
	}

	/**
	 * 获取当前日期时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime() {
		return getFormatDate(new Date(),defaultFormat);
	}

	/**
	 * 字符串转换成日期
	 * @param d1
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String d1,String format){
		Date d2 = null  ;
		try {
			DateFormat df = new SimpleDateFormat (format.trim());
			d2 = df.parse(d1.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d2;
	}

	public static String TimeDifference(String FirstDate,String secondDate){
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String TimeDifference = "";
		try
		{
			//后的时间
			Date d1 = df.parse(FirstDate);
			//前的时间
			Date d2 = df.parse(secondDate);
			//两时间差，精确到毫秒
			Long diff = d1.getTime() - d2.getTime();

			//以天数为单位取整
			Long day = diff / (1000 * 60 * 60 * 24);
			//以小时为单位取整
			Long hour=(diff/(60*60*1000)-day*24);
			//以分钟为单位取整
			Long min=((diff/(60*1000))-day*24*60-hour*60);
			Long second=(diff/1000-day*24*60*60-hour*60*60-min*60);



			System.out.println("---diff的值---->" +diff);
			System.out.println("---days的值---->" +day);
			System.out.println("---hour的值---->" +hour);
			System.out.println("---min的值---->"  +min);
			System.out.println("---second的值---->"  +second);

			System.out.println("---两时间差---> " +day+"天"+hour+"小时"+min+"分"+second+"秒");

			TimeDifference =  hour+":"+min+":"+second;
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return TimeDifference;


	}

	/**
	 * Add value on special field of date
	 *
	 * @param iField
	 *            Field which need add value
	 * @param iValue
	 *            Value which will be added
	 * @param date
	 *            Basic date
	 * @return New date
	 */
	public static Date dateAdd(int iField, int iValue, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (iField) {
			case DateUtil.YEAR:
				cal.add(Calendar.YEAR, iValue);
				break;
			case DateUtil.MONTH:
				cal.add(Calendar.MONTH, iValue);
				break;
			case DateUtil.DAY:
				cal.add(Calendar.DATE, iValue);
				break;
			case DateUtil.HOUR:
				cal.add(Calendar.HOUR, iValue);
				break;
			case DateUtil.HOUR_OF_DAY:
				cal.add(Calendar.HOUR_OF_DAY, iValue);
				break;
			case DateUtil.MINUTE:
				cal.add(Calendar.MINUTE, iValue);
				break;
			case DateUtil.SECOND:
				cal.add(Calendar.SECOND, iValue);
				break;
			case DateUtil.WEEK:
				cal.add(Calendar.DATE, iValue*7);
				break;
			default:
				break;
		}
		return cal.getTime();
	}

	/**
	 * 根据星期获取当前周的所在日期
	 * @param xingqi 星期：1，2，3，4，5，6，7
	 * @return
	 */
	public static String getWeekOfXQ(int xingqi){
		if(xingqi<1 || xingqi>7){
			return "";
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_WEEK, (2-cal.get(Calendar.DAY_OF_WEEK))%7);
		List list = new ArrayList();
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<7;i++){
			list.add(d.format(cal.getTime()));
			cal.roll(Calendar.DAY_OF_YEAR,true);
		}
		return list.get(xingqi-1).toString();
	}

	/**
	 * 获取两个日期相差的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate,Date bdate) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		smdate=sdf.parse(sdf.format(smdate));
		bdate=sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int betweenHour(Date first, Date second) {
		long var2 = first.getTime() - second.getTime();
		long var4 = var2 / 3600000L;
		return Integer.parseInt(String.valueOf(var4));
	}

	public static int betweenMinute(Date first, Date second) {
		long var2 = first.getTime() - second.getTime();
		long var4 = var2 / 60000L;
		return Integer.parseInt(String.valueOf(var4));
	}

	public static int betweenSecond(Date first, Date second) {
		long var2 = first.getTime() - second.getTime();
		long var4 = var2 / 1000L;
		return Integer.parseInt(String.valueOf(var4));
	}

	public static int betweenMillisecond(Date first, Date second) {
		long var2 = first.getTime() - second.getTime();
		return Integer.parseInt(String.valueOf(var2));
	}


	/**
	 * 获取两个日期间隔的天数
	 * @param fDate
	 * @param oDate
	 * @return
	 * @throws ParseException
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = (fDate.getTime() - oDate.getTime())/1000;
		return (int) (intervalMilli / (24 * 60 * 60));

	}


	public static void main(String[] args) throws ParseException {
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH,12-1);
		try {
			System.out.println(daysBetween(stringToDate("2016-05-03", "yyyy-MM-dd"),stringToDate("2016-05-05", "yyyy-MM-dd")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//System.out.println(daysInMonth(new Date()));
		System.out.println(DateUtil.getIntervalDays(DateUtil.stringToDate("2020-01-20 00:00:00","yyyy-MM-dd HH:mm:ss"),new Date()));

		String s = "123,str_qwe,qweawer3-AWER";
		System.out.println(s.replaceAll(",","=?,"));
	}

}
