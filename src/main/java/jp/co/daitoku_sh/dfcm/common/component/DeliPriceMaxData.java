/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:DeliPriceMaxData.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/18
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/18 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 納品金額上限格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class DeliPriceMaxData {

  /** 納品金額上限 */
  private double dblMaxDeliPrice;

  /** メッセージコード */
  private String strMsgCode;

  /**
   * 納品金額上限getter
   *
   * @return dblMaxDeliPrice 金額
   */
  public double getMaxDeliPrice() {
    return dblMaxDeliPrice;
  }

  /**
   * 納品金額上限setter
   *
   * @param dblMaxDeliPrice 納品金額上限
   */
  public void setMaxDeliPrice(double dblMaxDeliPrice) {
    this.dblMaxDeliPrice = dblMaxDeliPrice;
  }

  /**
   * メッセージコードgetter
   *
   * @return strMsgCode メッセージコード
   */
  public String getMsgCode() {
    return strMsgCode;
  }

  /**
   * メッセージコードsetter
   *
   * @param strMsgCode メッセージコード
   */
  public void setMsgCode(String strMsgCode) {
    this.strMsgCode = strMsgCode;
  }

}
