package com.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;
@Slf4j
public class DateUtils {

    public final static String MILLISECOND = "MS";
    public final static String SECOND      = "S";
    public final static String MINUTE      = "MIN";
    public final static String HOUR        = "H";
    public final static String DAY         = "D";
    public final static String MONTH       = "M";
    public final static String YEAR        = "Y";

    public static Date add(Date date, String s) throws RuntimeException {

        if (null == date || StringUtils.isEmpty(s)) {
            return date;
        }
        int field = 0;
        if (s.endsWith(MILLISECOND)) {
            field = Calendar.MILLISECOND;
            s = s.substring(0, s.indexOf(MILLISECOND));
        } else if (s.endsWith(SECOND)) {
            field = Calendar.SECOND;
            s = s.substring(0, s.indexOf(SECOND));
        } else if (s.endsWith(MINUTE)) {
            field = Calendar.MINUTE;
            s = s.substring(0, s.indexOf(MINUTE));

        } else if (s.endsWith(HOUR)) {
            field = Calendar.HOUR;
            s = s.substring(0, s.indexOf(HOUR));
        } else if (s.endsWith(DAY)) {
            field = Calendar.DAY_OF_YEAR;
            s = s.substring(0, s.indexOf(DAY));
        } else if (s.endsWith(MONTH)) {
            field = Calendar.MONTH;
            s = s.substring(0, s.indexOf(MONTH));
        } else if (s.endsWith(YEAR)) {
            field = Calendar.YEAR;
            s = s.substring(0, s.indexOf(YEAR));
        } else {
            throw new RuntimeException("s is valid");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, Integer.parseInt(s));
        return calendar.getTime();

    }

    /**
     * 根据日期和格式，返回日期文本
     *
     * @param date
     * @param pattern
     * @return
     * @throws RuntimeException
     */
    public static final String format(Date date, DatePatternEnum pattern) throws RuntimeException {
        if(pattern == null) {
            pattern = DatePatternEnum.DEFAULT_PATTERN;
        }
        return format(date,pattern.getPattern());
    }

    public static final String format(Date date, String pattern) throws RuntimeException {
        if (date == null) {
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 返回默认格式的日期字符串， yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @throws RuntimeException
     */
    public static final String format(Date date) throws RuntimeException {
        if (date == null) {
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(DatePatternEnum.DEFAULT_PATTERN.getPattern());
        return df.format(date);
    }

    public static final String format(Long timestamp) throws RuntimeException {
        if (timestamp == null) {
            return "";
        }

        Date date = new Date(timestamp);
        return format(date);
    }

    public static final Date parse(String dateStr) {

        return parse(dateStr, DatePatternEnum.DEFAULT_PATTERN);
    }

    /**
     * 解析字符串为Date
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return
     */
    public static final Date parse(String dateStr, DatePatternEnum pattern) {
        if(pattern == null) {
            pattern = DatePatternEnum.DEFAULT_PATTERN;
        }
        return parse(dateStr, pattern.getPattern());
    }

    public static final Date parse(String dateStr, String pattern) {
        if (dateStr == null || "".equals(dateStr.trim())) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            log.info(null,e);
        }
        return null;
    }

    /**
     * 获取输入日期 N天前或N天后的Date日期
     *
     * @param date
     * @param day
     * @return
     */
    public static final Date getDateAfterDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);

        return calendar.getTime();
    }

    /**
     * 获取输入日期 N hour 前或N hour 后的Date日期
     *
     * @param date
     * @param hour
     * @return
     */
    public static final Date getDateAfterHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static final Date getFirstDayOfMonthWithFirstDayConsider() {

        return getFirstDayOfMonthWithFirstDayConsider(new Date());
    }

    public static Date getFirstDayOfMonthWithFirstDayConsider(Date baseDate) {
        Date trgtDate = baseDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(trgtDate);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        if (date < 5) {
            trgtDate = getDateAfterDay(trgtDate, (1 + (-1) * date));
        }
        return getFirstDayOfMonth(trgtDate);
    }

