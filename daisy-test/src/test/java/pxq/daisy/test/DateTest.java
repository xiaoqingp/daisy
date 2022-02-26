package pxq.daisy.test;

import java.text.ParseException;
import java.util.Date;

import cn.hutool.core.date.format.FastDateFormat;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		Date maxDate = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
		.parse("9999-12-31 23:59:59");
		System.out.println(maxDate.getTime());
		//253402271999000
		//253402271999
	}
}
