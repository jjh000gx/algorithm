package com.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlScriptUtil {
    public static void main(String[] args) {
        /*String value = "1019499922315";
        long dbCount = 32;
        long tbCount = 1024;*/

        String value = "1021598227372";
        long dbCount = 64;
        long tbCount = 1024;



        //        System.out.println(parseTddlStr(value));
        System.out.println("table :" + getTableCount2(value, true, tbCount));
        System.out.println("db :" + getDbCount2(value, true,tbCount, dbCount));
        //System.out.println(getDbCount2("1019524077050", true, 1024L, 32L));
        BigDecimal t1 = new BigDecimal("137.735849");
        BigDecimal t2 = new BigDecimal("136.226415");
        System.out.println(t1.subtract(t2).setScale(6));

    }

    public static String getTableCount(String value, long tbCount) {
        return buildSequenceNo(parseTddlStr(value) % tbCount, 4);
    }

    public static String getDbCount(String value, long tbCount, long dbCount) {
        if(tbCount < dbCount) dbCount = tbCount;
        return buildSequenceNo(parseTddlStr(value) % tbCount / (tbCount / dbCount), 2);
    }

    public static String getTableCount2(String value, boolean ifHash, long tbCount) {
        return buildSequenceNo(parseTddlStr(value, ifHash) % tbCount, 4);
    }

    public static String getDbCount2(String value, boolean ifHash, long tbCount, long dbCount) {
        if(tbCount < dbCount) {
            return buildSequenceNo(parseTddlStr(value, ifHash) % tbCount / 1, 2);
        }else {
            return buildSequenceNo(parseTddlStr(value, ifHash) % tbCount / (tbCount / dbCount), 2);
        }

    }

    public static long parseTddlStr(String tddl, boolean ifHash) {
        if (tddl == null) {
            return -1L;
        }
        if (ifHash) {
            return Long.valueOf(Math.abs(tddl.hashCode()));
        } else {
            return Long.valueOf(tddl);
        }
        //long result = Long.valueOf(Math.abs(tddl.hashCode()));
        //return result;
    }

    private static String buildSequenceNo(long nextValue, int length) {
        String value = nextValue + "";
        if (value.length() <= length) {
            return StringUtils.leftPad(value, length, '0');
        } else {
            return StringUtils.substring(value, 0, length);
        }
    }

    public static long parseTddlStr(String tddl) {
        if (tddl == null) {
            return -1L;
        }
        long result = Long.valueOf(Math.abs(tddl.hashCode()));

        return result;
    }

    public static String getOutPutFileName(String fileName) {

        return System.getProperty("user.dir") + "/outputData/" + getNo(fileName) + ".txt";

    }

    public static String[] spiltData(String data, String split) {
        String[] items = StringUtils.splitPreserveAllTokens(data, split);
        for (int i = 0; i < items.length; i++) {
            items[i] = StringUtils.trim(items[i]);
        }

        return items;
    }

    public static String getSeq(Long seq) {
        StringBuffer sb = new StringBuffer();
        int length = seq.toString().length();
        for (int i = length; i < 9; i++) {
            sb.append("0");
        }
        sb.append(seq);
        return sb.toString();
    }

    public static String getNo(String fileName) {
        StringBuffer buf = new StringBuffer();
        buf.append(fileName);
        try {
            buf.append(parseSearchDate(new Date(), "yyyyMMdd"));
        } catch (Exception e) {
        }
        buf.append(getSeq(87l));
        return buf.toString();
    }

    public static String parseSearchDate(Date date, String format) throws Exception {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isBlank(format) ? "yyyy-MM-dd" : format);
            String dates = sdf.format(date);
            return dates;

        } catch (Exception e) {
            throw new Exception("日期" + date + "转换异常", e);
        }
    }

}
