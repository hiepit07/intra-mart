/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:DateUtil.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/24
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/24 1.00 TDK)安延 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;

/**
 * 日付関連ユーティリティをまとめたクラスです。
 *
 * @author 都築電気
 * @version 1.0
 * @since 1.0
 */
public class DateUtil {

  /**
   * システム日付を取得します。（YYYYMMDD形式）
   *
   * @return 日付文字列
   */
  public static String getSysDate() {
    String result = null;
    java.util.Date date = new java.util.Date();
    result = setFormat(date, CommonConst.DATE_FORMAT_YMD);
    return result;
  }

  /**
   * システム時刻を取得します。（HHmmss）
   *
   * @return 時刻文字列
   */
  public static String getSysTime() {
    String result = null;
    java.util.Date date = new java.util.Date();
    result = setFormat(date, CommonConst.DATE_FORMAT_HMS);
    return result;
  }

  /**
   * システム時刻(ミリ秒)を取得します。（HHmmssSSS）
   *
   * @return 時刻文字列
   */
  public static String getSysTimeMiSec() {
    String result = null;
    java.util.Date date = new java.util.Date();
    result = setFormat(date, CommonConst.DATE_FORMAT_HMSS);
    return result;
  }

  /**
   * 年、月、日を元に日付を作成します。
   *
   * @param year 年
   * @param month 月
   * @param day 日
   * @return java.sql.Date 日付
   */
  public static java.sql.Date createDate(Integer year,
      Integer month,
      Integer day) {

    String date = privateCreateDate(year, month, day);

    return java.sql.Date.valueOf(date);
  }

  /**
   * 基点日付から算出期間分の年を加算した日付を返します。
   *
   * @param date 基点日付
   * @param year 算出期間（年）
   * @return java.util.Date 算出後の日付
   */
  public static java.util.Date getSpecYear(java.util.Date date, int year) {

    return new java.util.Date(getCalulateDate(date, Calendar.YEAR, year));
  }

  /**
   * 基点日付から算出期間分の年を加算した日付を返します。
   *
   * @param date 基点日付
   * @param year 算出期間（年）
   * @return java.sql.Date 算出後の日付
   */
  public static java.sql.Date getSqlSpecYear(java.util.Date date, int year) {

    return new java.sql.Date(getCalulateDate(date, Calendar.YEAR, year));
  }

  /**
   * 基点日付から算出期間分の月を加算した日付を返します。
   *
   * @param date 基点日付
   * @param month 算出期間（月）
   * @return java.util.Date 算出後の日付
   */
  public static java.util.Date getSpecMonth(java.util.Date date, int month) {

    return new java.util.Date(getCalulateDate(date, Calendar.MONTH, month));
  }

  /**
   * 基点日付から算出期間分の月を加算した日付を返します。
   *
   * @param date 基点日付
   * @param month 算出期間（月）
   * @return java.sql.Date 算出後の日付
   */
  public static java.sql.Date getSqlSpecMonth(java.util.Date date, int month) {

    return new java.sql.Date(getCalulateDate(date, Calendar.MONTH, month));
  }

  /**
   * 基点日付から算出期間分の日を加算した日付を返します。
   *
   * @param date 基点日付
   * @param day 算出期間（日）
   * @return java.util.Date 算出後の日付
   */
  public static java.util.Date getSpecDay(java.util.Date date, int day) {

    return new java.util.Date(getCalulateDate(date, Calendar.DATE, day));
  }

  /**
   * 基点日付から算出期間分の日を加算した日付を返します。
   *
   * @param date 基点日付
   * @param day 算出期間（日）
   * @return java.sql.Date 算出後の日付
   */
  public static java.sql.Date getSqlSpecDay(java.util.Date date, int day) {

    return new java.sql.Date(getCalulateDate(date, Calendar.DATE, day));
  }

  /**
   * 指定日付の月末の日付を返します。
   *
   * @param date 指定日付
   * @return java.util.Date 月末の日付
   */
  public static java.util.Date getLastDayOfMonth(java.util.Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DATE, calendar.getActualMaximum(
        Calendar.DAY_OF_MONTH));

