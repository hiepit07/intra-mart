/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:Uri0101d01Screen.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

/**
 * AJAXで取得した値を画面に返すために格納するクラス
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */

public class Uri0103d01Screen {
  /** */
  private String strJsonUriHead;

  /** メッセージ */
  private String strMessage;
  /** 事業所コード */
  private String strJigyoCode;
  /** 事業所名 */
  private String strJigyoName;
  /** 納品金額上限値 */
  private String deliPriceMax;
  /** 売上情報 */
  private UriageData uriData;
  /** 伝票No */
  private long lngSlipNo;
  /** 伝票枝番 */
  private short shtSlipIdx;
  /** 得意先伝票No */
  private String strCustomerSlipNo;

  /**
   * strMessageを取得する.
   *
   * @return String strMessage
   */
  public String getStrMessage() {
    return strMessage;
  }

  /**
   * strMessageをセットする.
   *
   * @param strMessage strMessage
   */
  public void setStrMessage(String strMessage) {
    this.strMessage = strMessage;
  }

  /**
   * strJigyoCodeを取得する.
   *
   * @return String strJigyoCode
   */
  public String getStrJigyoCode() {
    return strJigyoCode;
  }

  /**
   * strJigyoCodeをセットする.
   *
   * @param strJigyoCode strJigyoCode
   */
  public void setStrJigyoCode(String strJigyoCode) {
    this.strJigyoCode = strJigyoCode;
  }

  /**
   * strJigyoNameを取得する.
   *
   * @return String strJigyoName
   */
  public String getStrJigyoName() {
    return strJigyoName;
  }

  /**
   * strJigyoNameをセットする.
   *
   * @param strJigyoName strJigyoName
   */
  public void setStrJigyoName(String strJigyoName) {
    this.strJigyoName = strJigyoName;
  }

  /**
   * deliPriceMaxを取得する.
   *
   * @return String deliPriceMax
   */
  public String getDeliPriceMax() {
    return deliPriceMax;
  }

  /**
   * deliPriceMaxをセットする.
   *
   * @param deliPriceMax deliPriceMax
   */
  public void setDeliPriceMax(String deliPriceMax) {
    this.deliPriceMax = deliPriceMax;
  }

  /**
   * uriDataを取得する.
   *
   * @return UriageData uriData
   */
  public UriageData getUriData() {
    return uriData;
  }

  /**
   * uriDataをセットする.
   *
   * @param uriData uriData
   */
  public void setUriData(UriageData uriData) {
    this.uriData = uriData;
  }

  /**
   * lngSlipNoを取得する.
   *
   * @return long lngSlipNo
   */
  public long getLngSlipNo() {
    return lngSlipNo;
  }

  /**
   * lngSlipNoをセットする.
   *
   * @param lngSlipNo lngSlipNo
   */
  public void setLngSlipNo(long lngSlipNo) {
    this.lngSlipNo = lngSlipNo;
  }

  /**
   * shtSlipIdxを取得する.
   *
   * @return short shtSlipIdx
   */
  public short getShtSlipIdx() {
    return shtSlipIdx;
  }

  /**
   * shtSlipIdxをセットする.
   *
   * @param shtSlipIdx shtSlipIdx
   */
  public void setShtSlipIdx(short shtSlipIdx) {
    this.shtSlipIdx = shtSlipIdx;
  }

  /**
   * strCustomerSlipNoを取得する.
   *
   * @return String strCustomerSlipNo
   */
  public String getStrCustomerSlipNo() {
    return strCustomerSlipNo;
  }

  /**
   * strCustomerSlipNoをセットする.
   *
   * @param strCustomerSlipNo strCustomerSlipNo
   */
  public void setStrCustomerSlipNo(String strCustomerSlipNo) {
    this.strCustomerSlipNo = strCustomerSlipNo;
  }

  /**
   * strJsonUriHeadを取得する.
   *
   * @return String strJsonUriHead
   */
  public String getStrJsonUriHead() {
    return strJsonUriHead;
  }

  /**
   * strJsonUriHeadをセットする.
   *
   * @param strJsonUriHead strJsonUriHead
   */
  public void setStrJsonUriHead(String strJsonUriHead) {
    this.strJsonUriHead = strJsonUriHead;
  }
}