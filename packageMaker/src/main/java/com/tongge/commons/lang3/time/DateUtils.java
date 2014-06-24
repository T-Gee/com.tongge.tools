package com.tongge.commons.lang3.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateStr(String patrn){
		DateFormat df = new SimpleDateFormat(patrn);
		String dateStr = df.format(new Date());
		return dateStr;
	}
}
