package com.example.demo.helper;


import java.time.ZoneOffset;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.text.ParseException;


public class Helper {
	
	public static Date parseDate(LocalDateTime localDateTime) throws ParseException{
//			Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
			Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);
			return date;
	}

}
