/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:TaxRateData.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/25
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 消費税率情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class TaxRateData {

  /** 消費税率 */
  private double dblTaxRate;

  /** 処理結果 */
  private boolean blnProcResult;

  /**
   * 消費税率getter
   *
   * @return dblTaxRate 消費税率
   */
  public double getTaxRate() {
    return dblTaxRate;
  }

  /**
   * 消費税率setter
   *
   * @param dblTaxRate 消費税率
   */
  public void setTaxRate(double dblTaxRate) {
    this.dblTaxRate = dblTaxRate;
  }

  /**
   * 処理結果getter
   *
   * @return blnProcResult 処理結果
   */
  public boolean getProcResult() {
    return blnProcResult;
  }

  /**
   * 処理結果setter
   *
   * @param blnProcResult 処理結果
   */
  public void setProcResult(boolean blnProcResult) {
    this.blnProcResult = blnProcResult;
  }

}
