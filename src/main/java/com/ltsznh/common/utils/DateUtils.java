package com.ltsznh.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 *
 * @author liutao
 * @email ltsznh@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {

    protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss.SSS)
     */
    public final static String DATE_TIME_PATTERN_WITH = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static String format(DateTime date) {
        try {
            return date.toString(DATE_PATTERN);
        } catch (Exception e) {
            return date.toString(DATE_TIME_PATTERN);
        }
    }

    public static String format(DateTime date, String pattern) {
        if (StringUtils.isNotBlank(pattern)) {
            return date.toString(pattern);
        }
        return date.toString(DATE_TIME_PATTERN);
    }

    public static DateTime format(String dateString) {
        try {
            if (StringUtils.isNotBlank(dateString)) {
                DateTime dateTime = new DateTime(dateString);
                return dateTime;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e.getCause());
            DateTime dateTime = DateTime.parse(dateString, DateTimeFormat.forPattern(DATE_TIME_PATTERN));

            return dateTime;
        }

        return null;
    }
}
