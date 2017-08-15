/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:UnitTaxPriceData.java
 *
 * <p>
 * 作成者:都築電気株式会社
 * 作成日:2015/10/21
 *
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/21 1.00 都築）関口 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 単価情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class UnitTaxPriceData {

  /** 単価消費税 */
  private double unitPriceTax;

  /** 税抜き単価 */
  private double withoutTax;

  /**
   * 単価消費税getter
   *
   * @return unitPriceTax 単価消費税
   */
  public double getUnitPriceTax() {
    return unitPriceTax;
  }

  /**
   * 単価消費税setter
   *
   * @param unitPriceTax 単価消費税
   */
  public void setUnitPriceTax(double unitPriceTax) {
    this.unitPriceTax = unitPriceTax;
  }

  /**
   * 税抜き単価getter
   *
   * @return withoutTax 税抜き単価
   */
  public double getWithoutTax() {
    return withoutTax;
  }

  /**
   * 税抜き単価setter
   *
   * @param withoutTax 税抜き単価
   */
  public void setWithoutTax(double withoutTax) {
    this.withoutTax = withoutTax;
  }

}