    public static final Date getFirstDayOfMonth() {

        return getFirstDayOfMonth(new Date());
    }

    public static final Date getFirstDayOfMonth(Date trgtDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(trgtDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return date;
    }

    public static final Date getFirstDayOfWeekWithFirstDayConsider() {

        return getFirstDayOfWeekWithFirstDayConsider(new Date());
    }

    public static final Date getFirstDayOfWeekWithFirstDayConsider(Date trgtDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(trgtDate);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            trgtDate = getDateAfterDay(trgtDate, -1);
        }
        return getFirstDayOfWeek(trgtDate);
    }

    public static final Date getFirstDayOfWeek() {

        return getFirstDayOfWeek(new Date());
    }

    public static Integer getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    public static final Date getFirstDayOfWeek(Date trgtDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(trgtDate);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    public static Date getTimestampAtStartOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static final Long getTimestampAtStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero.getTime();
    }

    public static Date getTimestampAtEndOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static final Long getTimestampAtEndOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date zero = calendar.getTime();
        return zero.getTime();
    }

    public static final Date convertLongToDate(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar.getTime();
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @return
     */
    public static String Date2TimeStamp(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DatePatternEnum.DEFAULT_PATTERN.getPattern());
            return valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            log.info(null, e);
        }
        return "";
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @return
     */
    public static Long DateToTimeStamp(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DatePatternEnum.YYYY_MM_DD.getPattern());
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            log.info(null, e);
        }
        return 0L;
    }

    /**
     * 计算date2比date1多的小时
     *
     * @param date1
     * @param date2
     * @return
     */
    public static double differentHours(Date date1, Date date2) {
        double time = (date2.getTime() - date1.getTime());
        BigDecimal b = BigDecimal.valueOf(time / (1000 * 60 * 60));
        double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 计算date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        int result = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return result;
    }

    public static boolean validFormat(String str, DatePatternEnum pattern) {

        Date d = parse(str, pattern);
        String str2 = format(d, pattern);
        return StringUtils.equals(str, str2);
    }

    public static boolean validFormat(String str, String pattern) {

        Date d = parse(str, pattern);
        String str2 = format(d, pattern);
        return StringUtils.equals(str, str2);
    }

    public static String convertStartDate(String startDate) {

        Date d = DateUtils.parse(startDate, DateUtils.DatePatternEnum.YYYYMMDD);
        d = DateUtils.getTimestampAtStartOfDate(d);
        return DateUtils.format(d);

    }

    public static String convertEndDate(String endDate) {
        Date d = DateUtils.parse(endDate, DateUtils.DatePatternEnum.YYYYMMDD);
        d = DateUtils.getTimestampAtEndOfDate(d);
        return DateUtils.format(d);
    }

    /**
     * 计算两日期内相关天数,不足一天按一天算
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static int ceilDaysBetween(Date endDate, Date startDate) {
        long endMilliSecs = endDate.getTime();
        long startMilliSecs = startDate.getTime();
        long oneDayMilliSecs = 60 * 60 * 24 * 1000;
        long differMilliSecs = endMilliSecs - startMilliSecs;
        float division = (float) differMilliSecs / oneDayMilliSecs;
        return (int) Math.ceil(division);
    }

    public static enum DatePatternEnum {
        DEFAULT_PATTERN("yyyy-MM-dd HH:mm:ss"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYYMMDD_HHMMSS("yyyyMMdd HHmmss"),
        YYYYMMDD("yyyyMMdd"),
        YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
        YYYY_NIAN_MM_YUE_DD_RI("yyyy年MM月dd日"),
        HHMMSS("HH:mm:ss"),
        UN_KNOWD("未知");
        private String pattern;

        DatePatternEnum(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }

        public DatePatternEnum getDatePatternEnum(String pattern) {
            DatePatternEnum[] datePatternEnums = DatePatternEnum.values();
            for (DatePatternEnum el : datePatternEnums) {
                if (StringUtils.equals(el.getPattern(), pattern)) {
                    return el;
                }
            }
            return DatePatternEnum.UN_KNOWD;
        }
    }

}

