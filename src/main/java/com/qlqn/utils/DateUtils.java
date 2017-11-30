package com.qlqn.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_PAR = "yyyy-MM-dd";

	public static final SimpleDateFormat formatAll = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat formatDate = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat formatDate_1 = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * 将短时间格式字符串yyyy-MM-dd转换为时间
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatDate.parse(strDate, pos);
		return strtodate;
	}
	/**
	 * 显示年月日 格式yyyy-MM-dd
	 * 
	 * @param dateStr时间字符串
	 *            2011-08-26 修改人：韩建新 修改内容：增加dateStr为空值处理
	 * @return
	 */
	public static String getDateFromString(Date date) {
		return formatDate.format(date);
	}
	// 得到一个时间延后或前移几天的时间
	public static String getCountedDay(String strDate, Integer delay) {
		try {
			String mdate = "";
			Date d = strToDate(strDate);
			long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = formatDate.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}
	// 得到 当前时间 前几天
	public static Date getNowDayBefore( Integer delay) {
		try {
			Date dNow = new Date();   //当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(dNow);//把当前时间赋给日历

			calendar.add(Calendar.DAY_OF_MONTH, -delay);  //设置为前一天
			dBefore = calendar.getTime();   //得到前一天的时间
			return dBefore;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 判断两个日期之间差了多少天，不足一天，则按一天计算，即20.01天也算21天
	 */
	public static int dateDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		long baseNum = 3600 * 1000 * 24;
		long absNum = Math.abs(date1.getTime() - date2.getTime());
		long mod = absNum % baseNum;
		int num = (int) (absNum / baseNum);
		if (mod > 0)
			num++;
		return num;
	}
	
	/**
	 * 判断两个日期之间差了多少天，不足一天，则按一天计算，即20.01天也算21天
	 */
	public static int dateDiff1(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		long baseNum = 3600 * 1000 * 24;
		long absNum = date1.getTime() - date2.getTime();
		long mod = absNum % baseNum;
		int num = (int) (absNum / baseNum);
		if (mod > 0)
			num++;
		return num;
	}

	/**
	 * 设置两个日期相差几个月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthDeffDate(String date1, String date2) throws ParseException {
		if(date1.trim().length()==10){
			date1=date1+" 00:00:00";
		}
		if(date2.trim().length()==10){
			date2=date2+" 00:00:00";
		}
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(formatAll.parse(date1));
		aft.setTime(formatAll.parse(date2));
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		int day = (aft.get(Calendar.DAY_OF_MONTH) - bef
				.get(Calendar.DAY_OF_MONTH));
		day = day > 0 ? 1 : 0;
		return month + result + day;

	}

	/**
	 * 得到一个时间延后或前移几月的时间
	 * 
	 * @param strDate
	 * @param delay
	 * @return
	 */
	public static String getCountedMonth(String strDate, Integer delay) {
		try {
			Date dd = strToDate(strDate);
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(dd);
			currentDate.add(Calendar.MONDAY, delay);
			return formatDate.format(currentDate.getTime());
		} catch (Exception e) {
			return "";
		}
	}
	
	
	/**
	 * 得到一个时间延后或前移几月的时间
	 * 
	 * @param strDate
	 * @param delay
	 * @return
	 */
	public static String getCountedMonthAll(String strDate, Integer delay) {
		try {
			Date dd = strToDate(strDate);
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(dd);
			currentDate.add(Calendar.MONDAY, delay);
			return formatAll.format(currentDate.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	

	/**
	 * 日期转毫秒
	 * 
	 * @param expireDate
	 * @return
	 */
	public static Long getSecondsFromDateTime(String expireDate) {
		if (expireDate == null || expireDate.trim().equals(""))
			return 0l;
		Date date = null;
		try {
			date = formatAll.parse(expireDate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0l;
		}
	}

	/**
	 * 日期转毫秒
	 * 
	 * @param expireDate
	 * @return
	 */
	public static Long getSecondsFromDate(String expireDate) {
		if (expireDate == null || expireDate.trim().equals(""))
			return 0l;
		Date date = null;
		try {
			date = formatDate.parse(expireDate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0l;
		}
	}
	
	
	public static String formatDate(String date) throws ParseException {
		return formatAll.format(formatAll.parse(date));
	}
	public static String formatDate1(String date) throws ParseException {
		return formatDate.format(formatDate.parse(date));
	}
	public static String formatDate(Date date) throws ParseException {
		return formatAll.format(date);
	}
	public static String formatDateStr(Date date) throws ParseException {
		return formatDate.format(date);
	}
	public static Date parseStr2Date(String date) throws ParseException {
		return formatAll.parse(date);
	}
	
	public static Date parseStr2Date1(String date) throws ParseException {
		return formatDate.parse(date);
	}
	
	
	public static String cidGetDate(String cid) throws ParseException {
		String bd = cid.substring(6, 14);
		return formatDate.format((formatDate_1.parse(bd)));
	}

	/**
	 * 根据天数返回失效时间的总计秒数，不传day默认一天
	 * 比如当前时间为23:59:00 传入参数1天，返回60秒
	 * @param day
	 * @return
	 */
	public static Integer getSmsFailSecond(Integer day){
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_PAR);
		String curDateStr = sf.format(new Date());
		Integer failSecond = 24 * 60 * 60;
		if(day == null){
			day = 1;
		}
		try {
			Date todayDate = sf.parse(curDateStr);
			long todayMilliSecond = todayDate.getTime();
			long curMilliSecond = System.currentTimeMillis();
			long afterMilliSecond = todayMilliSecond + (day * 24 * 60 * 60 * 1000);
//			System.out.println("今天开始的毫秒数：" + todayMilliSecond);
//			System.out.println( day + "天后开始的毫秒数：" + afterMilliSecond);
//			System.out.println("今天当前的毫秒数：" + curMilliSecond);
//			System.out.println("相差毫秒数：" + (afterMilliSecond - curMilliSecond)/1000);
//			System.out.println("相差毫秒数：" + (afterMilliSecond - todayMilliSecond));
			failSecond = (int) (afterMilliSecond - curMilliSecond)/1000;
			return (Integer)failSecond;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return failSecond;
	}
	
	/**
	 * 两个时间小时差
	 * @param start  开始时间
	 * @param end  结束时间
	 * @return
	 * @throws ParseException 
	 */
	public static int getDifferHours(String start,String end) throws ParseException{
		SimpleDateFormat simpleFormat = new SimpleDateFormat(DATE_FORMAT_STR);
		long from = simpleFormat.parse(start).getTime();  
		long to = simpleFormat.parse(end).getTime();  
		int hours = (int) ((to - from)/(1000 * 60 * 60));
		return hours;
	}
	public static void main(String[] args) throws ParseException {
//		  System.out.println(DateUtils.dateDiff(new Date(), new Date()));
		 // System.out.println(DateUtils.getDifferHours("","2017-08-25 17:18:10"));


		System.out.println(DateUtils.getMonthDeffDate("2017-11-07","2018-11-07"));
	}

}
