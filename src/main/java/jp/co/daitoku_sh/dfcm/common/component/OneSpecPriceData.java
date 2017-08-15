/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:OneSpecPriceData.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/16
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/16 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * １明細当たり金額情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class OneSpecPriceData {

  /** 金額 */
  private double price;

  /** 単価（本体） */
  private double bodyUnitPrice;

  /** 金額（本体） */
  private double bodyPrice;

  /** 消費税額 */
  private double taxPrice;

  /**
   * 金額getter
   *
   * @return price 金額
   */
  public double getPrice() {
    return price;
  }

  /**
   * 金額setter
   *
   * @param price 金額
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * 単価（本体）getter
   *
   * @return bodyUnitPrice 単価（本体）
   */
  public double getBodyUnitPrice() {
    return bodyUnitPrice;
  }

  /**
   * 単価（本体）setter
   *
   * @param bodyUnitPrice 単価（本体）
   */
  public void setBodyUnitPrice(double bodyUnitPrice) {
    this.bodyUnitPrice = bodyUnitPrice;
  }

  /**
   * 金額（本体）getter
   *
   * @return bodyPrice 金額（本体）
   */
  public double getBodyPrice() {
    return bodyPrice;
  }

  /**
   * 金額（本体）setter
   *
   * @param bodyPrice 金額（本体）
   */
  public void setBodyPrice(double bodyPrice) {
    this.bodyPrice = bodyPrice;
  }

  /**
   * 消費税額getter
   *
   * @return taxPrice 消費税額
   */
  public double getTaxPrice() {
    return taxPrice;
  }

  /**
   * 消費税額setter
   *
   * @param taxPrice 消費税額
   */
  public void setTaxPrice(double taxPrice) {
    this.taxPrice = taxPrice;
  }

}
