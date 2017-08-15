/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:SlipNumberingData.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/24
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/24 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 伝票No採番情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class SlipNumberingData {

  /** 伝票No */
  private String strSlipNumber;

  /** メッセージコード */
  private String strMsgCd;

  /** 発生マスタ区分 */
  private String strGeneMstKb;

  /**
   * 伝票No_getter
   *
   * @return strSlipNumber 伝票No
   */
  public String getSlipNumber() {
    return strSlipNumber;
  }

  /**
   * 伝票No_setter
   *
   * @param String strSlipNumber 伝票No
   */
  public void setSlipNumber(String strSlipNumber) {
    this.strSlipNumber = strSlipNumber;
  }

  /**
   * メッセージコードgetter
   *
   * @return String strMsgCd メッセージコード
   */
  public String getMsgCd() {
    return strMsgCd;
  }

  /**
   * メッセージコードsetter
   *
   * @param strMsgCd メッセージコード
   */
  public void setMsgCd(String strMsgCd) {
    this.strMsgCd = strMsgCd;
  }

  /**
   * 発生マスタ区分getter
   *
   * @return String strGeneMstKb 発生マスタ区分
   */
  public String getGeneMstKb() {
    return strGeneMstKb;
  }

  /**
   * 発生マスタ区分setter
   *
   * @param strstrGeneMstKb 発生マスタ区分
   */
  public void setGeneMstKb(String strGeneMstKb) {
    this.strGeneMstKb = strGeneMstKb;
  }

}
