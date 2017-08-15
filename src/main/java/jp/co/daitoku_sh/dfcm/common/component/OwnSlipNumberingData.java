/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:OwnSlipNumberingData.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/15
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/15 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 自社伝票No採番情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class OwnSlipNumberingData {

  /** 自社伝票No */
  private Long lngOwnSlipNumber;

  /** メッセージコード */
  private String strMsgCd;

  /**
   * 自社伝票No_getter
   *
   * @return lngOwnSlipNumber 自社伝票No
   */
  public Long getOwnSlipNumber() {
    return lngOwnSlipNumber;
  }

  /**
   * 自社伝票No_setter
   *
   * @param Long lngOwnSlipNumber 自社伝票No
   */
  public void setOwnSlipNumber(Long lngOwnSlipNumber) {
    this.lngOwnSlipNumber = lngOwnSlipNumber;
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

}
