/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:InputCheckCom.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/16
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/16 1.00                  TDK)安延        新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;

/**
 * 入力チェック（システム共通）
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */

public class InputCheckCom {

    /** 型 半角数値（0～9のみ） */
    public static final int TYPE_NUM = 0;
    /** 型 半角英字(a～z,A～Z) */
    public static final int TYPE_ALPHA = 1;
    /** 型 半角記号 */
    public static final int TYPE_SIGN = 2;
    /** 型 半角英数字(0～9,a～z,A～Z) */
    public static final int TYPE_NUM_ALPHA = 3;
    /** 型 半角英数字記号(0～9,a～z,A～Z,記号) */
    public static final int TYPE_NUM_ALPHA_SIGN = 4;
    /** 型 半角カタカナ */
    public static final int TYPE_KANA = 5;
    /** 型 半角全て */
    public static final int TYPE_HALF = 6;
    /** 型 全角全て */
    public static final int TYPE_EM = 7;

    /**
     * 必須チェック
     *
     * @param val   チェック項目
     * @return  チェック結果
     */
    public static String chkEmpty(Object val) {
        String result = null;
        if (val == null) {
            result = "COM006-E";
        } else {
            if (val instanceof String) {
                if (((String) val).trim().equals("")) {
                    result = "COM006-E";
                }
            }
        }
        return result;
    }