    return calendar.getTime();
  }

  /**
   * java.util.Date型をjava.sql.Date型に変換します。
   *
   * @param utilDate java.util.Date型日付
   * @return java.sql.Date java.sql.Date型日付
   */
  public static java.sql.Date utilToSqlDate(java.util.Date utilDate) {
    java.sql.Date sqldate = new java.sql.Date(utilDate.getTime());
    return sqldate;
  }

  /**
   * java.sql.Date型をjava.util.Date型に変換します。
   *
   * @param sqlDate java.sql.Date型日付
   * @return java.util.Date java.util.Date型日付
   */
  public static java.util.Date sqlToUtilDate(java.sql.Date sqlDate) {
    return (java.util.Date) sqlDate;
  }

  /**
   * 指定した書式、文字列から、java.util.Dateオブジェクトに変換し返します。
   *
   * @param str 文字列
   * @param format 書式
   * @return java.util.Date
   * @exception CpRuntimeException
   */
  public static java.util.Date toDate(String str, String format) {
    return toPrivateDate(str, format);
  }

  /**
   * 指定した書式、文字列から、java.sql.Dateオブジェクトに変換し返します。
   *
   * @param str 文字列
   * @param format 書式
   * @return java.sql.Date
   * @exception CpRuntimeException
   */
  public static java.sql.Date toSqlDate(String str, String format) {
    return utilToSqlDate(toPrivateDate(str, format));
  }

  /**
   * 指定日付を指定フォーマットに変換した文字列を返します。
   *
   * @param date 指定日付
   * @param format フォーマット形式 例）yyyy/MM/dd
   * @return String フォーマット後の文字列
   * @exception CpRuntimeException
   */
  public static String setFormat(java.util.Date date, String format) {

    String formatStr = "";
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      formatStr = formatter.format(date);
    } catch (Exception ex) {
      return null;
    }

    return formatStr;
  }

  /**
   * 月、日に0を補完し、日付文字列を作成する。(YYYYMMDD形式)
   *
   * @param year 年
   * @param month 月
   * @param day 日
   * @returns YYYYMMDD形式文字列
   */
  public static String compDateZero(String year, String month, String day) {
    if (month.length() == 1) {
      month = '0' + month;
    }
    if (day.length() == 1) {
      day = '0' + day;
    }
    return year + month + day;
  }

  /**
   * 年月日文字列（YYYYMMDD)をスラッシュ区切りに変換した値を返却する。
   *
   * @param ymd 年月日文字列
   * @return 変換後文字列
   */
  public static String addSlash(String ymd) {
    java.util.Date dateWk = toDate(ymd, "yyyyMMdd");
    return setFormat(dateWk, "yyyy/MM/dd");
  }

  /**
   * スラッシュ区切りの年月日文字列（YYYY/MM/DD)からスラッシュを除去する。
   *
   * @param ymd スラッシュ区切り文字列
   * @return 変換後文字列
   */
  public static String delSlash(String ymd) {
    return ymd.replaceAll("/", "");
  }

  // privete-------------------------------------------------------------------------

  /**
   * 年、月、日を元に日付文字列を作成します。
   *
   * @param year 年
   * @param month 月
   * @param day 日
   * @return String 日付文字列
   */
  private static String privateCreateDate(Integer year,
      Integer month,
      Integer day) {

    String strYear = year.toString();
    String strMonth = month.toString();
    String strDay = day.toString();

    return strYear + "-" + strMonth + "-" + strDay;
  }

  /**
   * 指定日付に指定の時間フィールド（年/月/日等）の増分値分増加して返します。
   *
   * @param date 指定日付
   * @param field Calendarクラスの時間フィールド
   * @param value フィールドに追加される日付または時刻の量
   */
  private static long getCalulateDate(java.util.Date date,
      int field,
      int value) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(field, value);

    return calendar.getTime().getTime();
  }

  /**
   * 指定した書式、文字列から、java.util.Dateオブジェクトに変換し返します。
   *
   * @param str 文字列
   * @param format 書式
   * @return java.util.Date
   * @exception CpRuntimeException
   */
  private static java.util.Date toPrivateDate(String str, String format) {

    java.util.Date date = null;
    SimpleDateFormat fmt = new SimpleDateFormat(format);
    try {
      date = fmt.parse(str);
    } catch (ParseException e) {
      return null;
    }
    return date;
  }
}