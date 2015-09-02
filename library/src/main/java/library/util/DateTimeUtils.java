package library.util;

/**
 * ClassName: DateTimeUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:52:44 <br/>
 * 读取日期时间类型
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public final class DateTimeUtils extends StringUtils {
	/**
	 * 获取完整的日期时间类型
	 * 
	 * @return
	 */
	@android.annotation.SuppressLint("SimpleDateFormat")
	public static String getLongDateTime(boolean hasFormat) {
		String format = "yyyy-MM-dd HH:mm:ss";
		if (!hasFormat) {
			format = "yyyyMMddHHmmss";
		}
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return date;
	}

	@android.annotation.SuppressLint("SimpleDateFormat")
	public static String getLongDateTime00(boolean hasFormat) {
		String format = "yyyy-MM-dd HH:mm:ss";
		if (!hasFormat) {
			format = "yyyyMMddHHmmss";
		}
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return (hasFormat ? date.substring(0, 10) + " 00:00:00" : date.substring(0, 8) + " 000000");
	}

	@android.annotation.SuppressLint("SimpleDateFormat")
	public static String getSimpleDateTime(boolean hasFormat) {
		String format = "yyyy-MM-dd";
		if (!hasFormat) {
			format = "yyyyMMdd";
		}
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return date;
	}

	@android.annotation.SuppressLint("SimpleDateFormat")
	public static String getPutDateTime(String date) {
		android.util.Log.i("getPutDateTime", "date:" + date);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat("MM月dd日 HH:mm");
		String dates = "";
		try {
			dates = sDateFormat.format(df.parse(date));
			android.util.Log.i("getPutDateTime", "dates:" + dates);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}

	@android.annotation.SuppressLint("SimpleDateFormat")
	public static String getWeiBoDateTime(String date) {
		android.util.Log.i("getPutDateTime", "date:" + date);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat("MM-dd HH:mm");
		String dates = "";
		try {
			dates = sDateFormat.format(df.parse(date));
			android.util.Log.i("getPutDateTime", "dates:" + dates);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}

	/*
	 * 从时间中获取日期 date "yyyy-MM-dd HH:mm:ss" 返回 "yyyy-MM-dd"
	 */
	@android.annotation.SuppressLint("SimpleDateFormat")
	public static String getDateFromDatetime(String date) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dates = "";
		try {
			dates = sDateFormat.format(df.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}

	@android.annotation.SuppressLint("SimpleDateFormat")
	public static long getDiffer(String time) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = 0;
		try {
			String today = getLongDateTime(true);
			java.util.Date now = df.parse(today);
			java.util.Date date = df.parse(time);
			l = now.getTime() - date.getTime();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return l;
	}

	@android.annotation.SuppressLint("SimpleDateFormat")
	public static long getDiffer00(String time) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = 0;
		try {
			String today = getLongDateTime00(true);
			if (null != time && time.length() > 0) {
				time = time.substring(0, 10) + " 00:00:00";
			}
			java.util.Date now = df.parse(today);
			java.util.Date date = df.parse(time);
			l = now.getTime() - date.getTime();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 
	 * getPutDate:(下拉刷新显示). <br/>
	 * 
	 * @author chenhao
	 * @param date
	 * @return
	 * @since JDK 1.6
	 */
	public static String getPutDate(String date) {
		if (date == null || date.length() == 0) {
			return "";
		}
		long l = library.util.DateTimeUtils.getDiffer(date);
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		if (day > 0) {
			return day + "天前";
		} else {
			if (hour > 0) {
				return hour + "小时前";
			} else {
				if (min > 0) {
					return min + "分钟前";
				} else {
					return "1分钟前";
				}
			}
		}
	}

	/**
	 * 时间格式转化
	 * 
	 * @param date
	 *            完整日期格式
	 * @return
	 */
	public static String parseDateTime(String date) {
		java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat("MM月dd日 HH:mm");
		String dates = "";
		@SuppressWarnings("deprecation")
		java.util.Date d = new java.util.Date(date);
		dates = sDateFormat.format(d);
		return dates;
	}

}