    /**
     * 型チェック
     *
     * @param val チェック項目
     * @param chkType 型（※形式は定数参照）
     * @return チェック結果
     */
    public static String chkType(String val, int chkType) {
        String result = null;

        if (val == null || val.equals("")) {
            return result;
        }

        switch (chkType) {
        // 半角数値
        case TYPE_NUM:
            if (!Pattern.compile("^[0-9]+$").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 半角英字
        case TYPE_ALPHA:
            if (!Pattern.compile("^[a-zA-Z]+$").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 半角記号
        case TYPE_SIGN:
            if (!Pattern.compile("^[ -/:-@\\[-\\`\\{-\\~]+$").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 半角英数字
        case TYPE_NUM_ALPHA:
            if (!Pattern.compile("^[a-zA-Z0-9]+$").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 半角英数字記号
        case TYPE_NUM_ALPHA_SIGN:
            if (!Pattern.compile("^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]+$").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 半角カタカナ
        case TYPE_KANA:
            if (!Pattern.compile("^[｡-ﾟ+]+$").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 半角すべて
        case TYPE_HALF:
            if (!Pattern.compile("[ -~｡-ﾟ]+").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        // 全角すべて
        case TYPE_EM:
            if (!Pattern.compile("[^ -~｡-ﾟ]+").matcher(val).matches()) {
                result = "COM001-E";
            }
            break;
        default:
            break;
        }
        return result;
    }

    /**
     * 日付フォーマットチェック
     *
     * @param val チェック値
     * @param format 日付フォーマッチ（※形式は定数参照）
     * @return チェック結果
     */
    public static String chkDate(String val, String format) {
        String result = null;

        //引数チェック
        if (val == null || val.equals("") || format == null || format.equals("")) {
            return result;
        }

        if (format.equals(CommonConst.DATE_FORMAT_YMD)) {
            //YYYYMMDD形式のチェック処理
            result = chkDateYmd(val);

        } else if (format.equals(CommonConst.DATE_FORMAT_YM)) {
            //YYYYMM形式のチェック処理
            result = chkDateYm(val);

        } else if (format.equals(CommonConst.DATE_FORMAT_MD)) {
            //MMDD形式のチェック処理
            result = chkDateMd(val);

        } else if (format.equals(CommonConst.DATE_FORMAT_YMDHMS)) {
            // YYYYMMDDHHMISS形式のチェック処理
            result = chkDateYmdhms(val);
        }
        return result;
    }

    /**
     * 数値フォーマットチェック
     *
     * @param val チェック値
     * @param minusFlg マイナス許可フラグ(true:許可する、false：許可しない）
     * @return チェック結果
     */
    public static String chkNum(String val, boolean minusFlg) {
        String result = null;
        if (val == null || val.equals("")) {
            return result;
        }
        if (minusFlg != true) {
            //マイナスを許可しない
            if (!Pattern.compile("^([1-9]\\d*|0)(\\.\\d+)?$").matcher(val).matches()) {
                result = "COM001-E";
            }
        } else {
            //マイナスを許可する
            if (!Pattern.compile("^[-]?([1-9]\\d*|0)(\\.\\d+)?").matcher(val).matches()) {
                result = "COM001-E";
            }
        }
        return result;
    }

    /**
     * 桁数チェック処理
     *
     * @param val チェック値
     * @return チェック結果
     */
    public static String chkLength(String val, int len) {
      String result = null;
      if (len < val.length()) {
        result = "COM001-E";
      }
      return result;
    }

    //private-----------------------------------------------------------

    /**
     * 日付チェック処理（YYYYMMDD形式用）
     *
     * @param val チェック値
     * @return チェック結果
     */
    private static String chkDateYmd(String val) {
        SimpleDateFormat formatter;
        String result = null;
        java.util.Date pdate = null;

        if (val.length() != CommonConst.DATE_FORMAT_YMD.length()) {
            result = "COM001-E";
        } else {
            formatter = new SimpleDateFormat(CommonConst.DATE_FORMAT_YMD);
            try {
                pdate = formatter.parse(val);
            } catch (ParseException e) {
                result = "COM001-E";
                return result;
            }
            formatter.setLenient(false);
            String reverse = formatter.format(pdate);
            if (!val.equals(reverse)) {
                result = "COM001-E";
            }
        }
        return result;
    }

    /**
     * 日付チェック処理（YYYYMM形式用）
     *
     * @param val チェック値
     * @return チェック結果
     */
    private static String chkDateYm(String val) {
        String result = null;

        if (val.length() != CommonConst.DATE_FORMAT_YM.length()) {
            result = "COM001-E";
        } else {
            result = chkDateYmd(val + "01");
        }
        return result;
    }

    /**
     * 日付チェック処理（MMDD形式用）
     *
     * @param val チェック値
     * @return チェック結果
     */
    private static String chkDateMd(String val) {
        String result = null;

        if (val.length() != CommonConst.DATE_FORMAT_MD.length()) {
            result = "COM001-E";
        } else {
            int mm = Integer.valueOf(val.substring(0, 2)).intValue();
            int dd = Integer.valueOf(val.substring(2, 4)).intValue();

            switch (mm) {
            case 4:
            case 6:
            case 9:
            case 11:
                if (dd < 1 || dd > 30) {
                    result = "COM001-E";
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (dd < 1 || dd > 31) {
                    result = "COM001-E";
                }
                break;
            case 2:
                if (dd < 1 || dd > 29) {
                    result = "COM001-E";
                }
                break;
            default:
                result = "COM001-E";
                break;
            }
        }
        return result;
    }

    /**
     * 日付チェック処理（YYYYMMDDHHMISS形式用）
     *
     * @param val チェック値
     * @return チェック結果
     */
    private static String chkDateYmdhms(String val) {
        String result = null;

        if (val.length() != CommonConst.DATE_FORMAT_YMDHMS.length()) {
            result = "COM001-E";
        } else {
            // YYYYMMDDのチェック
            result = chkDateYmd(val.substring(0, 8));

            //HHMISSのチェック
            if (result == null) {
                int hh = Integer.valueOf(val.substring(8, 10)).intValue();
                int mi = Integer.valueOf(val.substring(10, 12)).intValue();
                int ss = Integer.valueOf(val.substring(12, 14)).intValue();

                if (hh < 0 || hh > 23) {
                    result = "COM001-E";
                } else if (mi < 0 || mi > 59) {
                    result = "COM001-E";
                } else if (ss < 0 || ss > 59) {
                    result = "COM001-E";
                }
            }
        }
        return result;
    }
}
