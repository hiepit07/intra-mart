/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:BillSourceData.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/12/10
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 ACT)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

import java.util.List;

import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Body;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Head;

/**
 * 請求元格納クラス
 * 
 * @author ACT)前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class BillSourceData {

  /** メッセージコード */
  private String msgCd;

  /** 売上ヘッダ */
  private TblUri01Head uriHead;

  /** 売上明細 */
  private List<TblUri01Body> lstUriBody;
  private String uriBodyJson; // 売上明細（JSON型）

  /** 更新担当者名 */
  private String updUserName;
  
  /** 内税顧客区分 */
  private String taxIncKb;
  /**
   * msgCdを取得する.
   *
   * @return String msgCd
   */
  public String getMsgCd() {
    return msgCd;
  }

  /**
   * msgCdをセットする.
   *
   * @param msgCd msgCd
   */
  public void setMsgCd(String msgCd) {
    this.msgCd = msgCd;
  }

  /**
   * uriHeadを取得する.
   *
   * @return TblUri01Head uriHead
   */
  public TblUri01Head getUriHead() {
    return uriHead;
  }

  /**
   * uriHeadをセットする.
   *
   * @param uriHead uriHead
   */
  public void setUriHead(TblUri01Head uriHead) {
    this.uriHead = uriHead;
  }

  /**
   * lstUriBodyを取得する.
   *
   * @return List&lt;TblUri01Body&gt; lstUriBody
   */
  public List<TblUri01Body> getLstUriBody() {
    return lstUriBody;
  }

  /**
   * lstUriBodyをセットする.
   *
   * @param lstUriBody lstUriBody
   */
  public void setLstUriBody(List<TblUri01Body> lstUriBody) {
    this.lstUriBody = lstUriBody;
  }

  /**
   * uriBodyJsonを取得する.
   *
   * @return String uriBodyJson
   */
  public String getUriBodyJson() {
    return uriBodyJson;
  }

  /**
   * uriBodyJsonをセットする.
   *
   * @param uriBodyJson uriBodyJson
   */
  public void setUriBodyJson(String uriBodyJson) {
    this.uriBodyJson = uriBodyJson;
  }

  /**
   * updUserNameを取得する.
   *
   * @return String updUserName
   */
  public String getUpdUserName() {
    return updUserName;
  }

  /**
   * updUserNameをセットする.
   *
   * @param updUserName updUserName
   */
  public void setUpdUserName(String updUserName) {
    this.updUserName = updUserName;
  }

  /**
   * taxIncKbを取得する.
   *
   * @return String taxIncKb
   */
  public String getTaxIncKb() {
    return taxIncKb;
  }

  /**
   * taxIncKbをセットする.
   *
   * @param taxIncKb taxIncKb
   */
  public void setTaxIncKb(String taxIncKb) {
    this.taxIncKb = taxIncKb;
  }
}
