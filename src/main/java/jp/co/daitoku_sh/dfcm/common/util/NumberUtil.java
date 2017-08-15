/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:NumberUtil.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/27
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/27 1.00                  TDK)安延        新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 数値計算処理
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class NumberUtil {

    /**
     * num1 + num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double add(String num1, String num2) {
        BigDecimal result = new BigDecimal(num1).add(new BigDecimal(num2));
        return result.doubleValue();
    }

    /**
     * num1 + num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double add(Double num1, Double num2) {
        double result = add(Double.toString(num1.doubleValue()),
                Double.toString(num2.doubleValue()));
        return result;
    }

    /**
     * num1 + num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double add(double num1, double num2) {
        double result = add(Double.toString(num1), Double.toString(num2));
        return result;
    }

    /**
     * num1 - num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double minus(String num1, String num2) {
        BigDecimal result = new BigDecimal(num1).subtract(new BigDecimal(num2));
        return result.doubleValue();
    }

    /**
     * num1 - num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double minus(Double num1, Double num2) {
        double result = minus(Double.toString(num1.doubleValue()),
                Double.toString(num2.doubleValue()));
        return result;
    }

    /**
     * num1 - num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double minus(double num1, double num2) {
        double result = minus(Double.toString(num1), Double.toString(num2));
        return result;
    }

    /**
     * num1 * num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double multiply(String num1, String num2) {
        BigDecimal result = new BigDecimal(num1).multiply(new BigDecimal(num2));
        return result.doubleValue();
    }

    /**
     * num1 * num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double multiply(Double num1, Double num2) {
        double result = multiply(Double.toString(num1.doubleValue()),
                Double.toString(num2.doubleValue()));
        return result;
    }

    /**
     * num1 * num2を行い結果を返します。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double multiply(double num1, double num2) {
        double result = multiply(Double.toString(num1), Double.toString(num2));
        return result;
    }

    /**
     * num1 / num2を行い結果を返します。小数点以下は6桁までを求めそれ以下は切り捨てます。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double divide(String num1, String num2) {
        BigDecimal result = new BigDecimal(num1)
                .divide(new BigDecimal(num2), 6, BigDecimal.ROUND_DOWN);
        return result.doubleValue();
    }

    /**
     * num1 / num2を行い結果を返します。小数点以下は6桁までを求めそれ以下は切り捨てます。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double divide(Double num1, Double num2) {
        double result = divide(Double.toString(num1.doubleValue()),
                Double.toString(num2.doubleValue()));
        return result;
    }

    /**
     * num1 / num2を行い結果を返します。小数点以下は6桁までを求めそれ以下は切り捨てます。
     *
     * @param           num1            入力数１
     * @param           num2            入力数２
     * @return      double          計算結果
     */
    public static double divide(double num1, double num2) {
        double result = divide(Double.toString(num1), Double.toString(num2));
        return result;
    }

    /**
     * 数値を3桁づつカンマ区切りに変換する。
     *
     * @param num 変換前
     * @return 変換後
     */
    public static String addFigure(String num) {
        Long chkNum = Long.valueOf(num);
        NumberFormat nf = NumberFormat.getNumberInstance();
        String result = nf.format(chkNum);
        return result;
    }

    /**
     * 数値からカンマを除去する。
     *
     * @param num 変換前
     * @return 変換後
     */
    public static String delFigure(String num) {
        String result = num.replaceAll(",", "");
        return result;
    }

}
